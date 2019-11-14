package com.seanhoapps.chessgame;

public enum ChessColor {
	WHITE, BLACK;
	
	public boolean isWhite() {
		return this == this.WHITE;
	}
		
	public boolean isSameColor(ChessColor color) {
		return this == color;
	}
}
