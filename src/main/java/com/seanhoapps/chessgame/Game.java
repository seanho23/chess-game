package com.seanhoapps.chessgame;

public class Game {
	// Constants
	private static final int ROW_SIZE = 8;
	private static final int COL_SIZE = 8;
	
	private Board board;
	
	public Game(Player whitePlayer, Player blackPlayer) {
		initBoard(ROW_SIZE, COL_SIZE);
		board.printSquareColors();
		System.out.println();
		board.printPieces();
	}
	
	public void performMove(Position startPos, Position endPos) {
		if (endPos.equals(startPos)) {
			return;
		}
		
		if (!board.isValidPosition(startPos) || !board.isValidPosition(endPos)) {
			return;
		}
		
		Square square = board.getSquare(startPos);
		
		if (!square.isOccupied()) {
			return;
		}
		
		// Piece cannot capture teammate
		Piece piece = square.getPiece();
		Square targetSquare = board.getSquare(endPos);
		
		if (targetSquare.isOccupied()) {
			Piece targetPiece = targetSquare.getPiece();
			
			if (targetPiece.isSameColor(piece)) {
				return;
			}
		}
		
		if (!piece.isPossibleMove(startPos, endPos)) {
			return;
		}
		
		Position[] movePath = piece.getMovePath(startPos, endPos);
		
		if (!isMovePathClear(movePath)) {
			return;
		}
		
		if (isSpecialMove(startPos, endPos, piece)) {
			return;
		}
		
		// Perform move
		movePiece(startPos, endPos);
		 // TODO: Log move
	}
	
	public void performCastle(Position startPos, Position endPos, Piece king) {
		int colDiffSign = Integer.signum(endPos.getCol() - startPos.getCol());
		int rookCol = (colDiffSign > 0) ? board.getColSize() - 1 : 0;
		Position rookPos = new Position(startPos.getRow(), rookCol);
		Position[] movePath = king.getMovePath(startPos, endPos);

		movePiece(startPos, endPos);
		movePiece(rookPos, movePath[0]); // Rook is moved to square King moved through
		// TODO: Log move
	}
	
	public void performPawnCapture(Position startPos, Position endPos) {
		// TODO: Implement
	}
	
	public boolean isSpecialMove(Position startPos, Position endPos, Piece piece) {
		PieceType type = piece.getType();
		
		switch(type) {
			case KING:
				if (isValidCastle(startPos, endPos, piece)) {
					performCastle(startPos, endPos, piece);
					return true;
				}
				
				break;
			case PAWN:				
				if (isValidPawnCapture(startPos, endPos, piece)) {
					performPawnCapture(startPos, endPos);
					return true;
				}
			default:
				return false;
		}
		
		return false;
	}
	
	public boolean isValidCastle(Position startPos, Position endPos, Piece king) {
		// King castling conditions
		if (!king.getType().isKing()) {
			return false;
		}
		int colDiff = endPos.getCol() - startPos.getCol();
		
		if (king.hasMoved() || Math.abs(colDiff) != 2) {
			return false;
		}
		
		// King cannot be in check
		ChessColor enemyColor = getEnemyColor(king.getColor());
		
		if (isAttackableByColor(startPos, enemyColor)) {
			return false;
		}
		
		// Rook castling conditions
		int colDiffSign = Integer.signum(colDiff);
		int rookCol = (colDiffSign > 0) ? board.getColSize() - 1 : 0;
		Position rookPos = new Position(startPos.getRow(), rookCol);
		Square rookSquare = board.getSquare(rookPos);
		
		if (!rookSquare.isOccupied()) {
			return false;
		}
		
		Piece rook = rookSquare.getPiece();
		
		if (!rook.getType().isRook() || !rook.isSameColor(king) || rook.hasMoved()) {
			return false;
		}
		
		// No pieces between King and Rook
		Position[] castlePath = getCastlePath(startPos, rookPos);
		
		if (!isMovePathClear(castlePath)) {
			return false;
		}
		
		// King cannot move through check
		Position[] movePath = king.getMovePath(startPos, endPos);
		
		if (isAttackableByColor(movePath[0], enemyColor)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isValidPawnCapture(Position startPos, Position endPos, Piece pawn) {
		if (!pawn.getType().isPawn()) {
			return false;
		}
		
		int rowDiff = Math.abs(endPos.getRow() - startPos.getRow());
		int colDiff = Math.abs(endPos.getCol() - startPos.getCol());
		return rowDiff == 1 && colDiff == 1;
	}
	
	public void movePiece(Position startPos, Position endPos) {
		Piece piece = board.getPiece(startPos);
		piece.hasMoved(true);
		board.setPiece(endPos, piece);
		board.setPiece(startPos, null);
	}
	
	public boolean isChecked(ChessColor color) {
		Position kingPos = getKingPosition(color);
		ChessColor enemyColor = getEnemyColor(color);
		return isAttackableByColor(kingPos, enemyColor);
	}
	
	public boolean isAttackableByColor(Position endPos, ChessColor color) {
		for (int row = 0; row < board.getRowSize(); row++) {
			for (int col = 0; col < board.getColSize(); col++) {
				Position startPos = new Position(row, col);
				Square square = board.getSquare(startPos);
				
				if (square.isOccupied()) {
					Piece piece = square.getPiece();
					
					if (piece.getColor() == color) {
						if (piece.isPossibleMove(startPos, endPos)) {
							Position[] movePath = piece.getMovePath(startPos, endPos);
							
							if (isMovePathClear(movePath)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	public boolean isMovePathClear(Position[] movePath) {
		if (movePath.length <= 0) {
			return true;
		}
		
		for (Position pos : movePath) {
			if (board.getSquare(pos).isOccupied()) {
				return false;
			}
		}
		
		return true;
	}
	
	public Position getKingPosition(ChessColor color) {
		for (int row = 0; row < board.getRowSize(); row++) {
			for (int col = 0; col < board.getColSize(); col++) {
				Position pos = new Position(row, col);
				Square square = board.getSquare(pos);
				
				if (square.isOccupied()) {
					Piece piece = square.getPiece();
					
					if (piece.getType().isKing() && piece.getColor() == color) {
						return pos;
					}
				}
			}
		}
		
		return null; // Should never happen because game should be over before King is captured
	}
	
	public Position[] getCastlePath(Position kingPos, Position rookPos) {
		int colDiff = rookPos.getCol() - kingPos.getCol();
		int colDiffSign = Integer.signum(colDiff);
		int pathLength = Math.abs(colDiff) - 1;
		Position[] path = new Position[pathLength];
		
		for (int i = 1; i < pathLength; i++) {
			int colOffset = i * colDiffSign;
			path[i - 1] = new Position(kingPos.getRow(), kingPos.getCol() + colOffset);
		}
		
		return path;
	}
	
	public ChessColor getEnemyColor(ChessColor color) {
		return (color.isWhite()) ? ChessColor.BLACK : ChessColor.WHITE;
	}
	
	public void initBoard(int rowSize, int colSize) {
		board = new Board(rowSize, colSize);
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);
	}
	
	public void initPieces(ChessColor color) {
		int kingRow, pawnRow;
		
		if (color.isWhite()) {
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