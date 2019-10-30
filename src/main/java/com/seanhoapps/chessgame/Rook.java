package com.seanhoapps.chessgame;

public class Rook extends Piece {
	public Rook(ChessColor color) {
		super(PieceType.ROOK, color);
	}
	
	@Override
	public boolean getValidMoves(Position startPosition, Position endPosition) {
		return false;
	}
}
