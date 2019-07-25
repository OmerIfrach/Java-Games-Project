package com.hit.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox comboBoxGame;
	private JComboBox comboBoxType;
	private ArrayList<JButton> buttons;
	JButton button;
	PropertyChangeSupport propertyChangeHandler;

	public GamePanel(int height ,int width) {

		this.setPropertyChangeSupport();
		setBounds(100, 100, height, width);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		
	}
	
	public void initializeCatchTheBunny(String type ,int length) {
		removeAll();
		JLabel lblGame = new JLabel("Catch The Bunny");
		lblGame.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblGame.setBounds(309, 40, 217, 59);
		add(lblGame);
		
		JButton btnEndGame = new JButton("End Game");
		btnEndGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				propertyChangeHandler.firePropertyChange("EndGameEventView",0,1);
			}
		});
		btnEndGame.setBounds(651, 665, 97, 25);
		add(btnEndGame);
		
		JButton btnRestartGame = new JButton("Restrart");
		btnRestartGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				propertyChangeHandler.firePropertyChange("RestartEventView",0,1);
			}
		});
		btnRestartGame.setBounds(651, 635, 97, 25);
		add(btnRestartGame);
		
		JLabel lblType = new JLabel(type);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblType.setBounds(386, 99, 85, 22);
		add(lblType);
		buttons=new ArrayList<JButton>();
		
		for(int i=0;i<length;i++) {
			for(int j=0;j<length;j++) {
				int innerI=i;
				int innerJ=j;
				button = new JButton("");
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						makeMove(innerI+" "+innerJ);
					}
				});
				button.setBounds(190+j*50, 172+i*50, 50, 50);
				add(button);
				buttons.add(button);
				
			}
		}
		
	}
	
	public void initializeTicTacToe(String type ,int length) {
		removeAll();
		JLabel lblGame = new JLabel("Tic Tac Toe");
		lblGame.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblGame.setBounds(324, 60, 217, 59);
		add(lblGame);
		
		JButton btnEndGame = new JButton("End Game");
		btnEndGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				propertyChangeHandler.firePropertyChange("EndGameEventView",0,1);
			}
		});
		btnEndGame.setBounds(651, 665, 97, 25);
		add(btnEndGame);
		
		JButton btnRestartGame = new JButton("Restrart");
		btnRestartGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				propertyChangeHandler.firePropertyChange("RestartEventView",0,1);
			}
		});
		btnRestartGame.setBounds(651, 635, 97, 25);
		add(btnRestartGame);
		
		JLabel lblType = new JLabel(type);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblType.setBounds(386, 143, 85, 34);
		add(lblType);
		buttons=new ArrayList<JButton>();
		
		for(int i=0;i<length;i++) {
			for(int j=0;j<length;j++) {
				int innerI=i;
				int innerJ=j;
				button = new JButton("");
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						makeMove(innerI+" "+innerJ);
					}
				});
				button.setBounds(263+j*98, 305+i*98, 98, 98);
				add(button);
				buttons.add(button);
				
			}
		}
		
	}
	
	
	private void makeMove(String coordinates) {
		propertyChangeHandler.firePropertyChange("UpdatePlayerMoveView", "", coordinates);
	}
	
	
	public void updateViewNewGame(Character[] board) {
		Image bunny=new ImageIcon(this.getClass().getResource("/Icons/bunny1.png")).getImage();
		Image hunter=new ImageIcon(this.getClass().getResource("/Icons/hunter.png")).getImage();
		Image eks=new ImageIcon(this.getClass().getResource("/Icons/eks.png")).getImage();
		if(board!=null) {
			for(int i=0;i<buttons.size();i++) {
				if(board[i].toString().equals("B"))
				{
					//button.setIcon(new ImageIcon(getScaledImage(bunny)));
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(bunny,25)));
				}
				else if(board[i].toString().equals("P"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(hunter,25)));
				}
				else if(board[i].toString().equals("X"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(eks,40)));
				}
				else
				{
				buttons.get(i).setText((board[i].toString()));
				}
			}
		}
		
	}

	public void updateViewGameMove(int gameState, Character[] board) {
			if(gameState==1) {
				updateBoard(board);
			}
			else if (gameState==2) {
				updateBoard(board);
				JOptionPane.showMessageDialog(this,"You have lost the game!");
				gameFinished();
			}
			else if(gameState==3) {
				updateBoard(board);
				JOptionPane.showMessageDialog(this, "You have won the game!");
				gameFinished();
			}
			else if(gameState==4) {
				updateBoard(board);
				JOptionPane.showMessageDialog(this, "Thats a tie!");
				gameFinished();
			}
			else if(gameState==0) {
				JOptionPane.showMessageDialog(this, "Illegal player move");
			}
		
		
	}
	
	private void gameFinished() {
		propertyChangeHandler.firePropertyChange("GameFinishedEvent",0,1);
		
	}

	private void updateBoard(Character[] board) {
		Image bunny=new ImageIcon(this.getClass().getResource("/Icons/bunny1.png")).getImage();
		Image hunter=new ImageIcon(this.getClass().getResource("/Icons/hunter.png")).getImage();
		Image circle=new ImageIcon(this.getClass().getResource("/Icons/circle.png")).getImage();
		Image eks=new ImageIcon(this.getClass().getResource("/Icons/eks.png")).getImage();
		if(board!=null) {
			for(int i=0;i<buttons.size();i++) {
				if(board[i].toString().equals("B"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(bunny,20)));
					buttons.get(i).setText(null);
				}
				else if(board[i].toString().equals("P"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(hunter,20)));
					buttons.get(i).setText(null);
				}
				else if(board[i].toString().equals("X"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(eks,40)));
				}
				else if(board[i].toString().equals("O"))
				{
					buttons.get(i).setIcon(new ImageIcon(getScaledImage(circle,40)));
				}
				else
				{
				buttons.get(i).setText((board[i].toString()));
				buttons.get(i).setIcon(null);
				}
			}
		}
	}
	
	////////////observer functions//////////////////
	
	public void setPropertyChangeSupport() {
		propertyChangeHandler = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeHandler.addPropertyChangeListener(listener); }
	
	
	///////////////////
	private Image getScaledImage(Image srcImg,int size){
	    BufferedImage resizedImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, size, size, null);
	    g2.dispose();

	    return resizedImg;
	}
}






