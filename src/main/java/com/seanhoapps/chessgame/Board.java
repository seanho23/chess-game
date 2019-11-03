package com.seanhoapps.chessgame;

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
	
	public Piece getPiece(Position pos) {
		Square square = getSquare(pos);
		return square.getPiece();
	}
	
	public void setPiece(Position pos, Piece piece) {
		Square square = squares[pos.getRow()][pos.getCol()];
		square.setPiece(piece);
	}
	
	public Square getSquare(Position pos) {
		return squares[pos.getRow()][pos.getCol()];
	}
	
	public boolean isValidPosition(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		int rowSize = getRowSize();
		int colSize = getColSize();
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
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
