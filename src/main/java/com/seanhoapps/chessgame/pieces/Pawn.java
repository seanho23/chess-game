package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class Pawn extends Piece {	
	public Pawn(ChessColor color) {
		super(PieceType.PAWN, color);
	}
	
	// Copy constructor
	public Pawn(Pawn pawn) {
		super(pawn);
	}
	
	// Pawn normally moves 1 square forward or 2 squares forward if it has not moved before
	// Capture and en passant conditions are checked by GameController
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		int startRow = startPos.getRow();
		int endRow = endPos.getRow();
		
		// Can only move forward
		if ((isWhite() && endRow > startRow) || (!isWhite() && endRow < startRow)) {
			return false;
		}
		
		// Move 1 square forward
		int rowDiff = Math.abs(endRow - startRow);
		
		if (rowDiff == 1) {
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
		Position[] path;
		
		// Can only capture diagonally forward so end position is included in path when moving forward
		// Move 1 square forward
		if (Math.abs(rowDiff) == 1) {
			path = new Position[] {endPos};
		}
		else {
			// Move 2 squares forward
			int rowDiffSign = Integer.signum(rowDiff);
			int pathLength = Math.abs(rowDiff);
			path = new Position[pathLength];
			
			for (int i = 1; i <= pathLength; i++) {
				int rowOffset = i * rowDiffSign;
				path[i - 1] = new Position(startPos.getRow() + rowOffset, startPos.getCol());
			}
		}
		
		return path;
	}
	
	@Override
	public Piece getCopy() {
		return new Pawn(this);
	}
}