package com.hit.view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


	public class GamesView implements View,PropertyChangeListener {
		
		
		private JFrame mainFrame;
		private MenuPanel menuPanel;
		private GamePanel gamePanel;
		private String game;
		private String type;
		private final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		private final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		boolean isGameOn = false;
		private PropertyChangeSupport propertyChangeHandler;
		public GamesView() {
			initialize();
		}
		
		
		//////controller will listen to the view changes//////
		public void setPropertyChangeSupport() {
			propertyChangeHandler = new PropertyChangeSupport(this);
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeHandler.addPropertyChangeListener(listener); }
		

		@Override
		public void start() {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						mainFrame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setPropertyChangeSupport();
		
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, (int)(WIDTH*0.55), (int)(HEIGHT*0.9));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		menuPanel = new MenuPanel((int)(HEIGHT*0.9),(int)(WIDTH*0.55));
		menuPanel.setLocation(0, 0);
		gamePanel=new GamePanel((int)(HEIGHT*0.9),(int)(WIDTH*0.55));
		gamePanel.setLocation(0,0);
		mainFrame.add(menuPanel);

		
		menuPanel.addPropertyChangeListener(this);
		gamePanel.addPropertyChangeListener(this);
		
		mainFrame.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	if(isGameOn) {
		    		propertyChangeHandler.firePropertyChange("EndGameEventView",0,1);
		    	}
		    }
		});
		
	}
	
	@Override
	public void updateViewNewGame(Character[] board) {
		if(game.equals("TIC TAC TOE")) {
			gamePanel.initializeTicTacToe(type, (int) Math.sqrt(board.length));
		}
		else {
			gamePanel.initializeCatchTheBunny(type, (int) Math.sqrt(board.length));
		}
		isGameOn=true;
		gamePanel.updateViewNewGame(board);
		mainFrame.setContentPane(gamePanel);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	@Override
	public void updateViewGameMove(int gameState, Character[] board) {
		gamePanel.updateViewGameMove(gameState,board);
	}
	
	@Override
	public void connectionError(String err) {
		menuPanel.showErrorMessage(err);
	}
	
	 public static void main(String[] args)  {
		 GamesView view=new GamesView();
		 view.start();
	 }


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName()=="EndGameEventView") {
			isGameOn=false;
			mainFrame.setContentPane(menuPanel);
			mainFrame.revalidate();
			mainFrame.repaint();
			propertyChangeHandler.firePropertyChange("EndGameEventView",0,1);
		}
		else if(event.getPropertyName()=="NewGameEventMView")
		{
			type=(String) event.getNewValue();
			game=(String) event.getOldValue();
			propertyChangeHandler.firePropertyChange("NewGameEventMView",game,type);
			
		}
		else if(event.getPropertyName()=="GameFinishedEvent")
		{
			isGameOn=false;
			mainFrame.setContentPane(menuPanel);
			mainFrame.revalidate();
			mainFrame.repaint();
		}
		else
		{
			propertyChangeHandler.firePropertyChange(event.getPropertyName(),event.getOldValue(),event.getNewValue());	
		}
	}

}
