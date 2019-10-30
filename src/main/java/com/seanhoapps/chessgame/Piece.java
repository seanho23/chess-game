package com.seanhoapps.chessgame;

public abstract class Piece {
	protected PieceType type;
	protected ChessColor color;
	
	public Piece(PieceType type, ChessColor color) {
		this.type = type;
		this.color = color;
	}
	
	public abstract boolean getValidMoves(Position startPosition, Position endPosition);
	
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

	@Override
	public String toString() {
		return type.toString();
	}
}
