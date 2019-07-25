package com.hit.games;

import com.hit.gameAlgo.GameBoard;

public class CatchTheBunnySmart extends CatchTheBunny{
	
	char chosenDirection;
	GameBoard.GameMove computersNewMove;
	GameBoard.GameMove computersFinalMove;
	private char directions []=new char[4];
	private double maxDistance;
	private double distance;
	GameMove computerPosition;
	GameMove playerPosition;
	
	public CatchTheBunnySmart(int rowLength, int colLength) {
		super(rowLength, colLength, 20);
		setDirectionsArray(directions);
		computerPosition=getComputerPosition();
		computersNewMove=new GameBoard.GameMove(0,0);
		computersFinalMove=new GameBoard.GameMove(0,0);
		playerPosition=getPlayerPosition();
	}
	//catch the bunny smart choose the computers move by calculating the longest distance between the possible new computer move and the player position
	//and then chooses the move wich gives the biggest distance
	
	@Override
	public void calcComputerMove() {
		isComputerMove=true;
		for(int i=0;i<4;i++) {
			chosenDirection=directions[i];
			switch(chosenDirection) {
			case 'A'://up
				computersNewMove.setMove(computerPosition.getRow(), computerPosition.getColumn()-1);
				break;
			case 'D'://down
				computersNewMove.setMove(computerPosition.getRow(), computerPosition.getColumn()+1);
				break;
			case 'W'://left
				computersNewMove.setMove(computerPosition.getRow()-1, computerPosition.getColumn());
				break;
			case 'S'://right
				computersNewMove.setMove(computerPosition.getRow()+1, computerPosition.getColumn());
				break;
			}
			//if thr move is legal check if the new distance is bigger then the max distance
			if(isMoveLegal(computersNewMove)) {				
				distance=calculateDistance(this.computersNewMove,this.playerPosition);
				if(this.distance>this.maxDistance)
				{
					//if current distance is bigger we want to change the computers final move
					this.maxDistance=this.distance;
					this.computersFinalMove.setMove(this.computersNewMove.getRow(), this.computersNewMove.getColumn());
				}
			}
			
			
		}
		//update computers final move
		updatePlayerMove(computersFinalMove);
		isComputerMove=false;
		this.maxDistance=0;
	}

}
