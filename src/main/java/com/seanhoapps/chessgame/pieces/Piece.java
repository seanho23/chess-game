package com.seanhoapps.chessgame.pieces;

import java.awt.Image;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.PieceImage;
import com.seanhoapps.chessgame.Position;

public abstract class Piece {
	// Used to assign unique id for each piece
	private static int count = 0;
	
	protected final int id; // Only used by hashCode() for comparisons
	protected PieceType type;
	protected ChessColor color;
	protected Image image;
	protected boolean hasMoved = false;
	
	public Piece(PieceType type, ChessColor color) {
		id = count;
		this.type = type;
		this.color = color;
		image = PieceImage.getImage(type, color);
		
		// Increment unique id for next piece instantiation
		count++;
	}
	
	// Copy constructor
	public Piece(Piece piece) {
		id = piece.getId();
		type = piece.getType();
		color = piece.getColor();
		image = piece.getImage();
		hasMoved = piece.hasMoved();
	}
	
	public abstract boolean canMove(Position startPos, Position endPos);
	
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
	
	public void setMoved(Boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public Image getImage() {
		return image;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Piece)) {
			return false;
		}
		
		Piece piece = (Piece) obj;
		
		if (id != piece.getId()) {
			return false;
		}
		
		if (!type.equals(piece.getType())) {
			return false;
		}
		
		if (!color.equals(piece.getColor())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + id;
		hash = 31 * hash + ((type == null) ? 0 : type.hashCode());
		hash = 31 * hash + ((color == null) ? 0 : color.hashCode());
		return hash;
	}
		
	@Override
	public String toString() {
		return type.toString();
	}

	private int getId() {
		return id;
	}
}
