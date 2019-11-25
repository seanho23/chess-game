package com.seanhoapps.chessgame;

import java.awt.Color;

import com.seanhoapps.chessgame.pieces.Piece;

public class Square {
	private ChessColor color;
	private Color highlightColor = null;
	private Piece piece = null;
	
	public Square(ChessColor color) {
		this.color = color;
	}
	
	// Copy constructor
	public Square(Square square) {
		color = square.getColor();
		highlightColor = square.getHighlightColor();
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
	
	public Color getHighlightColor() {
		return highlightColor;
	}
	
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	
	public boolean isHighlighted() {
		return highlightColor != null;
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
