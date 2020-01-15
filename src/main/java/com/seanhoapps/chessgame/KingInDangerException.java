package com.seanhoapps.chessgame;

public class KingInDangerException extends Exception {
	private Position kingPosition;
	
	public KingInDangerException(Position kingPosition) {
		super();
		this.kingPosition = kingPosition;
	}
	
	public Position getKingPosition() {
		return kingPosition;
	}
}
