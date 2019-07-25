package com.hit.games;

import com.hit.gameAlgo.GameBoard;

public abstract class TicTacTow extends GameBoard 
{
	protected BoardSigns currentPlayer;//Indicates whos playing now,and uses his sign to update the board
	protected GameMove move;//Indicates the computer move and is updated by calcComputerMove
	protected boolean boardIsUpdated;
	boolean computerStarts;

	public TicTacTow(int rowLength, int colLength)//Constructor 
	{
		super(rowLength, colLength);
		currentPlayer=BoardSigns.PLAYER;
		move=new GameMove(0,0);
		boardIsUpdated=true;
		this.computerStarts=true;
	}
	public static enum BoardSigns//enum that represents each sign on the board
	{
		BLANK(' '),COMPUTER('X'),PLAYER('O');
		
		private char sign;
		
		private BoardSigns(char sign)
		{
			this.sign=sign;
		}
		
		public char getSign()
		{
			return sign;
		}
		
		public void setSign(char sign)//used to switchSign method,if player wishes to change his sign
		{
			this.sign=sign;
		}
		
	}
	
	//Gets a move,if move is illegal return ILLEGAL_PLAYER_MOVE
	//else,If the game is decided return state in accordance,else game return status IN_PROGRESS
	public GameState getGameState(GameMove move)
	{
		if(!this.boardIsUpdated)
		{
			return GameState.ILLEGAL_PLAYER_MOVE;//computer cannot get to this status,can only make legal moves
		}
		else if(checkGameWon())
			if(currentPlayer.getSign()==BoardSigns.PLAYER.getSign()) {
				return GameState.PLAYER_WON;
			}
			else {
				return GameState.PLAYER_LOST;
			}
		else if(checkGameTie()) {
			return GameState.TIE;
		}
		else {
			this.boardIsUpdated=false;
			return GameState.IN_PROGRESS;
		}
	}
	
	public boolean updatePlayerMove(GameMove move)//Updates the board if move is legal
	{
		if(!legalMove(move))
		{
			this.boardIsUpdated=false;
			return false;
		}
		this.computerStarts=false;
		this.boardIsUpdated=true;
		updateBoard(move, this.currentPlayer.getSign());
		return true;	
	}

	public abstract void calcComputerMove();
	////////
	////////My methods
	///////

	
	protected boolean checkGameWon()//Checks if someone won the game
	{
		char [][]GameBoard=this.getBoardState();
		if((GameBoard[0][0]!=BoardSigns.BLANK.getSign()&&GameBoard[0][0]==GameBoard[0][1] && GameBoard[0][0]==GameBoard[0][2]&& GameBoard[0][1]==GameBoard[0][2])
			||(GameBoard[1][0]!=BoardSigns.BLANK.getSign()&&GameBoard[1][0]==GameBoard[1][1] && GameBoard[1][0]==GameBoard[1][2]&& GameBoard[1][1]==GameBoard[1][2])
			||(GameBoard[2][0]!=BoardSigns.BLANK.getSign()&&GameBoard[2][0]==GameBoard[2][1] && GameBoard[2][0]==GameBoard[2][2]&& GameBoard[2][1]==GameBoard[2][2])
			||(GameBoard[0][0]!=BoardSigns.BLANK.getSign()&&GameBoard[0][0]==GameBoard[1][0] && GameBoard[0][0]==GameBoard[2][0]&& GameBoard[1][0]==GameBoard[2][0])
			||(GameBoard[0][1]!=BoardSigns.BLANK.getSign()&&GameBoard[0][1]==GameBoard[1][1] && GameBoard[0][1]==GameBoard[2][1]&& GameBoard[0][1]==GameBoard[2][1])
			||(GameBoard[0][2]!=BoardSigns.BLANK.getSign()&&GameBoard[0][2]==GameBoard[1][2] && GameBoard[0][2]==GameBoard[2][2]&& GameBoard[1][2]==GameBoard[2][2])
			||(GameBoard[0][0]!=BoardSigns.BLANK.getSign()&&GameBoard[0][0]==GameBoard[1][1] && GameBoard[0][0]==GameBoard[2][2]&& GameBoard[1][1]==GameBoard[2][2])
			||(GameBoard[0][2]!=BoardSigns.BLANK.getSign()&&GameBoard[0][2]==GameBoard[1][1] && GameBoard[0][2]==GameBoard[2][0]&& GameBoard[1][1]==GameBoard[2][0])
				)
			return true;
		else
			return false;
	}
	
	protected boolean checkGameTie()//Checks if board is full
	{
		for(int i=0;i<this.getRowsLength();i++)
		{
			for(int j=0;j<this.getColsLength();j++) {	
				if(this.getBoardState()[i][j]==BoardSigns.BLANK.getSign()) {
					return false;
				}
			}
		}
		return true;
	}
	
	protected boolean legalMove(GameMove move)//Check if move is legal
	{
		if((move.getRow()>2||move.getRow()<0)||(move.getColumn()>2||move.getColumn()<0))
		{
			return false;
		}
		else if(getBoardState()[move.getRow()][move.getColumn()]!=BoardSigns.BLANK.getSign()) {
			return false;
		}
		return true;
	}
	
	public void switchSign()//switch sign between player and computer,used in junit so computer can play verses itself
	{
		char sign=BoardSigns.PLAYER.getSign();
		BoardSigns.PLAYER.setSign(BoardSigns.COMPUTER.getSign());
		BoardSigns.COMPUTER.setSign(sign);
	}	
}

