package com.hit.gameHandler;

import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;

public class BoardGameHandler 
{
	
	private IGameAlgo game;
	
	public BoardGameHandler(IGameAlgo game)
	{
		this.game=game;
	}
	//this class will handle the board game using our users requests 
	//this class uses our GamesAlgorithms jar that contains the games logic
	
	//Plays a move for the user and then for the computer if game not done,returns the state of the game
	public IGameAlgo.GameState playOneRound(GameMove playerMove)
	{
		this.game.updatePlayerMove(playerMove);
		if(this.game.getGameState(playerMove)==GameState.IN_PROGRESS)
			this.game.calcComputerMove();
		return this.game.getGameState(playerMove);
	}
	
	public char[][] computerStartGame()//For use only if the computer starts and returns the board
	{
		this.game.calcComputerMove();
		return this.game.getBoardState();
	}
	
	public char[][] getBoardState()//returns the board of the running game
	{
		return this.game.getBoardState();
	}
}
