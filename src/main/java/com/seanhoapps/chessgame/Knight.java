package com.seanhoapps.chessgame;

public class Knight extends Piece {
	public Knight(ChessColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	// Knight moves in L-shape in any direction
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		int diffRow = Math.abs(endPosition.getRow() - startPosition.getRow());
		int diffCol = Math.abs(endPosition.getCol() - startPosition.getCol());
		
		if (diffRow + diffCol == 3 && diffRow > 0 && diffCol > 0) {
			return true;
		}
		
		return false;
	}
	
	// King is already K
	@Override
	public char getAbbreviation() {		
		return 'N';
	}
	
}
