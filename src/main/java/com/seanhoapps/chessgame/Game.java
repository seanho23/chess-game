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
	
	private void validateInput(Position startPosition, Position endPosition) {
		// Cannot move in place
		if (endPosition.equals(startPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot move out of bounds
		if (!chessBoard.isValidPosition(startPosition) || !chessBoard.isValidPosition(endPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot move an empty square
		if (!chessBoard.isOccupied(startPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot move enemy piece
		Piece piece = chessBoard.getPiece(startPosition);
		if (!piece.isSameColor(getTurnColor())) {
			throw new IllegalArgumentException();
		}
		
		// Cannot capture own piece
		if (chessBoard.isOccupied(endPosition) && chessBoard.getPiece(endPosition).isSameColor(piece)) {
			throw new IllegalArgumentException();
		}
	}
	
	private void validateMove(Position startPosition, Position endPosition, Board board) throws IllegalMoveException {
		if (!isNormalMove(startPosition, endPosition, board) && !isSpecialMove(startPosition, endPosition, board)) {
			throw new IllegalMoveException();
		}
		
//		if (!isLegalMove(startPosition, endPosition, board)) {
//			throw new IllegalMoveException();
//		}
	}
	
	public void move(Position startPosition, Position endPosition) throws IllegalMoveException {
		validateInput(startPosition, endPosition);
		validateMove(startPosition, endPosition, chessBoard);
		
		// Move passes all validation
		
		// Perform move
		doMove(startPosition, endPosition, chessBoard);
		
		saveBoardState();
						
		totalMoves++;
	}
	
	private void doMove(Position startPosition, Position endPosition, Board board) {
		if (isCastle(startPosition, endPosition, board)) {
			doCastle(startPosition, endPosition, board);
		}
		else if (isPawnCapture(startPosition, endPosition, board)) {
			doPawnCapture(startPosition, endPosition, board);
		}
		else {
			// Do normal move
			board.removePiece(endPosition);
			board.movePiece(startPosition, endPosition);
		}
	}
	
	private boolean isLegalMove(Position startPosition, Position endPosition, Board board) {
		Board testBoard = board.getCopy();
		
		doMove(startPosition, endPosition, testBoard);
		
		if (isChecked(board.getPiece(startPosition).getColor(), testBoard)) {
			return false;
		}
		
		return true;
	}
	
	private boolean isNormalMove(Position startPosition, Position endPosition, Board board) {
		// Move must follow piece-specific movement strategy
		Piece pieceToMove = board.getPiece(startPosition);
		if (!pieceToMove.canMove(startPosition, endPosition)) {
			return false;
		}
		
		// Move path cannot be obstructed
		if (!board.isMovePathClear(pieceToMove.getMovePath(startPosition, endPosition))) {
			return false;
		}
		
		return true;
	}
	
	private boolean isSpecialMove(Position startPosition, Position endPosition, Board board) {
		Piece pieceToMove = board.getPiece(startPosition);
		switch (pieceToMove.getType()) {
			case KING:
				if (isCastle(startPosition, endPosition, board)) {
					return true;
				}
			case PAWN:
				if (isPawnCapture(startPosition, endPosition, board)) {
					return true;
				}
			default:
				return false;
		}
	}
	
	private boolean isCastle(Position startPosition, Position endPosition, Board board) {
		Piece king = board.getPiece(startPosition);
		if (!king.getType().isKing()) {
			return false;
		}
		
		// King castling conditions
		int colDiff = endPosition.getCol() - startPosition.getCol();
		if (king.hasMoved() || Math.abs(colDiff) != 2) {
			return false;
		}
		
		// King cannot be in check
		ChessColor enemyColor = getEnemyColor(king.getColor());
		if (isPositionAttackedByColor(startPosition, enemyColor, board)) {
			return false;
		}
		
		// King cannot castle into check
		if (isPositionAttackedByColor(endPosition, enemyColor, board)) {
			return false;
		}
		
		// King cannot castle through check		
		if (isPositionAttackedByColor(king.getMovePath(startPosition, endPosition)[0], enemyColor, board)) {
			return false;
		}
		
		int rookCol = (Integer.signum(colDiff) > 0) ? board.getColCount() - 1 : 0;
		Position rookPosition = new Position(startPosition.getRow(), rookCol);
		if (!board.isOccupied(rookPosition)) {
			return false;
		}
		
		// Rook castling conditions
		Piece rook = board.getPiece(rookPosition);
		if (!rook.getType().isRook() || rook.hasMoved() || rook.isSameColor(king)) {
			return false;
		}
		
		// Move path between King and Rook cannot be obstructed
		if (!board.isMovePathClear(rook.getMovePath(rookPosition, startPosition))) {
			return false;
		}
		
		return true;
	}
	
	private boolean isPawnCapture(Position startPosition, Position endPosition, Board board) {
		Piece pawn = board.getPiece(startPosition);
		if (!pawn.getType().isPawn()) {
			return false;
		}
		
		// Pawn can only move forward
		// White pawn
		if (pawn.isWhite()) {
			if (endPosition.getRow() > startPosition.getRow()) {
				return false;
			}
		}
		
		// Black pawn
		if (!pawn.isWhite()) {
			if (endPosition.getRow() < startPosition.getRow()) {
				return false;
			}
		}
		
		// Can only capture diagonally
		int rowDiff = Math.abs(endPosition.getRow() - startPosition.getRow());
		int colDiff = Math.abs(endPosition.getCol() - startPosition.getCol());
		if (rowDiff != 1 || colDiff != 1) {
			return false;
		}
		
		// If no piece currently available for capture, check if piece can be captured in passing (en passant)
		if (!board.isOccupied(endPosition)) {
			if (!isEnPassantCapture(startPosition, endPosition, board)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isEnPassantCapture(Position startPosition, Position endPosition, Board board) {
		Piece pawn = board.getPiece(startPosition);
		
		int fifthRow = pawn.isWhite() ? board.getRowCount() - 5 : 4;
		
		if (startPosition.getRow() != fifthRow) {
			return false;
		}
		
		if (!pawn.getType().isPawn()) {
			return false;
		}
		
		int colOffset = Integer.signum(endPosition.getCol() - startPosition.getCol());
		Position enemyPawnPosition = new Position(startPosition.getRow(), startPosition.getCol() + colOffset);
		if (board.isOccupied(endPosition) || !board.isOccupied(enemyPawnPosition)) {
			return false;
		}
		
		Piece enemyPawn = board.getPiece(enemyPawnPosition);
		if (!enemyPawn.getType().isPawn() || enemyPawn.isSameColor(pawn)) {
			return false;
		}
		
		if (!hasTurnHistory()) {
			return false;
		}
		
		int enemyPawnStartRow = enemyPawn.isWhite() ? board.getRowCount() - 2 : 1;
		Position enemyPawnLastPosition = new Position(enemyPawnStartRow, endPosition.getCol());
		Board enemyBoardState = getLastBoardState();
		if (!enemyBoardState.isOccupied(enemyPawnLastPosition)) {
			return false;
		}
		
		if (!enemyBoardState.getPiece(enemyPawnLastPosition).equals(enemyPawn)) {
			return false;
		}
		
		return true;
	}

	// TODO: Pawn promotion
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
	
	private void doCastle(Position startPosition, Position endPosition, Board board) {
		int colDiff = endPosition.getCol() - startPosition.getCol();
		int rookCol = (Integer.signum(colDiff) > 0) ? board.getColCount() - 1 : 0;
		Position rookStartPosition = new Position(startPosition.getRow(), rookCol);
		
		// Rook is moved to square King moved through
		Piece king = board.getPiece(startPosition);
		Position rookEndPosition = king.getMovePath(startPosition, endPosition)[0];
		
		board.movePiece(startPosition, endPosition); // Move King to new square
		board.movePiece(rookStartPosition, rookEndPosition); // Move Rook to new square
	}

	public void doPawnCapture(Position startPosition, Position endPosition, Board board) {
		if (isEnPassantCapture(startPosition, endPosition, board)) {
			int colOffset = Integer.signum(endPosition.getCol() - startPosition.getCol());
			Position enemyPawnPosition = new Position(startPosition.getRow(), startPosition.getCol() + colOffset);
			
			// En passant capture enemy pawn
			board.removePiece(enemyPawnPosition);
		}
		
		// Normal capture
		board.removePiece(endPosition);
		board.movePiece(startPosition, endPosition);
	}
	
	public boolean isPositionAttackedByColor(Position targetPosition, ChessColor color, Board board) {
		Set<Position> currentPositions = board.getPositionsByColor(color);
		
		for (Position currentPosition : currentPositions) {
			if (isNormalMove(currentPosition, targetPosition, board) || isSpecialMove(currentPosition, targetPosition, board)) {
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
			if (isLegalMove(kingStartPos, kingEndPos, chessBoard.getCopy())) {
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
		if (!hasTurnHistory()) {
			return null;
		}
		
		return boardHistory.get(boardHistory.size() - 2);
	}
	
	public void undoLastTurn() {
		if (!hasTurnHistory()) {
			// TODO: Throw exception
			return;
		}
		
		chessBoard = getLastBoardState();
		boardHistory.remove(boardHistory.size() - 1);
		boardHistory.remove(chessBoard);
		totalMoves--;
	}
	
	public boolean hasTurnHistory() {
		return boardHistory.size() >= 2;
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
	
	private void initPieces(ChessColor color) {
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
	
	private void initBoard(int rowCount, int colCount) {
		chessBoard = new Board(rowCount, colCount);
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);
		saveBoardState();
	}
}