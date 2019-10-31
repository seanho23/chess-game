package com.seanhoapps.chessgame;

public class King extends Piece {
	public King(ChessColor color) {
		super(PieceType.KING, color);
	}
	
	// King normally moves 1 square within same row, column, and diagonals
	// It may castle if it has not moved before
	// Other castling conditions are checked by Game
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		int diffRow = Math.abs(endPosition.getRow() - startPosition.getRow());
		int diffCol = Math.abs(endPosition.getCol() - startPosition.getCol());
		
		// Normal moves
		if (diffRow <= 1 && diffCol <= 1) {
			return true;
		}
		
		// Castling
		if (!hasMoved() && diffCol == 2) {
			return true;
		}
		
		return false;
	}
	
}
