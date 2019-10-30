package com.seanhoapps.chessgame;

public class Square {
	private Position position;
	private ChessColor color;
	private Piece piece = null;
	
	public Square(Position position, ChessColor color, Piece piece) {
		this.position = position;
		this.color = color;
		this.piece = piece;
	}
	
	public Square(Position position, ChessColor color) {
		this.position = position;
		this.color = color;
	}
	
	public Position getPosition() {
		return position;
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
