package com.seanhoapps.chessgame;

public class King extends Piece {
	public King(ChessColor color) {
		super(PieceType.KING, color);
	}
	
	@Override
	public boolean getValidMoves(Position startPosition, Position endPosition) {
		return false;
	}
	
}
