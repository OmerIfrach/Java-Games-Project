

package com.hit.games;

import com.hit.gameAlgo.GameBoard;

public class CatchTheBunnyRandom extends CatchTheBunny {
	int randomNumber;
	char chosenDirection;
	GameBoard.GameMove computersNewMove=new GameBoard.GameMove(0,0);
	private char directions []=new char[4];
	
	public CatchTheBunnyRandom(int rowLength, int colLength) {
		super(rowLength,colLength, 40);
		setDirectionsArray(directions);
	}
	
	//catch the bunny random always checks a random move as you can see in the function below , it checks if its legal and then update the player move
	
	
	@Override
	public void calcComputerMove() {
		isComputerMove=true;
		do {
			//choosing random number and then a random direction
			randomNumber=(int)(Math.random() * 4);
			chosenDirection=directions[randomNumber];
			//sets the new move according to the new direction
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
			
			
		}while(!isMoveLegal(this.computersNewMove));//if the new move is not legal we need to calculate a new move
		updatePlayerMove(computersNewMove);//update the board
		isComputerMove=false;
		
	}
	
	
}

