package com.hit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hit.controller.Controller;
import com.hit.controller.GamesController;
import com.hit.view.GamesView;
import com.hit.view.View;

public class GamesModel implements Model {
	private PropertyChangeSupport propertyChangeHandler;
	private GamesClient server;
	private Integer id;
	private JSONParser jsonParser;
	
	public  GamesModel()
	{
		setPropertyChangeSupport();
		jsonParser = new JSONParser();
		server=new GamesClient(23425);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener)
	{
		propertyChangeHandler.addPropertyChangeListener(propertyChangeListener);
	}
	public void setPropertyChangeSupport() {
		propertyChangeHandler = new PropertyChangeSupport(this);
	}

	@Override
	public void newGame(String gameType, String opponentType) {
		try {
			server.connectServer();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("type", "New-Game");
			jsonObject.put("game", gameType);
			jsonObject.put("opponent", opponentType);
			String responseMessage=server.sendMessage(jsonObject.toJSONString(), true);
			if(responseMessage==null)
			{
				throw new Exception("Server is full\nPlease try again later!!!"); 
			}
			JSONObject response=(JSONObject)jsonParser.parse(responseMessage);
			id=Integer.parseInt((response.get("ID").toString()));
			id=Integer.parseInt((response.get("ID").toString()));
			JSONArray board=(JSONArray) response.get("board");
			Character[] boardChar=new Character[board.size()];
			for(int i=0;i<board.size();i++)
			{
				boardChar[i]=(((String) board.get(i)).charAt(0));
			}
			propertyChangeHandler.firePropertyChange("NewGameEventModel",0,boardChar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {//"Cannot connect to server, please try again later"
			propertyChangeHandler.firePropertyChange("FailToConnectServer",0,e.getMessage());
		}
		
	}

	@Override
	public void updatePlayerMove(int row, int col) {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type", "Update-Move");
		jsonObject.put("ID", id);
		jsonObject.put("row", row);
		jsonObject.put("col", col);
		try {
			JSONObject response=(JSONObject)jsonParser.parse(server.sendMessage(jsonObject.toJSONString(), true));
			JSONArray board=(JSONArray) response.get("board");
			Character[] boardChar=null;
			if(board!=null)
			{
				boardChar=new Character[board.size()];
				for(int i=0;i<board.size();i++)
				{
					boardChar[i]=(((String) board.get(i)).charAt(0));
				}
			}
			if(Integer.parseInt(response.get("state").toString())>=2)
			{
				server.closeConnection();
			}
			propertyChangeHandler.firePropertyChange("UpdatePlayerMoveModel",Integer.parseInt(response.get("state").toString()),boardChar);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void computerStartGame()
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type", "Start-Game");
		jsonObject.put("ID", id);
		try {
			JSONObject response=(JSONObject)jsonParser.parse(server.sendMessage(jsonObject.toJSONString(), true));
			JSONArray board=(JSONArray) response.get("board");
			Character[] boardChar=new Character[board.size()];
			for(int i=0;i<board.size();i++)
			{
				boardChar[i]=(((String) board.get(i)).charAt(0));
			}
			propertyChangeHandler.firePropertyChange("NewGameEventModel",0,boardChar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void restartGame()
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type", "restart");
		jsonObject.put("ID", id);
		try {
			JSONObject response=(JSONObject)jsonParser.parse(server.sendMessage(jsonObject.toJSONString(), true));
			JSONArray board=(JSONArray) response.get("board");
			Character[] boardChar=new Character[board.size()];
			for(int i=0;i<board.size();i++)
			{
				boardChar[i]=(((String) board.get(i)).charAt(0));
			}
			propertyChangeHandler.firePropertyChange("RestartEventModel",0,boardChar);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGame()
	{
		//"Start-Game"
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type", "Start-Game");
		jsonObject.put("ID", id);
		try {
			JSONObject response=(JSONObject)jsonParser.parse(server.sendMessage(jsonObject.toJSONString(), true));
			JSONArray board=(JSONArray) response.get("board");
			Character[] boardChar=new Character[board.size()];
			for(int i=0;i<board.size();i++)
			{
				boardChar[i]=(((String) board.get(i)).charAt(0));
			}
			propertyChangeHandler.firePropertyChange("StartGameEventModel",0,boardChar);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endGame()
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type", "Stop-Game");
		jsonObject.put("ID", id);
		server.sendMessage(jsonObject.toJSONString(), false);
		server.closeConnection();
	}
	

}
