package com.seanhoapps.chessgame;

public class Bishop extends Piece {
	public Bishop(PieceColor color) {
		super(PieceType.BISHOP, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}

}
