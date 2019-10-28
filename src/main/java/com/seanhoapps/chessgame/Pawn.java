package com.seanhoapps.chessgame;

public class Pawn extends Piece {
	public Pawn(PieceColor color) {
		super(PieceType.PAWN, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}

}
