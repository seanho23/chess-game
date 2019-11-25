package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class Knight extends Piece {
	public Knight(ChessColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	// Copy constructor
	public Knight(Knight knight) {
		super(knight);
	}
	
	// Knight moves in L-shape in any direction
	@Override
	public boolean canMove(Position startPos, Position endPos) {
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		
		if (rowDiff + colDiff == 3 && rowDiff > 0 && colDiff > 0) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		return new Position[0]; // Knight jumps over other pieces
	}
	
	// King is already K
	@Override
	public char getAbbreviation() {		
		return 'N';
	}
	
	@Override
	public Piece getCopy() {
		return new Knight(this);
	}
}
