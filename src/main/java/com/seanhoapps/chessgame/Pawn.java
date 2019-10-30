package com.seanhoapps.chessgame;

public class Pawn extends Piece {
	public Pawn(ChessColor color) {
		super(PieceType.PAWN, color);
	}
	
	@Override
	public boolean getValidMoves(Position startPosition, Position endPosition) {
		return false;
	}

}
