package com.seanhoapps.chessgame;

import java.util.HashMap;
import java.util.Map;

public class Board {
	private int numRows, numCols;
	private Map<Position, Square> squares;
	
	public Board(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		init();
	}
	
	public void init() {
		squares = new HashMap<Position, Square>();
		initSquares();
	}
	
	public void initSquares() {		
		boolean isWhite = true;
		
		for (int row = 0; row < getNumRows(); row++) {	
			for (int col = 0; col < getNumCols(); col++) {
				Position position = new Position(row, col);
				Square square = null;
				
				if (isWhite) {
					square = new Square(position, ChessColor.WHITE);
				}
				else {
					square = new Square(position, ChessColor.BLACK);
				}
				
				squares.put(position, square);
				isWhite = !isWhite;
			}
			
			isWhite = !isWhite; // First square in row is same color as last square in previous row
		}
	}
	
	public Piece getPieceAt(Position position) {
		Square square = getSquareAt(position);
		return square.getPiece();
	}
	
	public void placePieceAt(Position position, Piece piece) {
		Square square = getSquareAt(position);
		Square newSquare = new Square(position, square.getColor(), piece);
		squares.put(position, newSquare);
	}
	
	public Square getSquareAt(Position position) {
		return squares.get(position);
	}
	
	public boolean hasSquareAt(Position position) {
		return squares.containsKey(position);
	}
	
	public boolean hasPieceAt(Position position) {
		Square square = getSquareAt(position);
		
		if (square.getPiece() == null) {
			return false;
		}
		
		return true;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public void printSquareColors() {
		for (int row = 0; row < numRows; row++) {
			String space = "";
			
			for (int col = 0; col < numCols; col++) {
				Square square = getSquareAt(new Position(row, col));
				
				if (square.getColor() == ChessColor.WHITE) {
					System.out.print(space + "W");
				}
				else {
					System.out.print(space + "B");
				}
				
				space = " ";
			}
			
			System.out.println();
		}
	}
	
	public void printPieces() {		
		for (int row = 0; row < numRows; row++) {
			String space = "";
			
			for (int col = 0; col < numCols; col++) {
				Piece piece = getPieceAt(new Position(row, col));
				
				if (piece != null) {
					System.out.print(space + piece.getAbbreviation());
				}
				else {
					System.out.print(" ");
				}
				
				space = " ";
			}
			
			System.out.println();
		}
	}
}
