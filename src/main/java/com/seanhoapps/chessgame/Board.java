package com.seanhoapps.chessgame;

public class Board {
	Square[][] squares;
	
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
				if (isWhite) {
					squares[row][col] = new Square(ChessColor.WHITE);
				}
				else {
					squares[row][col] = new Square(ChessColor.BLACK);
				}
				
				isWhite = !isWhite;
			}
			
			isWhite = !isWhite; // First square in row is same color as last square in previous row
		}
	}
	
	public Piece getPiece(int row, int col) {
		return squares[row][col].getPiece();
	}
	
	public void placePiece(Piece piece, int row, int col) {
		piece.setRow(row);
		piece.setCol(col);
		squares[row][col].setPiece(piece);
	}
	
	public void printPieces() {		
		int rowSize = getRowSize();
		
		for (int row = 0; row < rowSize; row++) {
			String space = "";
			
			System.out.print(rowSize - row + " | ");
			
			for (int col = 0; col < getColSize(); col++) {
				Piece piece = getPiece(row, col);
				
				if (piece != null) {
					System.out.print(space + piece.getAbbreviation());
				}
				else {
					System.out.print(space + " ");
				}
				
				space = " ";
			}
			
			System.out.println();
		}
		
		System.out.println("--|----------------");
		System.out.println("  | A B C D E F G H");
	}
	
	public void printSquareColors() {		
		int rowSize = getRowSize();
		
		for (int row = 0; row < rowSize; row++) {
			String space = "";
			
			System.out.print(rowSize - row + " | ");
			
			for (int col = 0; col < getColSize(); col++) {
				ChessColor color = squares[row][col].getColor();
				
				if (color == ChessColor.WHITE) {
					System.out.print(space + "W");
				}
				else {
					System.out.print(space + "B");
				}
				
				space = " ";
			}
			
			System.out.println();
		}

		System.out.println("--|----------------");
		System.out.println("  | A B C D E F G H");
	}
	
	public boolean isOccupied(int row, int col) {
		Piece piece = getPiece(row, col);
		
		if (piece != null) {
			return true;
		}
		
		return false;
	}
	
	public int getRowSize() {
		return squares.length;
	}
	
	public int getColSize() {
		return squares[0].length;
	}
}
