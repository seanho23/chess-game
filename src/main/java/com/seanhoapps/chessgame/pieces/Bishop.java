package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class Bishop extends Piece {
	public Bishop(ChessColor color) {
		super(PieceType.BISHOP, color);
	}
	
	// Copy constructor
	public Bishop(Bishop bishop) {
		super(bishop);
	}
	
	// Bishop moves within same diagonals
	@Override
	public boolean canMove(Position startPos, Position endPos) {
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		
		if (rowDiff == colDiff) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		int rowDiff = endPos.getRow() - startPos.getRow();
		int colDiff = endPos.getCol() - startPos.getCol();
		int rowDiffSign = Integer.signum(rowDiff);
		int colDiffSign = Integer.signum(colDiff);
		int pathLength = ((Math.abs(rowDiff) + Math.abs(colDiff)) / 2) - 1;
		Position[] path = new Position[pathLength];

		for (int i = 1; i <= pathLength; i++) {
			int rowOffset = i * rowDiffSign;
			int colOffset = i * colDiffSign;
			path[i - 1] = new Position(startPos.getRow() + rowOffset, startPos.getCol() + colOffset);
		}
		
		return path;
	}
	
	@Override
	public Piece getCopy() {
		return new Bishop(this);
	}
}
