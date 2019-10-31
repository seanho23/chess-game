package com.seanhoapps.chessgame;

public class Game {
	// Constants
	private static final int NUM_ROWS = 8;
	private static final int NUM_COLS = 8;
	
	private Board board;
	
	public Game(Player whitePlayer, Player blackPlayer) {
		initBoard(NUM_ROWS, NUM_COLS);
		board.printSquareColors();
		System.out.println();
		board.printPieces();
	}
	
	public void initBoard(int numRows, int numCols) {
		board = new Board(numRows, numCols);
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
		
		board.setPiece(new Position(kingRow, 0), PieceFactory.createPiece(PieceType.ROOK, color));
		board.setPiece(new Position(kingRow, 1), PieceFactory.createPiece(PieceType.KNIGHT, color));
		board.setPiece(new Position(kingRow, 2), PieceFactory.createPiece(PieceType.BISHOP, color));
		board.setPiece(new Position(kingRow, 3), PieceFactory.createPiece(PieceType.QUEEN, color));
		board.setPiece(new Position(kingRow, 4), PieceFactory.createPiece(PieceType.KING, color));
		board.setPiece(new Position(kingRow, 5), PieceFactory.createPiece(PieceType.BISHOP, color));
		board.setPiece(new Position(kingRow, 6), PieceFactory.createPiece(PieceType.KNIGHT, color));
		board.setPiece(new Position(kingRow, 7), PieceFactory.createPiece(PieceType.ROOK, color));
		
		for (int col = 0; col < board.getColSize(); col++) {
			board.setPiece(new Position(pawnRow, col), PieceFactory.createPiece(PieceType.PAWN, color));
		}
	}
}