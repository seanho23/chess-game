package com.seanhoapps.chessgame;

public class Game {
	// Constants
	static final int ROW_SIZE = 8;
	static final int COL_SIZE = 8;
	
	Board board;
	
	public Game(Player whitePlayer, Player blackPlayer) {
		initBoard(ROW_SIZE, COL_SIZE);
		board.printSquareColors();
		System.out.println();
		board.printPieces();
	}
	
	public void initBoard(int rowSize, int colSize) {
		board = new Board(rowSize, colSize);
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);
	}
	
	public void initPieces(ChessColor color) {
		int kingRow, pawnRow;
		
		if (color == ChessColor.WHITE) {
			kingRow = board.getRowSize() - 1;
			pawnRow = kingRow - 1;
		}
		else {
			kingRow = 0;
			pawnRow = kingRow + 1;
		}
		
		board.placePiece(PieceFactory.createPiece(PieceType.ROOK, color), kingRow, 0);
		board.placePiece(PieceFactory.createPiece(PieceType.KNIGHT, color), kingRow, 1);
		board.placePiece(PieceFactory.createPiece(PieceType.BISHOP, color), kingRow, 2);
		board.placePiece(PieceFactory.createPiece(PieceType.QUEEN, color), kingRow, 3);
		board.placePiece(PieceFactory.createPiece(PieceType.KING, color), kingRow, 4);
		board.placePiece(PieceFactory.createPiece(PieceType.BISHOP, color), kingRow, 5);
		board.placePiece(PieceFactory.createPiece(PieceType.KNIGHT, color), kingRow, 6);
		board.placePiece(PieceFactory.createPiece(PieceType.ROOK, color), kingRow, 7);
		
		for (int col = 0; col < board.getColSize(); col++) {
			board.placePiece(PieceFactory.createPiece(PieceType.PAWN, color), pawnRow, col);
		}
	}
}