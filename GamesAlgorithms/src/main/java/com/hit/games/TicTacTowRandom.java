
package com.hit.games;

import java.util.Random;

public class TicTacTowRandom extends TicTacTow {
	public TicTacTowRandom(int rowLength, int colLength)  {
		super(rowLength,colLength);
	}
	
	
	public void calcComputerMove()//Fills a blank space in the board randomly as a computer move,test by junit succesful 
	{
		this.currentPlayer=BoardSigns.COMPUTER;
		Random rand = new Random();
		do {
			this.move.setMove(rand.nextInt(3),rand.nextInt(3));
		}while(!updatePlayerMove(this.move));
		if(!checkGameWon())
			this.currentPlayer=BoardSigns.PLAYER;
	}

}

