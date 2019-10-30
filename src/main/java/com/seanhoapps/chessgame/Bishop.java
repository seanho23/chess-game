package com.seanhoapps.chessgame;

public class Bishop extends Piece {
	public Bishop(ChessColor color) {
		super(PieceType.BISHOP, color);
	}
	
	@Override
	public boolean getValidMoves(Position startPosition, Position endPosition) {
		return false;
	}

}
