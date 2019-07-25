package com.hit.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox comboBoxGame;
	private JComboBox comboBoxType;
	private JComboBox comboBoxWhoseStarting;

	private final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	PropertyChangeSupport propertyChangeHandler;

	public MenuPanel(int height,int width) {

		this.setPropertyChangeSupport();
		setBounds(100, 100, height, width);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblGamesserverclient = new JLabel("GamesServerClient");
		lblGamesserverclient.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblGamesserverclient.setBounds(296, 85, 361, 38);
		add(lblGamesserverclient);
		
		JLabel lblChooseYourGame = new JLabel("Choose your game");
		lblChooseYourGame.setBounds(130, 295, 110, 44);
		add(lblChooseYourGame);
		
		comboBoxGame = new JComboBox();
		comboBoxGame.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxGame.setBounds(130, 364, 180, 38);
		comboBoxGame.setModel(new DefaultComboBoxModel(new String[] {"Catch The Bunny","Tic Tac Toe"}));
		add(comboBoxGame);
		
		JLabel lblGameType = new JLabel("Game Type");
		lblGameType.setBounds(345, 295, 91, 44);
		add(lblGameType);
		
		comboBoxType = new JComboBox();
		comboBoxType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxType.setBounds(345, 364, 157, 38);
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] {"Smart","Random"}));
		add(comboBoxType);
		
		JLabel lblWhoseStarting = new JLabel("Who Will Start?");
		lblWhoseStarting.setBounds(545, 295, 91, 44);
		add(lblWhoseStarting);
		
		comboBoxWhoseStarting = new JComboBox();
		comboBoxWhoseStarting.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxWhoseStarting.setBounds(540, 364, 157, 38);
		comboBoxWhoseStarting.setModel(new DefaultComboBoxModel(new String[] {"Player","PC"}));
		add(comboBoxWhoseStarting);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				propertyChangeHandler.firePropertyChange("NewGameEventMView",comboBoxGame.getSelectedItem().toString().toUpperCase()
				,comboBoxType.getSelectedItem().toString().toUpperCase());
				if(comboBoxWhoseStarting.getSelectedItem().equals("PC")) {
					propertyChangeHandler.firePropertyChange("ComputerStartGameEventView",0,1);	
				}
			}
		});
		btnNewGame.setBounds(357, 599, 97, 25);
		add(btnNewGame);
	}
	public void showErrorMessage(String err)
	{
		JOptionPane.showMessageDialog(this,err);
	}
	
	
	////////////observer functions//////////////////
	
	public void setPropertyChangeSupport() {
		propertyChangeHandler = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeHandler.addPropertyChangeListener(listener); }
}
