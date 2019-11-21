package com.seanhoapps.chessgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.seanhoapps.chessgame.pieces.Piece;
import com.seanhoapps.chessgame.pieces.PieceType;

public class Game {
	// Constants
	private static final int ROW_COUNT = 8;
	private static final int COL_COUNT = 8;
	
	private Board chessBoard;
	private List<Board> boardHistory = new ArrayList<Board>();
	private int totalMoves = 0;
	
	public Game() {
		initBoard(ROW_COUNT, COL_COUNT);
	}
	
	public boolean move(Position startPos, Position endPos) {
		if (!isPotentialMove(startPos, endPos)) {
			System.out.println("Not potential move");
			return false;
		}
		
		if (!isLegalMove(startPos, endPos)) {
			System.out.println("Not legal move");
			return false;
		}
		
		// Passes all checks
		
		// Save current board for undo later
		saveBoardState();
		
		// Perform move
		doMove(startPos, endPos, chessBoard);
		
		if (isPawnPromotion(startPos, endPos, chessBoard)) {
			// Do pawn promotion
			// Request user for piece
		}
		
		// TODO: Check for stalemate
		
		// End game if move checkmated enemy
		if (isGameOver()) {
			// TODO: Implement game over
			// Manage score
		}
		
		totalMoves++;
		System.out.println(getTurnColor());
		return true;
	}
	
	public void doMove(Position startPos, Position endPos, Board board) {
		if (isCastle(startPos, endPos, chessBoard)) {
			doCastle(startPos, endPos, chessBoard);
		}
		else if (isPawnCapture(startPos, endPos, chessBoard)) {
			doPawnCapture(startPos, endPos, chessBoard);
		}
		else {
			if (chessBoard.isOccupied(endPos)) {
				chessBoard.removePiece(endPos);
			}
			
			chessBoard.movePiece(startPos, endPos);
		}
	}
	
	public boolean isLegalMove(Position startPos, Position endPos) {
		return isLegalMove(startPos, endPos, chessBoard.getCopy());
	}
	
	public boolean isLegalMove(Position startPos, Position endPos, Board board) {
		// Move cannot put own King under attack
		doMove(startPos, endPos, board);
		
		if (isChecked(board.getPiece(startPos).getColor(), board)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isPotentialMove(Position startPos, Position endPos) {
		// Cannot move in place
		if (endPos.equals(startPos)) {
			System.out.println("Same position");
			return false;
		}
		
		// Positions must be within bounds of board
		if (!chessBoard.isValidPosition(startPos) || !chessBoard.isValidPosition(endPos)) {
			System.out.println("Out of bounds");
			return false;
		}
		
		// Cannot move an empty square
		if (!chessBoard.isOccupied(startPos)) {
			System.out.println("Empty position");
			return false;
		}
		
		// Cannot move enemy pieces
		Piece piece = chessBoard.getPiece(startPos);

		if (!piece.isSameColor(getTurnColor())) {
			System.out.println("Cannot move enemy piece");
			return false;
		}
		
		// Cannot capture own pieces
		if (chessBoard.isOccupied(endPos) && chessBoard.getPiece(endPos).isSameColor(piece)) {
			System.out.println("Cannot capture own piece");
			return false;
		}
		
		// Move must satisfy piece-specific movement conditions
		if (!isNormalMove(startPos, endPos, chessBoard) || !isSpecialMove(startPos, endPos, chessBoard)) {
			System.out.println("Fail move check");
			return false;
		}

		return true;
	}
	
	public boolean isNormalMove(Position startPos, Position endPos, Board board) {
		Piece piece = board.getPiece(startPos);
		
		if (!piece.isPossibleMove(startPos, endPos)) {
			System.out.println("Not possible move");
			return false;
		}
		
		// Piece cannot move through other pieces
		if (!board.isMovePathClear(piece.getMovePath(startPos, endPos))) {
			System.out.println("Not clear path");
			return false;
		}
		
		return true;
	}
	
	public boolean isSpecialMove(Position startPos, Position endPos, Board board) {
		Piece piece = board.getPiece(startPos);
		
		switch (piece.getType()) {
			case KING:
				if (isCastle(startPos, endPos, board)) {
					return true;
				}
			case PAWN:
				if (isPawnCapture(startPos, endPos, board)) {
					return true;
				}
			default:
				return false;
		}
	}
	
	public boolean isCastle(Position startPos, Position endPos, Board board) {
		// King castling conditions
		Piece piece = board.getPiece(startPos);
		
		if (!piece.getType().isKing()) {
			return false;
		}
		
		int colDiff = endPos.getCol() - startPos.getCol();
		
		if (piece.hasMoved() || Math.abs(colDiff) != 2) {
			return false;
		}
		
		// King cannot be in check
		ChessColor enemyColor = getEnemyColor(piece.getColor());
		
		if (isPositionAttackedByColor(startPos, enemyColor, board)) {
			return false;
		}
		
		// King cannot castle into check
		if (isPositionAttackedByColor(endPos, enemyColor, board)) {
			return false;
		}
		
		// King cannot castle through check		
		if (isPositionAttackedByColor(piece.getMovePath(startPos, endPos)[0], enemyColor, board)) {
			return false;
		}
		
		// Rook castling conditions
		int rookCol = (Integer.signum(colDiff) > 0) ? board.getColCount() - 1 : 0;
		Position rookPos = new Position(startPos.getRow(), rookCol);
		
		if (!board.isOccupied(rookPos)) {
			return false;
		}
		
		Piece rook = board.getPiece(rookPos);
		
		if (!rook.getType().isRook() || !rook.isSameColor(piece) || rook.hasMoved()) {
			return false;
		}
		
		// Path between King and Rook must be clear
		if (!board.isMovePathClear(rook.getMovePath(rookPos, startPos))) {
			return false;
		}
		
		return true;
	}
	
	public boolean isPawnCapture(Position startPos, Position endPos, Board board) {
		Piece piece = board.getPiece(startPos);
		
		if (!piece.getType().isPawn()) {
			System.out.println("Not pawn");
			return false;
		}
		
		// Can only move forward
		if ((piece.isWhite() && endPos.getRow() > startPos.getRow()) || (!piece.isWhite() && endPos.getRow() < startPos.getRow())) {
			System.out.println("Can only move forward");
			return false;
		}
		
		// Can only capture diagonally
		int rowDiff = endPos.getRow() - startPos.getRow();
		int colDiff = endPos.getCol() - startPos.getCol();
		
		if (Math.abs(rowDiff) != 1 && Math.abs(colDiff) != 1) {
			System.out.println("Cannot capture forward");
			return false;
		}
		
		if (!board.isOccupied(endPos)) {
			if (!isEnPassant(startPos, endPos, board)) {
				System.out.println("Not enpassant");
				return false;
			}
		}
		
		// Cannot capture teammate
		Piece targetPiece = board.getPiece(endPos);
		
		if (targetPiece.isSameColor(piece)) {
			System.out.println("Cannot capture teammate");
			return false;
		}
		
		return true;
	}
	
	public boolean isEnPassant(Position startPos, Position endPos, Board board) {
		// Not en passant if there is any piece diagonally forward
		if (board.isOccupied(endPos)) {
			return false;
		}
		
		Piece piece = board.getPiece(startPos);
		
		if (!piece.getType().isPawn()) {
			return false;
		}
		
		// Enemy pawn has to have moved the previous turn
		if (isHistoryEmpty()) {
			return false;
		}
		
		Board lastBoard = getLastBoardState();
		int rowOffset = Integer.signum(endPos.getRow() - startPos.getRow());
		Position enemyStartPos = new Position(endPos.getRow() + rowOffset, endPos.getCol());
		Position enemyEndPos = new Position(endPos.getRow() - rowOffset, endPos.getCol());
		
		if (!lastBoard.isOccupied(enemyStartPos) || lastBoard.isOccupied(enemyEndPos)) {
			return false;
		}
		
		Piece enemyPieceStart = lastBoard.getPiece(enemyStartPos);
		
		if (!enemyPieceStart.getType().isPawn() || enemyPieceStart.isSameColor(piece) || enemyPieceStart.hasMoved()) {
			return false;
		}
		
		// Enemy pawn has to have moved 2 squares
		if (!board.isOccupied(enemyEndPos) || board.isOccupied(enemyStartPos)) {
			return false;
		}
		
		Piece enemyPieceEnd = board.getPiece(enemyEndPos);
		
		if (!enemyPieceEnd.getType().isPawn() || enemyPieceEnd.isSameColor(piece)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isPawnPromotion(Position startPos, Position endPos, Board board) {
		Piece piece = board.getPiece(startPos);
		
		if (!piece.getType().isPawn()) {
			return false;
		}
		
		if ((piece.isWhite() && endPos.getRow() > 0) || (!piece.isWhite() && endPos.getRow() < board.getRowCount() - 1)) {
			return false;
		}
		
		return true;
	}
	
	public void doCastle(Position startPos, Position endPos, Board board) {
		Piece king = board.getPiece(startPos);
		int colDiff = endPos.getCol() - startPos.getCol();
		int rookCol = (Integer.signum(colDiff) > 0) ? board.getColCount() - 1 : 0;
		Position rookStartPos = new Position(startPos.getRow(), rookCol);
		Position rookEndPos = king.getMovePath(startPos, endPos)[0]; // Rook is moved to square King moved through
		
		board.movePiece(startPos, endPos); // Move King to new square after castle
		board.movePiece(rookStartPos, rookEndPos); // Move Rook to new square after castle
	}

	public void doPawnCapture(Position startPos, Position endPos, Board board) {
		if (isEnPassant(startPos, endPos, board)) {
			int rowOffset = Integer.signum(endPos.getRow() - startPos.getRow());
			Position enemyPawnPos = new Position(endPos.getRow() - rowOffset, endPos.getCol());
			board.removePiece(enemyPawnPos);
		}
		
		board.removePiece(endPos);
		board.movePiece(startPos, endPos);
	}
	
	public boolean isPositionAttackedByColor(Position endPos, ChessColor color, Board board) {
		Set<Position> currentPositions = board.getPositionsByColor(color);
		
		for (Position startPos : currentPositions) {
			Piece piece = board.getPiece(startPos);
			
			if (board.isMovePathClear(piece.getMovePath(startPos, endPos))) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isGameOver() {
		return isCheckmated(ChessColor.WHITE) || isCheckmated(ChessColor.BLACK); 
	}
	
	public boolean isCheckmated(ChessColor color) {
		// Cannot be checkmated if King is not under attack
		if (!isChecked(color, chessBoard)) {
			return false;
		}
		
		// King is currently under attack
		
		// Can King move to a safe square?
		Position kingStartPos = chessBoard.getKingPositionByColor(color);
		Set<Position> kingEndPositions = getPseudoLegalMoves(kingStartPos);
		
		for (Position kingEndPos : kingEndPositions) {
			if (isLegalMove(kingStartPos, kingEndPos)) {
				// King can move to safe square; not checkmated
				return false;
			}
		}
		
		// King cannot move to a safe square
		// Can teammate block enemy attacking path?
		Board testBoard;
		Set<Position> teamStartPositions = chessBoard.getPositionsByColor(color);
		
		for (Position teamStartPos : teamStartPositions) {
			Set<Position> teamEndPositions = getPseudoLegalMoves(teamStartPos);
			
			for (Position teamEndPos : teamEndPositions) {
				testBoard = chessBoard.getCopy();
				
				doMove(teamStartPos, teamEndPos, testBoard);
				
				// Teammate makes a move
				// Did teammate block enemy attacking path?
				for (Position kingEndPos : kingEndPositions) {
					if (isLegalMove(kingStartPos, kingEndPos, testBoard)) {
						// King can move to safe square; not checkmated
						return false;
					}
				}
			}
		}
		
		// No legal moves; checkmated
		
		return true;
	}
	
	public Set<Position> getPseudoLegalMoves(Position startPos) {
		Piece piece = chessBoard.getPiece(startPos);
		Set<Position> endPositions = new HashSet<Position>();
		
		for (int row = 0, rows = chessBoard.getRowCount(); row < rows; row++) {
			for (int col = 0, cols = chessBoard.getColCount(); col < cols; col++) {
				Position endPos = new Position(row, col);
				
				if (endPos.equals(startPos)) {
					continue;
				}
				
				// Piece cannot move to square occupied by teammate				
				if (chessBoard.isOccupied(endPos) && chessBoard.getPiece(endPos).isSameColor(piece)) {
					continue;
				}
				
				// Piece-specific movement conditions must be satisfied
				if (isNormalMove(startPos, endPos, chessBoard) || isSpecialMove(startPos, endPos, chessBoard)) {
					endPositions.add(endPos);
				}
			}
		}
		
		return endPositions;
	}
	
	public boolean isChecked(ChessColor color, Board board) {
		return isPositionAttackedByColor(board.getKingPositionByColor(color), getEnemyColor(color), board);
	}
	
	private void saveBoardState() {
		boardHistory.add(chessBoard.getCopy());
	}
	
	private Board getLastBoardState() {
		return boardHistory.get(boardHistory.size() - 1);
	}
	
	public void undoLastMove() {
		if (isHistoryEmpty()) {
			// TODO: Throw exception
			return;
		}
		
		int lastIndex = boardHistory.size() - 1;
		
		chessBoard = boardHistory.get(lastIndex);
		boardHistory.remove(lastIndex);
	}
	
	public boolean isHistoryEmpty() {
		return boardHistory.isEmpty();
	}
	
	public ChessColor getEnemyColor(ChessColor color) {
		return (color.isWhite()) ? ChessColor.BLACK : ChessColor.WHITE;
	}
	
	public ChessColor getTurnColor() {
		return (totalMoves % 2 == 0) ? ChessColor.WHITE : ChessColor.BLACK;
	}
	
	public Board getBoard() {
		return chessBoard;
	}
	
	/*
	 *  Chess Board Layout
	 * 
	 *    ,-----------------,
	 *  0 | r n b q k b n r |
	 *  1 | p p p p p p p p |
     *  2 |				    |
     *  3 |			        |
     *  4 |			        |
     *  5 |				    |
	 *  6 | P P P P P P P P |
	 *  7 | R N B Q K B N R |
	 *    '-----------------'
	 *      0 1 2 3 4 5 6 7
	 *
	 *  Black Pieces - lower case
	 *  White Pieces - UPPER case
	 *  
	 *  R - Rook
	 *  N - Knight
	 *  B - Bishop
	 *  Q - Queen
	 *  K - King
	 *  P - Pawn
	 */
	
	public void initPieces(ChessColor color) {
		int kingRow, pawnRow;
		
		// Black pieces will be placed on first 2 rows
		// White pieces will be placed on last 2 rows
		if (color.isWhite()) {
			kingRow = chessBoard.getRowCount() - 1;
			pawnRow = kingRow - 1;
		}
		else {
			kingRow = 0;
			pawnRow = kingRow + 1;
		}
		
		chessBoard.initPiece(new Position(kingRow, 0), PieceFactory.createPiece(PieceType.ROOK, color));
		chessBoard.initPiece(new Position(kingRow, 1), PieceFactory.createPiece(PieceType.KNIGHT, color));
		chessBoard.initPiece(new Position(kingRow, 2), PieceFactory.createPiece(PieceType.BISHOP, color));
		chessBoard.initPiece(new Position(kingRow, 3), PieceFactory.createPiece(PieceType.QUEEN, color));
		chessBoard.initPiece(new Position(kingRow, 4), PieceFactory.createPiece(PieceType.KING, color));
		chessBoard.initPiece(new Position(kingRow, 5), PieceFactory.createPiece(PieceType.BISHOP, color));
		chessBoard.initPiece(new Position(kingRow, 6), PieceFactory.createPiece(PieceType.KNIGHT, color));
		chessBoard.initPiece(new Position(kingRow, 7), PieceFactory.createPiece(PieceType.ROOK, color));
		
		for (int col = 0, colCount = chessBoard.getColCount(); col < colCount; col++) {
			chessBoard.initPiece(new Position(pawnRow, col), PieceFactory.createPiece(PieceType.PAWN, color));
		}
	}
	
	public void initBoard(int rowCount, int colCount) {
		chessBoard = new Board(rowCount, colCount);
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);
	}
}