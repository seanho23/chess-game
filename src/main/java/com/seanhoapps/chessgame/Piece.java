package com.seanhoapps.chessgame;

public abstract class Piece {
	protected PieceType type;
	protected ChessColor color;
	protected int moveCount = 0;
	
	public Piece(PieceType type, ChessColor color) {
		this.type = type;
		this.color = color;
	}
	
	public abstract boolean isPossibleMove(Position startPosition, Position endPosition);
	
	public PieceType getType() {
		return type;
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public boolean isWhite() {
		if (color == ChessColor.WHITE) {
			return true;
		}
		
		return false;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void setMoveCount(int newCount) {
		moveCount = newCount;
	}
	
	public boolean hasMoved() {
		if (moveCount > 0) {
			return true;
		}
		
		return false;
	}
	
	public char getAbbreviation() {
		return type.toString().charAt(0);
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
