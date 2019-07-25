package com.hit.games;
import java.lang.Math;

import com.hit.gameAlgo.GameBoard;


public abstract class CatchTheBunny extends GameBoard  {
	protected GameBoard.GameMove playerPosition; 
	protected GameBoard.GameMove computerPosition;
	private GameBoard.GameMove previousPosition;//previous position of the current player
	private GameBoard.GameMove otherPlayerPosition; //the position of the other player
	private int numOfAvailableRounds;
	private int currentNumOfRounds;
	private char currentDirection; // current direction is calculated according to the current move and previous player position
	private boolean isBoardUpdated;
	protected boolean isComputerMove;
	GameState state;
	
	
	CatchTheBunny(int rowLength, int colLength,int numOfAvailableRounds) {
		super(rowLength,colLength);
		this.currentNumOfRounds=0;
		this.isComputerMove=false;
		this.isBoardUpdated=false;
		//set the current gamemove to 0,0
		playerPosition = new GameBoard.GameMove(4,2);
		computerPosition = new GameBoard.GameMove(4, 6);
		//initializing the board
		setBoard(rowLength,colLength);
		//
		this.numOfAvailableRounds=numOfAvailableRounds;
		
		
	}
	
	//enum that represents each sign on the board
	public static enum BoardSigns{
		BLANK(' '),COMPUTER('B'),PLAYER('P');
		
		private final char sign;
		
		BoardSigns(char sign){
			this.sign=sign;
		}
		
		public char getSign() {
			return sign;
		}
		
	}
	
	//initializing the board with the computer and player signs
	public void setBoard(int rowLength, int colLength) {
		
		char computerSign=BoardSigns.COMPUTER.getSign();
		char playerSign=BoardSigns.PLAYER.getSign();
		
		updateBoard(playerPosition,playerSign);
		updateBoard(computerPosition,computerSign);
	}
	
	
	@Override
	public GameState getGameState(GameMove move) {//return the game state according to the current move
		if(this.numOfAvailableRounds>this.currentNumOfRounds)//checks if we didnt pass the available rounds
		{
			if(checkGameWin()==GameState.PLAYER_WON) {
				return GameState.PLAYER_WON;
			}
			else if(!this.isBoardUpdated){
				if(!isMoveLegal(move))//check if the move is legal
					return GameState.ILLEGAL_PLAYER_MOVE;
				else {
					return GameState.IN_PROGRESS;
				}	
			}
			else return GameState.IN_PROGRESS;
		}
		else 
			return checkGameWin();//checks if the player or the computer has won
	}
	
	//checks if the player has won the game
	public GameState checkGameWin() {
		if(computerPosition.getColumn()==playerPosition.getColumn()&&computerPosition.getRow()==playerPosition.getRow())
			return GameState.PLAYER_WON;
		else {
			return GameState.PLAYER_LOST;
		}
	}

	@Override
	public boolean updatePlayerMove(GameMove move) {
		//indicates if the board has changed
		boolean isBoardChanged=false;
		//board is not updated , we need to change it to false so the get game state function will work
		//as it should
		isBoardUpdated=false;
		//checking if the move is legal
		if(getGameState(move)==GameState.IN_PROGRESS) {
		this.isBoardUpdated=true;
		//saves the current players move
		this.previousPosition=this.isComputerMove?this.computerPosition:this.playerPosition;
		this.otherPlayerPosition=this.isComputerMove?this.playerPosition:this.computerPosition;
		//check if it is the turn of the computer or the player and get the needed sign
		char currentMoveSign=getBoardSign(previousPosition);
		//check in which direction we need to move according to the current move
		this.currentDirection=getDirection(this.previousPosition,move);
		switch (this.currentDirection) {
        case 'W'://up
        	{
            	updateBoard(this.previousPosition,BoardSigns.BLANK.getSign());
            	//if the bunny position and the computer position is the same we want to change the sign to P ( player sign)
            	updateBoard(move,move.getRow()==otherPlayerPosition.getRow()&&move.getColumn()
            			==otherPlayerPosition.getColumn()?'P':currentMoveSign);
            	this.currentNumOfRounds++;
            	isBoardChanged=true;
            	break;
            }
          
        case 'S'://down
        	{
        		updateBoard(this.previousPosition,BoardSigns.BLANK.getSign());
        		//if the bunny position and the computer position is the same we want to change the sign to P ( player sign)
        		updateBoard(move,move.getRow()==otherPlayerPosition.getRow()&&move.getColumn()
            			==otherPlayerPosition.getColumn()?'P':currentMoveSign);
        		this.currentNumOfRounds++;
        		isBoardChanged=true;
        		break;
        	}
        	
        case 'A'://left
        	{
        		updateBoard(this.previousPosition,BoardSigns.BLANK.getSign());
        		//if the bunny position and the computer position is the same we want to change the sign to P ( player sign)
        		updateBoard(move,move.getRow()==otherPlayerPosition.getRow()&&move.getColumn()
            			==otherPlayerPosition.getColumn()?'P':currentMoveSign);
            	this.currentNumOfRounds++;
            	isBoardChanged=true;
            	break;
        	}
            
        case 'D'://right
        	{
        		updateBoard(this.previousPosition,BoardSigns.BLANK.getSign());
        		//if the bunny position and the computer position is the same we want to change the sign to P ( player sign)
        		updateBoard(move,move.getRow()==otherPlayerPosition.getRow()&&move.getColumn()
            			==otherPlayerPosition.getColumn()?'P':currentMoveSign);
            	this.currentNumOfRounds++;
            	isBoardChanged=true;
            	break;
        	}
                    
        default:
        	return false;
		}
	}
		if(isBoardChanged) {	
			//if the board has changed update the new player/computer position
			previousPosition.setMove(move.getRow(), move.getColumn());
			return true;
		}
		else return false;
			
			
	}
	


		//calculates the right direction between an old move and a new move;
		public char getDirection(GameMove previousMove, GameMove move) {///////////can be private
		if(previousMove.getColumn()+1==move.getColumn())
			return 'D';
		else if(previousMove.getColumn()-1==move.getColumn())
			return 'A';
		else if(previousMove.getRow()-1==move.getRow())
			return 'W';
		else return 'S';
	}


	public boolean isMoveLegal(GameMove move) 
	{
		double distance;
		int cols=move.getColumn();
		int rows=move.getRow();
		//check if the move is in the range of the rows and cols of the board
		if(cols<0||cols>(getColsLength()-1)||rows<0||rows>(getRowsLength()-1))
			return false;
		this.previousPosition=isComputerMove?computerPosition:playerPosition;
		//check if the the next move is similar to the player position
		if(move.getColumn()==previousPosition.getColumn()&&move.getRow()==previousPosition.getRow())
			return false;
		distance=calculateDistance(move,previousPosition);
		//if the distance equals to 1 it means that the move is legal (we moved to up down right or left)
		if(distance==1) return true;
			else return false;
		
	}
	//calculating the distance of two dots from the board.
	public double calculateDistance(GameMove move,GameMove otherMove) {
		double distance;
		distance = Math.sqrt(Math.pow(otherMove.getColumn()-move.getColumn(), 2)+Math.pow(otherMove.getRow()-move.getRow(), 2));
		return distance;
	}
	
	public GameMove getPlayerPosition() {
		return this.playerPosition;
	}
	
	public GameMove getComputerPosition() {
		return this.computerPosition;
	}
	
	public void setDirectionsArray(char directions []) {//creating direction array of chars
		directions[0]='W';
		directions[1]='A';
		directions[2]='S';
		directions[3]='D';
		
	}
	
}
	
