package com.hit.gameAlgo;

public interface IGameAlgo {

	public static enum GameState{
		ILLEGAL_PLAYER_MOVE,IN_PROGRESS,PLAYER_LOST,PLAYER_WON,TIE;
		
	}

	
	void calcComputerMove();
	char[][] getBoardState();
	IGameAlgo.GameState getGameState(GameBoard.GameMove move);
	boolean updatePlayerMove(GameBoard.GameMove move);
	
}
