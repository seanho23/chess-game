package com.seanhoapps.chessgame;

public class Pawn extends Piece {
	public Pawn(ChessColor color) {
		super(PieceType.PAWN, color);
	}
	
	// Pawn normally moves 1 space forward and captures 1 space diagonally forward
	// It may move 2 spaces forward if it has not moved before
	// En passant conditions are checked by Game
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		int endRow = endPosition.getRow();
		int endCol = endPosition.getCol();
		
		// Can only move forward
		if ((isWhite() && endRow > startRow) || (!isWhite() && endRow < startRow)) {
			return false;
		}
		
		int diffRow = Math.abs(endRow - startRow);
		int diffCol = Math.abs(endCol - startCol);
		
		// Move 1 space forward or capture 1 space diagonally forward
		if (diffRow <= 1 && diffCol <= 1) {
			return true;
		}
		
		// Move 2 spaces forward
		if (!hasMoved() && diffRow == 2) {
			return true;
		}	
		
		return false;
	}

}