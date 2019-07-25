package com.hit.services;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo.GameState;

//this class suppose to make a seperation layer between the internet layer and game service class
//it uses all the same methods as game service and call them respectively..
public class GameServerController {
	private GamesService service;
	
	
	public GameServerController(int capacity){
		this.service=new GamesService(capacity);
	}
	public int newGame(String gameType,String opponent)throws UnknownIdException 
	{
		return this.service.newGame(gameType, opponent);
	}
	public void endGame(java.lang.Integer gameId)throws UnknownIdException{
		this.service.endGame(gameId);
	}
	public GameState updateMove(Integer gameId,GameMove playerMove) throws UnknownIdException{
		return this.service.updateMove(gameId, playerMove);
	}
	public char[][] computerStartGame(Integer gameId)throws UnknownIdException{
		return this.service.computerStartGame(gameId);
	}
	public char[][] getBoardState(Integer gameId)throws UnknownIdException{
		return this.service.getBoardState(gameId);
	}
	public void restartGame(int gameId) throws UnknownIdException {
		this.service.restartGame(gameId);
		
	}
	
}
