package com.seanhoapps.chessgame;

public class Pawn extends Piece {
	public Pawn(ChessColor color) {
		super(PieceType.PAWN, color);
	}
	
	// Pawn normally moves 1 square forward and captures 1 square diagonally forward
	// It may move 2 squares forward if it has not moved before
	// En passant conditions are checked by Game
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		if (endPos.equals(startPos)) {
			return false;
		}
		
		int startRow = startPos.getRow();
		int startCol = startPos.getCol();
		int endRow = endPos.getRow();
		int endCol = endPos.getCol();
		
		// Can only move forward
		if ((isWhite() && endRow > startRow) || (!isWhite() && endRow < startRow)) {
			return false;
		}
		
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		
		// Move 1 square forward
		if (rowDiff == 1) {
			return true;
		}
		
		// Capture 1 square diagonally forward
		if (rowDiff == 1 && colDiff == 1) {
			return true;
		}
		
		// Move 2 squares forward
		if (!hasMoved() && rowDiff == 2) {
			return true;
		}
		
		return false;
	}

	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		int rowDiff = endPos.getRow() - startPos.getRow();
		int colDiff = endPos.getCol() - startPos.getCol();
		Position[] path;
		
		// Normal moves and capture
		if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
			path = new Position[0];
		}
		else {
			// Moves 2 squares
			int rowOffset = Integer.signum(rowDiff);
			path = new Position[1];
			path[0] = new Position(startPos.getRow() + rowOffset, startPos.getCol());
		}
		
		return path;
	}

}