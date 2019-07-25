package com.hit.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacTowSmart extends TicTacTow  {
	
	public TicTacTowSmart(int rowLength, int colLength)  {
		super(rowLength,colLength);
	}
	public void calcComputerMove()//Fills a blank space on the board using the minmax method to get the best move for the computer
	{
		
		this.currentPlayer=BoardSigns.COMPUTER;
		if(computerStarts) {
			Random rand = new Random();
			this.move.setMove(rand.nextInt(3),rand.nextInt(3));
		}
		else
			minmax(0,BoardSigns.COMPUTER);
		updatePlayerMove(this.move);
		if(!checkGameWon())
			this.currentPlayer=BoardSigns.PLAYER;
	}

	
	public List<GameMove> getAvailableCells()//returns a list of all available cells left in the game
	{
		List<GameMove> availableCells=new ArrayList<>();
		
		for(int i=0;i<this.getRowsLength();i++)
			for(int j=0;j<this.getColsLength();j++) {
				if(getBoardState()[i][j]==BoardSigns.BLANK.getSign()) {
					availableCells.add(new GameMove(i,j));
				}
			}
		
		
		return availableCells;
	}
	
///////minmax uses a scoring algorithm which the computer wants to get higest score,while the he wants the player to get the lowest
///////the algorithm will check all possible cases for all available cells
	public int minmax(int depth,BoardSigns turn)
	{
		
		if(checkGameWon()&&turn==BoardSigns.PLAYER)//if computer won return 1
		{
			return 1;		
		}
		if(checkGameWon()&&turn==BoardSigns.COMPUTER)//if player won return -1
		{
			return -1;
		}
		
		List<GameMove> availableCells=getAvailableCells();
		
		if(availableCells.isEmpty())//if no one won and there are no Available Cells return 0 for tie
			return 0;
		
		int min=Integer.MAX_VALUE;//Setting current min for player to max value of int
		int max=Integer.MIN_VALUE;//Setting current min for computer to max value of int
		
		for(int i=0;i<availableCells.size();i++)
		{
			GameMove move=availableCells.get(i);
			
			if(turn==BoardSigns.COMPUTER)//This turn is for a computer
			{
				updateBoard(move, currentPlayer.getSign());//temporarily update the board for current move
				int currentScore=minmax(depth+1,BoardSigns.PLAYER);//recursion call for next move of the player
				max=Math.max(currentScore, max);//setting max to max score until now
				if(currentScore>=0)//if score has been updated to a non lose case,update move to the currentmove
					if(depth==0)//depth has to be 0 for the current move to be updated
						this.move.setMove(move.getRow(), move.getColumn());
				if(currentScore==1)//found case where computer wins,so this move is optimal,no need to further search
				{
					updateBoard(move, BoardSigns.BLANK.getSign());
					break;
				}
				if(i==availableCells.size()-1&&max<0)//if all moves left leed to lost update board to the left move
					if(depth==0)
						this.move.setMove(move.getRow(), move.getColumn());
			}
			else if(turn==BoardSigns.PLAYER)//This turn is for a player
			{
				updateBoard(move, BoardSigns.PLAYER.getSign());
				int currentScore=minmax(depth+1,BoardSigns.COMPUTER);//recursion call for next move of the computer
				min=Math.min(currentScore, min);
				if(min==-1)//current case will bring a lose to the computer so no furter search is needed
				{
					updateBoard(move, BoardSigns.BLANK.getSign());
					break;
				}
			}
			updateBoard(move, BoardSigns.BLANK.getSign());//restore current cell to BLANK
		}
		if(turn==BoardSigns.COMPUTER)
		{
			return max;
		}
		else return min;	
	}
}
