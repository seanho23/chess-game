package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class Queen extends Piece {
	public Queen(ChessColor color) {
		super(PieceType.QUEEN, color);
	}
	
	// Copy constructor
	public Queen(Queen queen) {
		super(queen);
	}
	
	// Queen moves within same row, column, and diagonals
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		int startRow = startPos.getRow();
		int startCol = startPos.getCol();
		int endRow = endPos.getRow();
		int endCol = endPos.getCol();
		
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

	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		int startRow = startPos.getRow();
		int startCol = startPos.getCol();
		int endRow = endPos.getRow();
		int endCol = endPos.getCol();
		int rowDiff = endRow - startRow;
		int colDiff = endCol - startCol;
		int rowDiffSign = Integer.signum(rowDiff);
		int colDiffSign = Integer.signum(colDiff);
		Position[] path;
		
		// Moves within same row and column
		if (startRow == endRow || startCol == endCol) {
			int pathLength = Math.abs(rowDiff) + Math.abs(colDiff) - 1;
			path = new Position[pathLength];
			
			for (int i = 1; i <= pathLength; i++) {
				int rowOffset = i * rowDiffSign;
				int colOffset = i * colDiffSign;
				path[i - 1] = new Position(startRow + rowOffset, startCol + colOffset);
			}
		}
		else {
			// Moves within same diagonals
			int pathLength = ((Math.abs(rowDiff) + Math.abs(colDiff)) / 2) - 1;
			path = new Position[pathLength];
			
			for (int i = 1; i <= pathLength; i++) {
				int rowOffset = i * rowDiffSign;
				int colOffset = i * colDiffSign;
				path[i - 1] = new Position(startRow + rowOffset, startCol + colOffset);
			}
		}
		
		return path;
	}
	
	@Override
	public Piece getCopy() {
		return new Queen(this);
	}
}
