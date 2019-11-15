package com.seanhoapps.chessgame;

import com.seanhoapps.chessgame.pieces.Piece;

public class Square {
	private ChessColor color;
	private Piece piece = null;
	
	public Square(ChessColor color) {
		this.color = color;
	}
	
	// Copy constructor
	public Square(Square square) {
		this.color = square.getColor();
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public boolean isWhite() {
		return color.isWhite();
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean isOccupied() {
		return piece != null;
	}
	
	public Square getCopy() {
		Square squareCopy = new Square(this);
		
		// Copy piece
		if (isOccupied()) {
			Piece pieceCopy = getPiece().getCopy();
			squareCopy.setPiece(pieceCopy);
		}
		
		return squareCopy;
	}
}
