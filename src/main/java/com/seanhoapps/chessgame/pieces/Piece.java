package com.seanhoapps.chessgame.pieces;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public abstract class Piece {
	protected PieceType type;
	protected ChessColor color;
	protected boolean hasMoved = false;
	
	public Piece(PieceType type, ChessColor color) {
		this.type = type;
		this.color = color;
	}
	
	// Copy constructor
	public Piece(Piece piece) {
		type = piece.getType();
		color = piece.getColor();
		hasMoved = piece.hasMoved();
	}
	
	public abstract boolean isPossibleMove(Position startPos, Position endPos);
	
	public abstract Position[] getMovePath(Position startPos, Position endPos);
	
	public abstract Piece getCopy();
	
	public PieceType getType() {
		return type;
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public boolean isSameColor(Piece piece) {
		return color == piece.getColor();
	}
	
	public boolean isSameColor(ChessColor color) {
		return this.color == color;
	}
	
	public boolean isWhite() {
		return color.isWhite();
	}
	
	public char getAbbreviation() {
		return type.toString().charAt(0);
	}
	
	public void hasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
		
	@Override
	public String toString() {
		return type.toString();
	}
}
