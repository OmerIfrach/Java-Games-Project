package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class GamesClient {
	int serverPort;
	Socket gamesServer;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	GamesClient(int serverPort) 
	{
		this.serverPort=serverPort;
	}
	
	
	public void closeConnection() 
	{
		try {
			gamesServer.close();
			output.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void connectServer() throws Exception
	{
		try {
			InetAddress Address=InetAddress.getByName("192.168.1.19");
			gamesServer=new Socket(Address,serverPort);
			output=new ObjectOutputStream(gamesServer.getOutputStream());
			input=new ObjectInputStream(gamesServer.getInputStream());
		} catch (IOException e) {
			throw new Exception("Fail to connect server\nServer is down,Please try again later"); 
		}
	}
	
	
	public String sendMessage(String message,boolean hasResponse)
	{
		try {
			output.writeObject(message);
			if(!hasResponse)
			{
				return null;
			}
			else
			{
				gamesServer.setSoTimeout(5000);
				return (String)input.readObject();

			}
		} catch (IOException e) {
			closeConnection(); 
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "1";
		}
	}

}
