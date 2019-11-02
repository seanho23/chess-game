package com.seanhoapps.chessgame;

public class Square {
	private ChessColor color;
	private Piece piece = null;
	
	public Square(ChessColor color) {
		this.color = color;
	}
		
	public ChessColor getColor() {
		return color;
	}
	
	public boolean isWhite() {
		return color.isWhite();
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean isOccupied() {
		return piece != null;
	}
}
