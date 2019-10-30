package com.seanhoapps.chessgame;

public class Queen extends Piece {
	public Queen(ChessColor color) {
		super(PieceType.QUEEN, color);
	}
	
	@Override
	public boolean getValidMoves(Position startPosition, Position endPosition) {
		return false;
	}

}
