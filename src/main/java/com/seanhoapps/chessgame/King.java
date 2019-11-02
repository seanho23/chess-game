package com.seanhoapps.chessgame;

public class King extends Piece {
	public King(ChessColor color) {
		super(PieceType.KING, color);
	}
	
	// King normally moves 1 square within same row, column, and diagonals
	// It may castle if it has not moved before
	// Other castling conditions are checked by Game
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		if (endPos.equals(startPos)) {
			return false;
		}
		
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		
		// Normal moves
		if (rowDiff <= 1 && colDiff <= 1) {
			return true;
		}
		
		// Castling
		if (!hasMoved() && colDiff == 2) {
			return true;
		}
		
		return false;
	}

	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		int rowDiff = endPos.getRow() - startPos.getRow();
		int colDiff = endPos.getCol() - startPos.getCol();
		Position[] path;
		
		// Normal moves
		if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
			path = new Position[0];
		}
		else {
			// Castling
			int colOffset = Integer.signum(colDiff);
			path = new Position[1];
			path[0] = new Position(startPos.getRow(), startPos.getCol() + colOffset);
		}
		
		return path;
	}
	
}
