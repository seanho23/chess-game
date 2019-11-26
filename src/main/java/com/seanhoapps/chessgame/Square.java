package com.seanhoapps.chessgame;

import com.seanhoapps.chessgame.gui.HighlightType;
import com.seanhoapps.chessgame.pieces.Piece;

public class Square {
	private ChessColor color;
	private HighlightType highlight;
	private Piece piece = null;
	
	public Square(ChessColor color) {
		this.color = color;
	}
	
	// Copy constructor
	public Square(Square square) {
		color = square.getColor();
		highlight = square.getHighlight();
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
	
	public HighlightType getHighlight() {
		return highlight;
	}
	
	public void setHighlight(HighlightType highlight) {
		this.highlight = highlight;
	}
	
	public boolean isHighlighted() {
		return highlight != null;
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
