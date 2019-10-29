package com.seanhoapps.chessgame;

public class Square {
	ChessColor color;
	Piece piece = null;
	
	public Square(ChessColor color) {
		this.color = color;
	}
		
	public ChessColor getColor() {
		return color;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
