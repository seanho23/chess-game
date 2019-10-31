package com.seanhoapps.chessgame;

public class Queen extends Piece {
	public Queen(ChessColor color) {
		super(PieceType.QUEEN, color);
	}
	
	// Queen moves within same row, column, and diagonals
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		int endRow = endPosition.getRow();
		int endCol = endPosition.getCol();
		
		// Same row and column
		if (startRow == endRow || startCol == endCol) {
			return true;
		}
		
		// Same diagonals
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		
		if (rowDiff == colDiff) {
			return true;
		}
		
		return false;
	}

}
