package com.hit.gameAlgo;


public abstract class GameBoard implements IGameAlgo  {
	
	private int rowLength;
	private int colLength;
	private char[][] board;
	
	
	public static class GameMove {
		private int col;
		private int row;
		public GameMove(int row,int column) {
			this.col=column;
			this.row=row;
		}
		public int getRow() {
			return this.row;
		}
		public int getColumn() {
			return this.col;
		}
		public void setRow(int row) {
			this.row=row;
		}
		public void setCol(int col) {
			this.col=col;
		}
		public void setMove(int row,int col) {
			setRow(row);
			setCol(col);
		}
	}
	
	public GameBoard(int rowLength, int colLength) {
		this.rowLength=rowLength;
		this.colLength=colLength;
		this.board=new char[rowLength][colLength];
		for(int i=0;i<rowLength;i++)
			for(int j=0;j<colLength;j++) 
				board[i][j]=' ';
	}
	
	public char[][] getBoardState(){
		return this.board;
	}
	
	public void updateBoard(GameMove move, char sign) {
		board[move.getRow()][move.getColumn()]=sign;
	}
	
	public char getBoardSign(GameMove move) {
		return board[move.getRow()][move.getColumn()];
	}
	
	public int getRowsLength() {
		return this.rowLength;
	}
	
	public int getColsLength() {
		return this.colLength;
	}
	
	
	public abstract void calcComputerMove();
	public abstract GameState getGameState(GameBoard.GameMove move);
	public abstract boolean updatePlayerMove(GameBoard.GameMove move);
	
	
	
	
}

