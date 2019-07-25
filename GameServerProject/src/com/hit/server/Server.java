package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hit.services.GameServerController;




public class Server implements PropertyChangeListener, Runnable {
	private int capacity;
	private ServerSocket server;
	private boolean isAvailable;
	private GameServerController gameController;
	private Executor executor;
	
	public Server(int port) {
		try {
			server=new ServerSocket(port);
		} catch (IOException err) {
			err.printStackTrace();
			
		}
	}

	@Override
	public void run() {
		//limit the possible users that can connect to the server using fixedthreadpoll
		this.executor=Executors.newFixedThreadPool (this.capacity);
		//creating one game server controller for all the users
		gameController=new GameServerController(this.capacity);
		Socket someClient;
		while(this.isAvailable) 
		{
			
		try {
			//if no one connect to the server and the admin send shutdown command to the CLI
			//we need to make time out to the server so it can exit from the loop
			this.server.setSoTimeout(10000);
			someClient=this.server.accept();
			//create new thread of handlerequest
			//the thread will handle all the requests of the current user till the game will end.
			HandleRequest handler=new HandleRequest(someClient,gameController); 
			executor.execute (handler);
			} catch (SocketException err) {
				
			} catch (IOException err) {
				
			}

			
		}
		((ExecutorService) executor).shutdownNow(); //Will close the server only after all games are finished
		
	}

	//observer functions 
	//the server wants to be updated if the admin entered commands
	//the server will be updated from the cli thread using observer design pattern
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("GAME_SERVER_CONFIG")){
			this.capacity=(int) evt.getNewValue();	
			gameController=new GameServerController(this.capacity);
			executor =Executors.newFixedThreadPool (this.capacity);
			}
		else if(evt.getPropertyName().equals("START")){
			this.isAvailable=true;
			new Thread(this).start();
		}
		else if(evt.getPropertyName().equals("SHUTDOWN")) {
			this.isAvailable=false;
		}
		
	}
	

}

