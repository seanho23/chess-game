package com.seanhoapps.chessgame;

public class Position {
	private final int row, col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Position)) {
			return false;
		}
		
		Position position = (Position) obj;
		
		return row == position.getRow() && col == position.getCol();
	}
	
	@Override
	public int hashCode() {
		return Integer.parseInt("" + row + col);
	}
}
