package com.hit.view;

public interface View {
	
	void start();
	void updateViewNewGame(Character[] board);
	void updateViewGameMove(int gameState,Character[] board);
	void connectionError(String err);

}
