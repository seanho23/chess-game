package com.seanhoapps.chessgame;

public abstract class Piece {
	PieceType type;
	PieceColor color;
	
	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;
	}
	
	public abstract boolean isValidMove(Position from, Position to);
	
	public char getSymbol() {
		char initial = type.toString().charAt(0);
		
		if (color == PieceColor.BLACK) {
			return Character.toUpperCase(initial);
		}
		else {
			return Character.toLowerCase(initial);
		}
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
