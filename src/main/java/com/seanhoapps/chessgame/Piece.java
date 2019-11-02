package com.seanhoapps.chessgame;

public abstract class Piece {
	protected PieceType type;
	protected ChessColor color;
	protected boolean hasMoved = false;
	
	public Piece(PieceType type, ChessColor color) {
		this.type = type;
		this.color = color;
	}
	
	public abstract boolean isPossibleMove(Position startPos, Position endPos);
	
	public abstract Position[] getMovePath(Position startPos, Position endPos);
	
	public PieceType getType() {
		return type;
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public boolean isWhite() {
		return color.isWhite();
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void hasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public char getAbbreviation() {
		return type.toString().charAt(0);
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
