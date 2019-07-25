package com.hit.catchTheBunny;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.games.CatchTheBunny.BoardSigns;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;


class CatchTheBunnyTest {

	
	CatchTheBunnyRandom game;
	GameBoard.GameMove move;
	GameBoard.GameMove playerPosition;
	GameBoard.GameMove computerPosition;
	int rows;
	int cols;
	char board[][];

	@BeforeEach
	void setUp() {
		game=new CatchTheBunnyRandom(9,9);
		playerPosition=game.getPlayerPosition();
		computerPosition=game.getComputerPosition();
		move=new GameBoard.GameMove(playerPosition.getRow(),playerPosition.getColumn());
		board=game.getBoardState();
	}
	@AfterEach
	void tearDown() {
		
	}
	@Test
	void testInintializationOfBoard() {
		assertEquals("player sign is NOT initialize",board[playerPosition.getRow()][playerPosition.getColumn()],'P');
		assertEquals("computer sign is NOT initialize",board[computerPosition.getRow()][computerPosition.getColumn()],'B');
	}
	@Test
	void testUpdateBoard() {
		this.move.setMove(0, 0);
		game.updateBoard(move, 'H');
		assertEquals("cant update board move",board[0][0],'H');

	}
	@Test
	void testUpdatePlayerMove() {
		this.rows=playerPosition.getRow();
		this.cols=playerPosition.getColumn()+1;
		move.setMove(rows,cols);
		game.updatePlayerMove(move);
		//checks if the sign of the player is in the new move position
		assertEquals("cant update new move problem at updatePlayerMove",board[rows][cols],BoardSigns.PLAYER.getSign());
		//checks if the older position is exchanged with empy sign
		assertEquals("cant update new move problem at updatePlayerMove",board[rows][cols-1],BoardSigns.BLANK.getSign());
	}
	@Test
	void testGetDirection() {
		char direction;
		move.setMove(5, 5);
		GameBoard.GameMove move2=new GameBoard.GameMove(5,6);
		direction=game.getDirection(move,move2);
		assertEquals("direction is wrong",direction,'D');
		move2.setMove(5, 4);
		direction=game.getDirection(move,move2);
		assertEquals("direction is wrong",direction,'A');
		move2.setMove(4, 5);
		direction=game.getDirection(move,move2);
		assertEquals("direction is wrong",direction,'W');
		move2.setMove(6, 5);
		direction=game.getDirection(move,move2);
		assertEquals("direction is wrong",direction,'S');
		
	}
	
	@Test 
	void testCalculateDistance() {
		double distance; double rightDistance;
		GameBoard.GameMove move2=new GameBoard.GameMove(playerPosition.getRow(),playerPosition.getColumn()+1);
		distance=game.calculateDistance(playerPosition, move2);
		rightDistance=1;
		assertEquals("distance is wrong",distance,rightDistance,0.1);
		move2.setMove(playerPosition.getRow()+1, playerPosition.getColumn()+1);
		distance=game.calculateDistance(move2, move);
		rightDistance=Math.sqrt(2);
		assertEquals("distance is wrong",distance,rightDistance,0.1);

	}
	
	@Test
	void testIsMoveLegal() {
		this.rows=playerPosition.getRow();
		this.cols=playerPosition.getColumn();
		move.setMove(rows, cols+1);
		assertEquals("move suppose to be legal",true,game.isMoveLegal(move));
		move.setMove(rows, cols+2);
		assertEquals("move suppose to be illegal",false,game.isMoveLegal(move));
		move.setMove(rows+1, cols+1);
		assertEquals("move suppose to be illegal",false,game.isMoveLegal(move));

	}
	
	@Test
	void testGetGameState() {
		this.move.setMove(30, 30);
		assertEquals("gamestate should be illegal move",GameState.ILLEGAL_PLAYER_MOVE,game.getGameState(move));
		this.move.setMove(playerPosition.getRow(), playerPosition.getColumn()+1);
		assertEquals("gamestate should be in progress",GameState.IN_PROGRESS,game.getGameState(move));
		this.move.setMove(playerPosition.getRow(), playerPosition.getColumn());
		assertEquals("gamestate should be illegal because its the move of the player position",GameState.ILLEGAL_PLAYER_MOVE,game.getGameState(move));
		this.move.setMove(30, 30);
		assertEquals("gamestate should be illegal move",GameState.ILLEGAL_PLAYER_MOVE,game.getGameState(move));
				for(int i=0;i<22;i++) {
		this.move.setMove(playerPosition.getRow()+1, playerPosition.getColumn());
		game.updatePlayerMove(move);
		this.move.setMove(playerPosition.getRow()-1, playerPosition.getColumn());
		game.updatePlayerMove(move);
		}
		this.move.setMove(playerPosition.getRow()+1, playerPosition.getColumn());
		game.updatePlayerMove(move);
		assertEquals("gamestate should be player lost because we past 20 turns",GameState.PLAYER_LOST,game.getGameState(move));

	}
	@Test
	void testGetGameStateWon() {
		this.move.setMove(4,3);
		game.updatePlayerMove(move);
		this.move.setMove(4,4);
		game.updatePlayerMove(move);
		this.move.setMove(4,5);
		game.updatePlayerMove(move);
		this.move.setMove(4,6);
		game.updatePlayerMove(move);
		assertEquals("gamestate should be player has won",GameState.PLAYER_WON,game.getGameState(move));


		
	}
	
	@Test 
	void testRandomComputerMove(){
		int sumOfOldRowCols,sumOfNewRowCols;
		GameBoard.GameMove computerPreviousMove;
		computerPreviousMove=new GameBoard.GameMove(this.computerPosition.getRow(), this.computerPosition.getColumn());
		game.calcComputerMove();
		sumOfOldRowCols=computerPreviousMove.getRow()+computerPreviousMove.getColumn();
		sumOfNewRowCols=computerPosition.getColumn()+computerPosition.getRow();
		assertNotEquals("computer random move has failed",sumOfNewRowCols,sumOfOldRowCols);

	}
	
	@Test
	void testSmartCompuerMove() {
		CatchTheBunnySmart gameSmart=new CatchTheBunnySmart(9,9);
		GameBoard.GameMove computerSmartPosition=gameSmart.getComputerPosition();
		GameBoard.GameMove computerPreviousMove=new GameBoard.GameMove(computerSmartPosition.getRow(), computerSmartPosition.getColumn());
		gameSmart.calcComputerMove();
		int sumOfOldRowCols = computerPreviousMove.getRow()+computerPreviousMove.getColumn();
		int sumOfNewRowCols = computerSmartPosition.getColumn()+computerSmartPosition.getRow();
		assertNotEquals("computer random move has failed",sumOfNewRowCols,sumOfOldRowCols);

		
	}
	
	
	
	
	
	
}

