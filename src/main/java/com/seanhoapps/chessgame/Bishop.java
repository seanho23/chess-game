package com.seanhoapps.chessgame;

public class Bishop extends Piece {
	public Bishop(ChessColor color) {
		super(PieceType.BISHOP, color);
	}
	
	// Bishop moves within same diagonals
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		int rowDiff = Math.abs(endPosition.getRow() - startPosition.getRow());
		int colDiff = Math.abs(endPosition.getCol() - startPosition.getCol());
		
		if (rowDiff == colDiff) {
			return true;
		}
		
		return false;
	}

}
