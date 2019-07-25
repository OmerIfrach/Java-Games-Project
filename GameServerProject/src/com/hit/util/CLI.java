package com.hit.util;
import java.util.Scanner;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class CLI implements Runnable {
	private InputStream in;
	private OutputStream out;
	Scanner scan;
	private volatile boolean running=true;
	
	PropertyChangeSupport propertyChangeHandler;
	
	public CLI(InputStream in, OutputStream out) {///constructor
		this.in=in;
		this.out=out;
		propertyChangeHandler=new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {//observer functions
		propertyChangeHandler.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {//observer functions
		propertyChangeHandler.removePropertyChangeListener(pcl);
	}
	
	public void writeResponse(String response) {//write response to the CLI according to the command 
		try {
		if(response.equals("GAME_SERVER_CONFIG")) {
			out.write("\n".getBytes());
		}
		else if(response.equals("START")) {
			out.write("Starting server...\n".getBytes());
		}
		else if(response.equals("SHUTDOWN")) {
			out.write("Shutdown server\n".getBytes());
		}
		else {
			out.write("Not a valid command\n".getBytes());
		}
		}
		catch(IOException err){
			err.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.scan= new Scanner(this.in);
		String command;
		String[] commandArray;
		//cli will update the server about the admin commands using observer design pattern
		while(this.running) {
			try {
				this.out.write("Please enter your command\n".getBytes());
				command=scan.nextLine(); // recieving command from the use
				commandArray=command.split(" ");//split the command to array
				//check the first command
				if(commandArray[0].equals("START")) {
					propertyChangeHandler.firePropertyChange("START",0,1);
				}
				else if(commandArray[0].equals("GAME_SERVER_CONFIG"))
				{
					if(commandArray.length>1)
						// if length is greater than 1 = the user declare how many games he want the server will run
						propertyChangeHandler.firePropertyChange("GAME_SERVER_CONFIG",0,Integer.parseInt(commandArray[1]));
					else
						//in case the user dont declare the capacity
						propertyChangeHandler.firePropertyChange("GAME_SERVER_CONFIG",0,3);
				}
				else if(commandArray[0].equals("SHUTDOWN")) {
					propertyChangeHandler.firePropertyChange("SHUTDOWN",0,1);
					running=false; // break the while loop wich makes CLI to end.
				}
				writeResponse(commandArray[0]);
			} catch (IOException e) {
				writeResponse("Somthing went Wrong...");
				e.printStackTrace();
			}
		}
	}
}
