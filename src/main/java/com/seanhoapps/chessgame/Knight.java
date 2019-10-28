package com.seanhoapps.chessgame;

public class Knight extends Piece {
	public Knight(PieceColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	@Override
	public boolean isValidMove(Position from, Position to) {
		return false;
	}

	@Override
	public char getSymbol() {
		if (color == PieceColor.BLACK) {
			return 'N';
		}
		else {
			return 'n';
		}
	}
	
}
