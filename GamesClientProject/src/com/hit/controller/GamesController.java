package com.hit.controller;

import java.beans.PropertyChangeEvent;

import com.hit.model.Model;
import com.hit.view.View;

public class GamesController implements Controller {
	
	Model model;
	View view;
	
	public GamesController(Model model,View view)
	{
		this.model=model;
		this.view=view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("NewGameEventMView"))
		{
			model.newGame((String)evt.getOldValue(), (String)evt.getNewValue());
		}
		else if(evt.getPropertyName().equals("NewGameEventModel"))
		{
			view.updateViewNewGame((Character[])evt.getNewValue());
		}
		else if(evt.getPropertyName().equals("UpdatePlayerMoveView"))
		{
			String index=(String)evt.getNewValue();
			//Integer.parseInt(index.split(" ")[0]);
			model.updatePlayerMove(Integer.parseInt(index.split(" ")[0]), Integer.parseInt(index.split(" ")[1]));
		}
		else if(evt.getPropertyName().equals("UpdatePlayerMoveModel"))
		{
			view.updateViewGameMove((int)evt.getOldValue(), (Character[])evt.getNewValue());
		}
		else if(evt.getPropertyName().equals("EndGameEventView")) {
			model.endGame();
		}
		else if(evt.getPropertyName().equals("RestartEventView"))
		{
			model.restartGame();
		}
		else if(evt.getPropertyName().equals("RestartEventModel"))
		{
			view.updateViewNewGame((Character[])evt.getNewValue());
		}//"ComputerStartGameEventView"
		else if(evt.getPropertyName().equals("ComputerStartGameEventView"))
		{
			model.computerStartGame();
		}else if(evt.getPropertyName().equals("FailToConnectServer"))
		{
			view.connectionError((String)evt.getNewValue());
		}
	}

}
