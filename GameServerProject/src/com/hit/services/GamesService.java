package com.hit.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.gameHandler.BoardGameHandler;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;



public class GamesService {
	private int capacity;
	private ConcurrentHashMap<Integer,BoardGameHandler> runningGames;
	private ConcurrentHashMap<Integer,String> idToGameString;
	private ConcurrentHashMap<Integer,String> idToOpponent;
	private AtomicInteger currentGamesCapacity;
	private int restartIdGame; // if user wants to restart the game we save here his id
	boolean isNewIdNeeded;
	
	
	public GamesService(int capacity) {
		this.capacity=capacity; //capacity of games that the server can run
		this.currentGamesCapacity= new AtomicInteger(0);
		this.runningGames=new ConcurrentHashMap<Integer,BoardGameHandler>();//hashmap of all the games
		this.idToGameString = new ConcurrentHashMap<Integer,String>();//hashmap of ids and string of the matching games name
		this.idToOpponent = new ConcurrentHashMap<Integer,String>(); //hashmap of ids and string of the matching games type
		this.isNewIdNeeded=true;// if its a new game new id is needed , if we want to restart we dont need new id
		
		
		
	}
	
	synchronized public int newGame(String gameType, String opponent) {
		int id;
		IGameAlgo game=null;
		//if the current capacity equals to servers capacity it means that server dont need to accept a new game
		//be aware that current games capacity starts from 0
		if(this.currentGamesCapacity.get()==this.capacity)//
			return -1;
		else if(isNewIdNeeded)//if we dont want to restart our game we need to check for new id
			id=checkAvailableId();
			else id=restartIdGame; // if we want to restart the game we take the previous user id
		//creating new tic tac toe games according to the users request
		//if the request is not correct we return -1
		if(gameType.equals("TIC TAC TOE")) {
			if(opponent.equals("RANDOM")) {
				game=new TicTacTowRandom(3,3);
				idToGameString.put(id, "TIC TAC TOE");
				idToOpponent.put(id, "RANDOM");
			}
			else if(opponent.equals("SMART")){
				game=new TicTacTowSmart(3,3);
				idToGameString.put(id, "TIC TAC TOE");
				idToOpponent.put(id, "SMART");
			}
			else {
				return -1;
			}
		}
		//creating new catch the bunny games according to the users request
				//if the request is not correct we return -1
		else if(gameType.equals("CATCH THE BUNNY"))
		{
		if(opponent.equals("RANDOM")) {	
				game=new CatchTheBunnyRandom(9,9);
				idToGameString.put(id, "CATCH THE BUNNY");
				idToOpponent.put(id, "RANDOM");
			}
			else if(opponent.equals("SMART")) {
				game=new CatchTheBunnySmart(9,9);
				idToGameString.put(id, "CATCH THE BUNNY");
				idToOpponent.put(id, "SMART");
			}
			else {
				return -1;
			}
			
		}
			
			this.currentGamesCapacity.incrementAndGet();
			//creating new gameboard handler for the users id
			this.runningGames.put(id, new BoardGameHandler(game));
			return id;

	}
	// check which id is available between 1 and servers capacity 
	//and returns -1 if there isn't a available space
	private int checkAvailableId() {
		for(int i=1;i<=this.capacity;i++) {
			if(!this.runningGames.containsKey(i)) {
				return i;
			}
		}
		return -1;
	}
	
	///the below methods use gameboard hanler methods according to the users id using our runningGames hashmap
	//if the users id doesnt exist in our hashmap we throw unknownIdException.
	
	public GameState updateMove(Integer gameId,GameMove playerMove) throws UnknownIdException{
		if(!this.runningGames.containsKey(gameId))
			throw new UnknownIdException(new Exception(),"No such game exists");
		return this.runningGames.get(gameId).playOneRound(playerMove);
	}
	
	public char[][] getBoardState(Integer gameId)throws UnknownIdException{
			if(!this.runningGames.containsKey(gameId))
				throw new UnknownIdException(new Exception(),"No such game exists");
		return this.runningGames.get(gameId).getBoardState();
	}
	
	public char[][] computerStartGame(Integer gameId)throws UnknownIdException{
		if(!this.runningGames.containsKey(gameId))
			throw new UnknownIdException(new Exception(),"No such game exists");
		return this.runningGames.get(gameId).computerStartGame();
	}
	
	public void endGame(Integer gameId) throws UnknownIdException{
		if(!this.runningGames.containsKey(gameId))
			throw new UnknownIdException(new Exception(),"No such game exists");
		this.runningGames.remove(gameId); //remove the game from our hashmap
		this.idToGameString.remove(gameId); // remove game name string from hashmap
		this.idToOpponent.remove(gameId); //remove opponent from hashmap
		this.currentGamesCapacity.decrementAndGet();
	}

	synchronized public void restartGame(int gameId) throws UnknownIdException {
		if(!this.runningGames.containsKey(gameId))
			throw new UnknownIdException(new Exception(), "No such game exists");
		runningGames.remove(gameId);//remove the old game from hashmap
		this.isNewIdNeeded=false;//we dont need new id because we want to use the same id for the user
		this.restartIdGame=gameId; // saves the user previous id to a variable
		newGame(this.idToGameString.get(gameId), this.idToOpponent.get(gameId));//create new game with the same user id
		this.isNewIdNeeded=true;	//now we want to generate new id for new games
	}
}