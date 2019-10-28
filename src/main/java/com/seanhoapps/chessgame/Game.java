package com.seanhoapps.chessgame;

public class Game {
	// Constants
	static final int ROW_SIZE = 8;
	static final int COL_SIZE = 8;
	
	private static boolean isGameOver = false;
	Piece[][] board = new Piece[ROW_SIZE][COL_SIZE];
	
	public Game(Player whitePlayer, Player blackPlayer) {
		initBoard();
		printBoard();
	}
	
	public void initBoard() {
		initPieces(PieceColor.BLACK);
		initPieces(PieceColor.WHITE);
	}
	
	public void initPieces(PieceColor color) {
		int row, pawnRow;
		
		if (color == PieceColor.BLACK) {
			row = 0;
			pawnRow = row + 1;
		}
		else {
			row = getRowSize() - 1;
			pawnRow = row - 1;
		}
		
		board[row][0] = PieceFactory.getPiece(PieceType.ROOK, color);
		board[row][1] = PieceFactory.getPiece(PieceType.KNIGHT, color);
		board[row][2] = PieceFactory.getPiece(PieceType.BISHOP, color);
		board[row][3] = PieceFactory.getPiece(PieceType.QUEEN, color);
		board[row][4] = PieceFactory.getPiece(PieceType.KING, color);
		board[row][5] = PieceFactory.getPiece(PieceType.BISHOP, color);
		board[row][6] = PieceFactory.getPiece(PieceType.KNIGHT, color);
		board[row][7] = PieceFactory.getPiece(PieceType.ROOK, color);
		
		for (int col = 0; col < getColSize(); col++) {
			board[pawnRow][col] = PieceFactory.getPiece(PieceType.PAWN, color);
		}
	}
	
	public int getRowSize() {
		return board.length;
	}
	
	public int getColSize() {
		return board[0].length;
	}
	
	public void printBoard() {
		for (int row = 0; row < getRowSize(); row++) {
			for (int col = 0; col < getColSize(); col++) {
				Piece piece = board[row][col];
				
				if (piece != null) {
					System.out.print(piece.getSymbol());
				}
				else {
					System.out.print(" ");
				}
			}
			
			System.out.println();
		}
	}
	
	public void play() {
		
	}
}
