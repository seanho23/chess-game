package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class King extends Piece {	
	public King(ChessColor color) {
		super(PieceType.KING, color);
	}
	
	// Copy constructor
	public King(King king) {
		super(king);
	}
	
	// King normally moves 1 square within same row, column, and diagonals
	// Castling conditions are checked by GameController
	@Override
	public boolean canMove(Position startPos, Position endPos) {
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		
		// Normal moves
		if (rowDiff <= 1 && colDiff <= 1) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Position[] getMovePath(Position startPos, Position endPos) {
		return new Position[0]; // No path because King moves 1 square
	}
	
	@Override
	public Piece getCopy() {
		return new King(this);
	}
}
