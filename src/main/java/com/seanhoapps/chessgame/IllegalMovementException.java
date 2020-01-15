package com.seanhoapps.chessgame;

public class IllegalMovementException extends Exception {
	
	private Position startPosition = null;
	private Position endPosition = null;
	
	public IllegalMovementException() {
		super();
	}
	
	public IllegalMovementException(String message) {
		super(message);
	}
	
	public IllegalMovementException(String message, Position startPosition, Position endPosition) {
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
