package com.hit.server;
import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.services.GameServerController;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

	public class HandleRequest implements Runnable {
		GameServerController controller;
		JSONParser jsonParser;
		ObjectOutputStream output;
		ObjectInputStream input;
		boolean isGameOn;

		
	 public HandleRequest(java.net.Socket s, GameServerController controller)throws java.io.IOException {
		 this.controller=controller;
		 this.jsonParser=new JSONParser();
		 output=new ObjectOutputStream(s.getOutputStream());
		 input=new ObjectInputStream(s.getInputStream());
		 isGameOn=true;
	 }
	
	@Override
	public void run() {
			String msg;
			JSONObject jsonResponse=new JSONObject();
			JSONArray board;
			//while loop dephend on gameon boolean , gamon is false if the user send STOP-GAME request
			try {
				while(this.isGameOn) {
					msg = (String) input.readObject(); //waiting for new string of json object
					JSONObject jsonMsg=(JSONObject)jsonParser.parse(msg);//parse the string to a json object
					board=new JSONArray();
					if("New-Game".equals((String)jsonMsg.get("type"))) //Works fine to the currect message
					{
						//trying to create new game through the game server controller
						
						int id=this.controller.newGame((String)jsonMsg.get("game"),(String)jsonMsg.get("opponent"));
						if(id==-1)
						{
							throw new Exception("Server capacity is full.");
						}
						//making the new json message to the user
						jsonResponse.put("type", "New-Game");
						jsonResponse.put("ID", id);
						for(char[] array: this.controller.getBoardState(id)) 
							for(char sign :array)
								board.add(""+sign);	//adding to json array all the signs of the board
						jsonResponse.put("board",board);
						output.writeObject(jsonResponse.toJSONString());
						output.flush();
					}
					else if("Start-Game".equals((String)jsonMsg.get("type")))// updates one computer move and returns jsonobject to the user
					{
						Integer id=Integer.parseInt((jsonMsg.get("ID").toString()));
						this.controller.computerStartGame(id);
						jsonResponse.put("type", "Start-Game");
						jsonResponse.put("ID", id);
						for(char[] array: this.controller.getBoardState(id)) 
							for(char sign :array)
								board.add(""+sign);	//adding to json array all the signs of the board
						jsonResponse.put("board",board);
						output.writeObject(jsonResponse.toString());
						output.flush();

					}
					else if("Update-Move".equals((String)jsonMsg.get("type")))
					{
						//updates the player move
						Integer id=Integer.parseInt((jsonMsg.get("ID").toString()));
						Integer row=Integer.parseInt((jsonMsg.get("row").toString()));
						Integer col=Integer.parseInt((jsonMsg.get("col").toString()));
						//update the player move and saves the current game state
						GameState state=this.controller.updateMove(id, new GameMove(row,col));
						jsonResponse.put("type", "Update-Move");
						jsonResponse.put("ID", id);
						jsonResponse.put("state", state.ordinal());
						if(state==GameState.ILLEGAL_PLAYER_MOVE)
							jsonResponse.put("board", null);
						else {
						for(char[] array: this.controller.getBoardState(id)) 
							for(char sign :array)
								board.add(""+sign);	//adding to json array all the signs of the board
						jsonResponse.put("board",board);
						}
						output.writeObject(jsonResponse.toString());
						output.flush();	
						if(state.ordinal()>=2)
						{
							this.controller.endGame(id);
							isGameOn=false;
						}
					}
					else if("Stop-Game".equals((String)jsonMsg.get("type")))
					{
						//ends the game
						this.controller.endGame(Integer.parseInt((jsonMsg.get("ID").toString())));
						isGameOn=false;
					}
					else if("restart".equals((String)jsonMsg.get("type")))
					{
						//restart the game
						Integer id=Integer.parseInt(jsonMsg.get("ID").toString());
						//restart the game using controller methods
						this.controller.restartGame(id);
						jsonResponse.put("type", "restart");
						jsonResponse.put("ID", id);
						for(char [] array: this.controller.getBoardState(id))
							for(char sign: array)
								board.add(""+sign);
						jsonResponse.put("board", board);
						output.writeObject(jsonResponse.toString());
						output.flush();
					}
				}
			} 
 
			catch (Exception e) //print error if happened
			{
				System.out.println(e.getMessage());
			}

	}

}

