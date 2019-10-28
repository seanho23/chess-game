package com.seanhoapps.chessgame;

public class King extends Piece {
	public King(PieceColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}
	
}
