package com.hit.ticTacTow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.games.TicTacTow;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;

class TicTacTowTest {
	TicTacTow game;
	GameBoard.GameMove move;
	IGameAlgo.GameState state;
	@BeforeEach
	void setUp() {
		move=new GameBoard.GameMove(0,0);
	}
	@AfterEach
	void tearDown() {
		
	}
	@Test
	void testBoardIsUpdated1() {
		game=new TicTacTowRandom(3,3);
		move.setMove(2,2);
		game.updatePlayerMove(move);
		char [][] table=game.getBoardState();
		assertEquals("Board is not updated",'O',table[2][2]);
	}
	@Test
	void testSignIsSwitched()//check that sign is switched between player and computer,and then switched back
	{
		game=new TicTacTowRandom(3,3);
		game.switchSign();
		move.setMove(0, 0);
		game.updatePlayerMove(move);
		assertEquals("Board is not updated",game.getBoardState()[0][0],'X');
		game.switchSign();
		move.setMove(1, 1);
		game.updatePlayerMove(move);
		assertEquals("Board is not updated",game.getBoardState()[1][1],'O');		
	}
	@Test
	void testUpdateGameStateToWin()//Tests all winning events are covered by checkGameWon and getGameState updates state
	{
		game=new TicTacTowRandom(3,3);
		move.setMove(0,0);
		game.updatePlayerMove(move);
		move.setMove(0,1);
		game.updatePlayerMove(move);
		move.setMove(0,2);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check row 0 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(1,0);
		game.updatePlayerMove(move);
		move.setMove(1,1);
		game.updatePlayerMove(move);
		move.setMove(1,2);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check row 1 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(2,0);
		game.updatePlayerMove(move);
		move.setMove(2,1);
		game.updatePlayerMove(move);
		move.setMove(2,2);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check row 2 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(0,0);
		game.updatePlayerMove(move);
		move.setMove(1,0);
		game.updatePlayerMove(move);
		move.setMove(2,0);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check col 0 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(0,1);
		game.updatePlayerMove(move);
		move.setMove(1,1);
		game.updatePlayerMove(move);
		move.setMove(2,1);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check col 1 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(0,2);
		game.updatePlayerMove(move);
		move.setMove(1,2);
		game.updatePlayerMove(move);
		move.setMove(2,2);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check col 2 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(0,0);
		game.updatePlayerMove(move);
		move.setMove(1,1);
		game.updatePlayerMove(move);
		move.setMove(2,2);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check angle 1 is full
		game=new TicTacTowRandom(3,3);
		move.setMove(0,2);
		game.updatePlayerMove(move);
		move.setMove(1,1);
		game.updatePlayerMove(move);
		move.setMove(2,0);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("PLAYER_WON"));//Check angle 2 is full
	}

	@Test
	void testUpdateGameToTie1() {//Check if board is full witout empty spaces,then get status tie
		game=new TicTacTowRandom(3,3);
		move.setMove(0,0);
		game.updatePlayerMove(move);
		move.setMove(1,1);
		game.updatePlayerMove(move);
		move.setMove(0,2);
		game.updatePlayerMove(move);
		move.setMove(1,0);
		game.updatePlayerMove(move);
		move.setMove(2,1);
		game.updatePlayerMove(move);
		game.switchSign();
		game.updatePlayerMove(move);
		move.setMove(2,0);
		game.updatePlayerMove(move);
		move.setMove(2,2);
		game.updatePlayerMove(move);
		move.setMove(1,2);
		game.updatePlayerMove(move);
		move.setMove(0,1);
		game.updatePlayerMove(move);
		game.switchSign();
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("TIE"));
	}
	@Test
	void testUpdateGameStateToInProgress()//tests if game is finished,status is updated to In Progress
	{
		game=new TicTacTowRandom(3,3);
		move.setMove(0,1);
		game.updatePlayerMove(move);
		assertEquals("Game Status hasent changed to In Progress",game.getGameState(move),GameState.valueOf("IN_PROGRESS"));
	}
	@Test
	void testIllgelMove1()//Check if update player move takes invalid moves where moves are out of board
	{
		game=new TicTacTowRandom(3,3);
		move.setMove(3, 2);
		game.updatePlayerMove(move);
		game.getGameState(move);
		assertEquals("Game Status hasent changed to player won",game.getGameState(move),GameState.valueOf("ILLEGAL_PLAYER_MOVE"));
	}
	@Test
	void testIllgelMove2() //Check if update player move takes invalid moves where moves are out of board
	{
		game=new TicTacTowRandom(3,3);
		move.setMove(1, 3);
		game.updatePlayerMove(move);
		state=game.getGameState(move);
		assertEquals("Game Status hasent changed to player won",state,GameState.valueOf("ILLEGAL_PLAYER_MOVE"));
	}
	@Test
	void testIllgelMove3()//Check if update player move takes invalid moves,where slot is already taken
	{
		game=new TicTacTowRandom(3,3);
		move.setMove(0, 0);
		game.updatePlayerMove(move);
		state=game.getGameState(move);
		move.setMove(0, 0);
		state=game.getGameState(move);
		assertEquals("Game Status hasent changed to player won",state,GameState.valueOf("ILLEGAL_PLAYER_MOVE"));
	}
	@Test
	void testGetBoardFromGame()//Check that game returns its board
	{
		Random rand = new Random();
		for(int k=0;k<10;k++) {
			game=new TicTacTowRandom(3,3);
			move.setMove(rand.nextInt(3),rand.nextInt(3));
			game.updatePlayerMove(move);
			char [][]board=game.getBoardState();
			for(int i=0;i<game.getRowsLength();i++) {
				for(int j=0;j<game.getColsLength();j++)
					assertEquals("The method dosent returns the updated board",board[i][j],game.getBoardState()[i][j]);
			}
		}
	}
	@Test
	void testCalcComputerMoveRandom1() //Fills the board with the calcComputerMove method,and checks game is one by the computer
	{
		game=new TicTacTowRandom(3,3);
		while((state=game.getGameState(move))==GameState.valueOf("IN_PROGRESS")) {
			game.calcComputerMove();
		}
		assertEquals("Computer dosent fill the board right",state,GameState.valueOf("PLAYER_LOST"));
	}
	@Test
	void testCalcComputerMoveRandom2()//Checks a game between the computer and itself
	{
		game=new TicTacTowRandom(3,3);
		Random rand = new Random();
		while((state=game.getGameState(move))==GameState.valueOf("IN_PROGRESS")) {
			move.setMove(rand.nextInt(3),rand.nextInt(3));
			game.updatePlayerMove(move);
			while((state=game.getGameState(move))==GameState.valueOf("ILLEGAL_PLAYER_MOVE")) {
				move.setMove(rand.nextInt(3),rand.nextInt(3));
				game.updatePlayerMove(move);
			}
			if(state!=GameState.valueOf("IN_PROGRESS"))
				break;
			game.calcComputerMove();
		}
		assertNotSame("The game has finished,but something went wrong",GameState.valueOf("IN_PROGRESS"),state);
	}
	@Test
	void testCalcComputerMoveSmart1()//Computer doesn't lose verses a player who plays random move,runs 10 times
	{
		game= new TicTacTowSmart(3,3);
		Random rand = new Random();
		for(int i=0;i<10;i++) {
			while((state=game.getGameState(move))==GameState.valueOf("IN_PROGRESS")) {
				move.setMove(rand.nextInt(3),rand.nextInt(3));
				game.updatePlayerMove(move);
				while((state=game.getGameState(move))==GameState.valueOf("ILLEGAL_PLAYER_MOVE")) {
					move.setMove(rand.nextInt(3),rand.nextInt(3));
					game.updatePlayerMove(move);
				}
				if(state!=GameState.valueOf("IN_PROGRESS"))
					break;
				game.calcComputerMove();
			}
			assertNotSame("Game Status hasent changed to lost won",state,GameState.valueOf("PLAYER_WON"));
			game= new TicTacTowSmart(3,3);
		}
		
	}
	@Test
	void testCalcComputerMoveSmart2()//smart verses smart always ends in tie,check 10 times
	{
		game= new TicTacTowSmart(3,3);
		Random rand = new Random();
		for(int i=0;i<10;i++) {
			move.setMove(rand.nextInt(3),rand.nextInt(3));
			game.updatePlayerMove(move);
			while((state=game.getGameState(move))==GameState.valueOf("IN_PROGRESS")) {
				game.calcComputerMove();
				game.switchSign();
				game.calcComputerMove();
				game.switchSign();
			}
			assertEquals("Computer dosent fill the board right",state,GameState.valueOf("TIE"));
			game= new TicTacTowSmart(3,3);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
