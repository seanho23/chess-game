package com.seanhoapps.chessgame;

import java.util.HashMap;
import java.util.Map;

public class Board {
	private Square[][] squares;
	
	public Board(int rowSize, int colSize) {
		init(rowSize, colSize);
	}
	
	public void init(int rowSize, int colSize) {
		squares = new Square[rowSize][colSize];
		initSquares();
	}
	
	public void initSquares() {		
		boolean isWhite = true;
		
		for (int row = 0; row < getRowSize(); row++) {	
			for (int col = 0; col < getColSize(); col++) {
				Square square = null;
				
				if (isWhite) {
					square = new Square(ChessColor.WHITE);
				}
				else {
					square = new Square(ChessColor.BLACK);
				}
				
				squares[row][col] = square;
				isWhite = !isWhite;
			}
			
			isWhite = !isWhite; // First square in row is same color as last square in previous row
		}
	}
	
	public void setPiece(Position position, Piece piece) {
		Square square = squares[position.getRow()][position.getCol()];
		square.setPiece(piece);
	}
	
	public int getRowSize() {
		return squares.length;
	}
	
	public int getColSize() {
		return squares[0].length;
	}
	
	public void printPieces() {
		for (int row = 0; row < getRowSize(); row++) {
			String space = "";
			
			for (int col = 0; col < getColSize(); col++) {
				Square square = squares[row][col];
				
				if (square.isOccupied()) {
					Piece piece = square.getPiece();
					char abbr = piece.getAbbreviation();
					
					if (piece.isWhite()) {
						abbr = Character.toUpperCase(abbr);
					}
					else {
						abbr = Character.toLowerCase(abbr);
					}
					
					System.out.print(space + abbr);
				}
				else {
					System.out.print(space + " ");
				}
				
				space = " ";
			}
			
			System.out.println();
		}
	}
	
	public void printSquareColors() {
		for (int row = 0; row < getRowSize(); row++) {
			String space = "";
			
			for (int col = 0; col < getColSize(); col++) {
				Square square = squares[row][col];
				
				if (square.isWhite()) {
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
}
