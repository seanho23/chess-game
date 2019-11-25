package com.seanhoapps.chessgame;

public class IllegalMoveException extends Exception {
	
	private Position startPosition = null;
	private Position endPosition = null;
	
	public IllegalMoveException() {
		super();
	}
	
	public IllegalMoveException(String message) {
		super(message);
	}
	
	public IllegalMoveException(String message, Position startPosition, Position endPosition) {
		super(message + ": " + startPosition + " --> " + endPosition);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
	
	public Position getEndPosition() {
		return endPosition;
	}
}
