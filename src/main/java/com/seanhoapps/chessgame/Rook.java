package com.seanhoapps.chessgame;

public class Rook extends Piece {
	public Rook(ChessColor color) {
		super(PieceType.ROOK, color);
	}
	
	// Rook moves within same row and column
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		if (endPos.equals(startPos)) {
			return false;
		}
		
		if (startPos.getRow() == endPos.getRow() || startPos.getCol() == endPos.getCol()) {
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
		int pathLength = Math.abs(rowDiff) + Math.abs(colDiff) - 1;
		Position[] path = new Position[pathLength];

		for (int i = 1; i <= pathLength; i++) {
			int rowOffset = i * rowDiffSign;
			int colOffset = i * colDiffSign;
			path[i - 1] = new Position(startPos.getRow() + rowOffset, startPos.getCol() + colOffset);
		}
		
		return path;
	}
}
