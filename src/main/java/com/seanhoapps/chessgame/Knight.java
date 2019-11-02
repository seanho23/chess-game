package com.seanhoapps.chessgame;

public class Knight extends Piece {
	public Knight(ChessColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	// Knight moves in L-shape in any direction
	@Override
	public boolean isPossibleMove(Position startPos, Position endPos) {
		if (endPos.equals(startPos)) {
			return false;
		}
		
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		
		if (rowDiff + colDiff == 3 && rowDiff > 0 && colDiff > 0) {
			return true;
		}
		
		return false;
	}
	
	// Knight jumps over other pieces
	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		return new Position[0];
	}
	
	// King is already K
	@Override
	public char getAbbreviation() {		
		return 'N';
	}
}
