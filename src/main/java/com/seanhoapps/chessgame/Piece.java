package com.seanhoapps.chessgame;

public abstract class Piece {
	PieceType type;
	ChessColor color;
	int row;
	int col;
	
	public Piece(PieceType type, ChessColor color) {
		this.type = type;
		this.color = color;
	}
	
	public abstract boolean isValidMove(Board board, int toRow, int toCol);
	
	// White pieces = upper case
	// Black pieces = lower case
	public char getAbbreviation() {
		char abbr = type.toString().charAt(0);
		
		if (color == ChessColor.WHITE) {
			return Character.toUpperCase(abbr);
		}
		else {
			return Character.toLowerCase(abbr);
		}
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int newRow) {
		row = newRow;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int newCol) {
		col = newCol;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
