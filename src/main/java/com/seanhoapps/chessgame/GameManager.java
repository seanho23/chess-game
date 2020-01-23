package com.seanhoapps.chessgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seanhoapps.chessgame.gui.HighlightType;
import com.seanhoapps.chessgame.gui.Observer;
import com.seanhoapps.chessgame.pieces.Piece;
import com.seanhoapps.chessgame.pieces.PieceType;

public class GameManager implements Observable {
	// Constants
	private static final int ROW_COUNT = 8;
	private static final int COL_COUNT = 8;

	private List<Observer> observers = new ArrayList<Observer>();
	private Board chessBoard;
	private List<Board> boardHistory;
	private int totalMoves;
	
	private Position lastStartPosition;
	private Position lastEndPosition;
	
	public void move(Position startPosition, Position endPosition) throws IllegalMovementException, KingInDangerException {
		validateInput(startPosition, endPosition);
		validateMovementStrategy(startPosition, endPosition, chessBoard);
		validateLegality(startPosition, endPosition, chessBoard);		
		
		// Move passes all validation
		
		chessBoard.resetHighlights();
		lastStartPosition = startPosition;
		lastEndPosition = endPosition;
		
		// Perform move on current board
		doMove(startPosition, endPosition, chessBoard);
		saveBoardState();
		notifyObservers(GameEvent.MOVE_COMPLETED);
		
		if (isPawnPromotion(endPosition, chessBoard)) {
			notifyObservers(GameEvent.AWAITING_PROMOTION_RESPONSE);
		}
		
		chessBoard.setHighlight(lastStartPosition, HighlightType.INFO);
		chessBoard.setHighlight(lastEndPosition, HighlightType.INFO);
		
		ChessColor enemyColor = getEnemyColor(getTurnColor());
		if (isChecked(getEnemyColor(getTurnColor()), chessBoard)) {
			chessBoard.setHighlight(chessBoard.getKingPosition(enemyColor), HighlightType.DANGER);
			notifyObservers(GameEvent.KING_CHECKED);
		}
		
		if (isGameOver()) {
			return;
		}
		
		totalMoves++;
		notifyObservers(GameEvent.AWAITING_NEXT_MOVE);
	}
	
	public void promote(PieceType type) {
		if (!isPawnPromotion(lastEndPosition, chessBoard)) {
			return;
		}
		
		Piece piece = PieceFactory.createPiece(type, getTurnColor());
		doPromotion(lastEndPosition, piece, chessBoard);
	}
	
	public void start() {
		chessBoard = new Board(ROW_COUNT, COL_COUNT);
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);
		
		boardHistory = new ArrayList<Board>();
		totalMoves = 0;
		lastStartPosition = null;
		lastEndPosition = null;
		
		notifyObservers(GameEvent.NEW_GAME_STARTED);
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
	
	private void validateInput(Position startPosition, Position endPosition) {
		// Cannot move in place
		if (endPosition.equals(startPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot move out of bounds
		if (!chessBoard.isValidPosition(startPosition) || !chessBoard.isValidPosition(endPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot move an empty position or enemy piece
		Map<Position, Piece> positionToPiece = chessBoard.getPositionToPiece(getTurnColor());
		if (!positionToPiece.containsKey(startPosition)) {
			throw new IllegalArgumentException();
		}
		
		// Cannot capture ally piece
		if (positionToPiece.containsKey(endPosition)) {
			throw new IllegalArgumentException();
		}
	}
	
	private void validateMovementStrategy(Position startPosition, Position endPosition, Board board) throws IllegalMovementException {
		if (!isSpecialMove(startPosition, endPosition, board) && !isNormalMove(startPosition, endPosition, board)) {
			throw new IllegalMovementException();
		}
	}
	
	private void validateLegality(Position startPosition, Position endPosition, Board board) throws KingInDangerException {
		// Test possible next move on copy of current board to prevent illegal moves from affecting actual game
		Board testBoard = board.getCopy();
		doMove(startPosition, endPosition, testBoard);
		
		// Next move is illegal if it will endanger own King
		ChessColor color = board.getPiece(startPosition).getColor();
		if (isChecked(color, testBoard)) {
			throw new KingInDangerException(chessBoard.getKingPosition(color));
		}
	}
	
	private boolean isNormalMove(Position startPosition, Position endPosition, Board board) {
		// Must satisfy piece-specific movement strategy
		Piece pieceToMove = board.getPiece(startPosition);
		if (!pieceToMove.isPossibleMove(startPosition, endPosition)) {
			return false;
		}
		
		// Move path cannot be obstructed
		if (!isMovePathClear(pieceToMove.getMovePath(startPosition, endPosition), board)) {
			return false;
		}
		
		return true;
	}
	
	private boolean isSpecialMove(Position startPosition, Position endPosition, Board board) {
		if (!isCastle(startPosition, endPosition, board) && !isPawnCapture(startPosition, endPosition, board)) {
			return false;
		}
		
		return true;
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
		if (isPositionInDanger(startPosition, board)) {
			return false;
		}
		
		// King cannot castle into check
		if (isPositionInDanger(endPosition, board)) {
			return false;
		}
		
		// King cannot castle through check
		int colOffset = Integer.signum(colDiff);
		Position adjacentPosition = new Position(startPosition.getRow(), startPosition.getCol() + colOffset);
		if (isPositionInDanger(adjacentPosition, board)) {
			return false;
		}
		
		int rookCol = (colOffset > 0) ? board.getColCount() - 1 : 0;
		Position rookPosition = new Position(startPosition.getRow(), rookCol);
		if (!board.isOccupied(rookPosition)) {
			return false;
		}
		
		// Rook castling conditions
		Piece rook = board.getPiece(rookPosition);
		if (!rook.getType().isRook() || rook.hasMoved() || !rook.isSameColor(king)) {
			return false;
		}
		
		// Move path between King and Rook cannot be obstructed
		if (!isMovePathClear(rook.getMovePath(rookPosition, startPosition), board)) {
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

	private boolean isPawnPromotion(Position endPosition, Board board) {
		Piece pawn = board.getPiece(endPosition);
		
		// Only pawns can promote
		if (!pawn.getType().isPawn()) {
			return false;
		}
		
		
		// Pawn can only move forward and promote on last row
		// White pawn
		if (pawn.isWhite()) {
			if (endPosition.getRow() > 0) {
				return false;
			}
		}
		
		// Black pawn
		if (!pawn.isWhite()) {
			if (endPosition.getRow() < board.getRowCount() - 1) {
				return false;
			}
		}
		
		return true;
	}
	
	private void doCastle(Position startPosition, Position endPosition, Board board) {
		int colOffset = Integer.signum(endPosition.getCol() - startPosition.getCol());
		int rookCol = (colOffset > 0) ? board.getColCount() - 1 : 0;
		Position rookStartPosition = new Position(startPosition.getRow(), rookCol);
		// Rook is moved to square King moved through
		Position rookEndPosition = new Position(startPosition.getRow(), startPosition.getCol() + colOffset);
		
		board.movePiece(startPosition, endPosition); // Move King to new square
		board.movePiece(rookStartPosition, rookEndPosition); // Move Rook to new square
	}

	private void doPawnCapture(Position startPosition, Position endPosition, Board board) {
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
	
	private void doPromotion(Position position, Piece piece, Board board) {
		board.removePiece(position);
		board.setPiece(position, piece);
	}
	
	/*
	 * Determines whether piece at target position is in danger
	 * A piece at target position is in danger if an enemy piece can legally move to target position on next turn
	 * Returns true if target position is in danger, otherwise returns false
	 * Also returns false if target position is not occupied
	 */
	private boolean isPositionInDanger(Position targetPosition, Board board) {
		if (!board.isOccupied(targetPosition)) {
			return false;
		}
		
		ChessColor enemyColor = getEnemyColor(board.getPiece(targetPosition).getColor());
		for (Position enemyPosition : board.getPositionToPiece(enemyColor).keySet()) {
			if (isPawnCapture(enemyPosition, targetPosition, board) || isNormalMove(enemyPosition, targetPosition, board)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isGameOver() {
		if (isStalemated(ChessColor.WHITE) || isStalemated(ChessColor.BLACK)) {
			notifyObservers(GameEvent.GAME_OVER_STALEMATED);
			return true;
		}
		
		if (isCheckmated(ChessColor.WHITE) || isCheckmated(ChessColor.BLACK)) {
			notifyObservers(GameEvent.GAME_OVER_CHECKMATED);
			return true;
		}
		
		return false;
	}
	
	private boolean isCheckmated(ChessColor color) {
		// Cannot be checkmated if King is not in danger
		if (!isChecked(color, chessBoard)) {
			return false;
		}
		
		// King is in danger
		// Are there any legal moves to remove King from danger?
		if (hasLegalMoves(color, chessBoard)) {
			// Not checkmated if there are legal moves
			return false;
		}
		
		// No legal moves to remove King from danger
		// Checkmated
		return true;
	}
	
	private boolean isStalemated(ChessColor color) {
		// Cannot be stalemated if King is in danger
		if (isChecked(color, chessBoard)) {
			return false;
		}
		
		// Are there any legal moves?
		if (hasLegalMoves(color, chessBoard)) {
			// Not stalemated if there are legal moves
			return false;
		}
		
		// No legal moves
		// Stalemated
		return true;
	}
	
	/*
	 * Determines whether ChessColor has any legal moves on Board
	 * Returns true if there is at least 1 legal move, otherwise returns false
	 */
	private boolean hasLegalMoves(ChessColor color, Board board) {		
		for (Position startPosition : board.getPositionToPiece(color).keySet()) {
			for (Position endPosition : getPseudoLegalMoves(startPosition)) {
				try {
					validateLegality(startPosition, endPosition, board);
					
					// This is a legal move
					return true;
				}
				catch (KingInDangerException e) {
					// This is an illegal move
					// Validate next move, if any
					continue;
				}
			}
		}

		return false;
	}
	
	/*
	 * Determines all pseudo-legal moves for piece located at start position
	 * A pseudo-legal move is a possible next move made by piece before checking if move will place ally King in danger
	 * Returns a set of end positions or an empty set if no possible moves
	 */
	private Set<Position> getPseudoLegalMoves(Position startPosition) {
		Set<Position> endPositions = new HashSet<Position>();
		if (!chessBoard.isOccupied(startPosition)) {
			return endPositions;
		}
		
		// Can piece move to an empty position?
		for (Position emptyPosition : chessBoard.getEmptyPositions()) {
			if (isSpecialMove(startPosition, emptyPosition, chessBoard) || isNormalMove(startPosition, emptyPosition, chessBoard)) {
				endPositions.add(emptyPosition);
			}
		}
		
		// Can piece move to an enemy position?
		Piece piece = chessBoard.getPiece(startPosition);
		Map<Position, Piece> enemyPositionToPiece = chessBoard.getPositionToPiece(getEnemyColor(piece.getColor()));
		enemyPositionToPiece.forEach((enemyPosition, enemyPiece) -> {
			if (isSpecialMove(startPosition, enemyPosition, chessBoard) || isNormalMove(startPosition, enemyPosition, chessBoard)) {
				endPositions.add(enemyPosition);
			}
		});
		
		return endPositions;
	}
	
	public boolean isChecked() {
		return isChecked(getTurnColor(), chessBoard);
	}
	
	private boolean isChecked(ChessColor color, Board board) {
		return isPositionInDanger(board.getKingPosition(color), board);
	}
	
	public void saveBoardState() {
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
	 * Determines if movePath is clear on Board
	 * A path is clear is if all positions within path are not occupied
	 * Returns true if path is clear, otherwise returns false
	 * Also returns true if movePath is empty
	 */
	private boolean isMovePathClear(Position[] movePath, Board board) {
		if (movePath.length <= 0) {
			return true;
		}
		
		for (Position position : movePath) {
			if (board.isOccupied(position)) {
				return false;
			}
		}
		
		return true;
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
		
		chessBoard.setPiece(new Position(kingRow, 0), PieceFactory.createPiece(PieceType.ROOK, color));
		chessBoard.setPiece(new Position(kingRow, 1), PieceFactory.createPiece(PieceType.KNIGHT, color));
		chessBoard.setPiece(new Position(kingRow, 2), PieceFactory.createPiece(PieceType.BISHOP, color));
		chessBoard.setPiece(new Position(kingRow, 3), PieceFactory.createPiece(PieceType.QUEEN, color));
		chessBoard.setPiece(new Position(kingRow, 4), PieceFactory.createPiece(PieceType.KING, color));
		chessBoard.setPiece(new Position(kingRow, 5), PieceFactory.createPiece(PieceType.BISHOP, color));
		chessBoard.setPiece(new Position(kingRow, 6), PieceFactory.createPiece(PieceType.KNIGHT, color));
		chessBoard.setPiece(new Position(kingRow, 7), PieceFactory.createPiece(PieceType.ROOK, color));
		
		for (int col = 0, colCount = chessBoard.getColCount(); col < colCount; col++) {
			chessBoard.setPiece(new Position(pawnRow, col), PieceFactory.createPiece(PieceType.PAWN, color));
		}
	}
	
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(GameEvent event) {
		for (Observer observer : observers) {
			observer.onGameEvent(event);
		}
	}
}