package com.seanhoapps.chessgame;

public class Queen extends Piece {
	public Queen(PieceColor color) {
		super(PieceType.QUEEN, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}

}
