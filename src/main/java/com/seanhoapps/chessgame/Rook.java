package com.seanhoapps.chessgame;

public class Rook extends Piece {
	public Rook(PieceColor color) {
		super(PieceType.ROOK, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}
}
