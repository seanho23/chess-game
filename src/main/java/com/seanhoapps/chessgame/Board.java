package com.seanhoapps.chessgame;

import java.util.HashSet;
import java.util.Set;

import com.seanhoapps.chessgame.pieces.Piece;

public class Board {
	private final int rowCount;
	private final int colCount;
	
	private Square[] boardSquares;
	private Set<Position> whitePositions = new HashSet<Position>();
	private Set<Position> blackPositions = new HashSet<Position>();
	private Position whiteKingPosition = null;
	private Position blackKingPosition = null;
	
	public Board(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
		boardSquares = new Square[getSquareCount()];
		initSquares();
	}
	
	// Copy constructor
	private Board(Board board) {
		this.rowCount = board.getRowCount();
		this.colCount = board.getColCount();
		boardSquares = new Square[board.getSquareCount()];
		copyBoard(board);
	}
	
	public void initPiece(Position pos, Piece piece) {		
		setPiece(pos, piece);
		
		// Store positions for faster access later
		ChessColor color = piece.getColor();
		
		getPositionsByColor(color).add(pos);
		
		// Store King position
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, pos);
		}
	}
	
	public void movePiece(Position startPos, Position endPos) {
		Piece piece = getPiece(startPos);
		
		// Move piece
		setPiece(endPos, piece);
		setPiece(startPos, null);
		piece.hasMoved(true);
		
		// Store positions for faster access later
		ChessColor color = piece.getColor();
		Set<Position> positions = getPositionsByColor(color);
		
		positions.remove(startPos);
		positions.add(endPos);
		
		// Store King position
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, endPos);
		}
	}
	
	public void capturePiece(Position pos) {
		if (!isOccupied(pos)) {
			return;
		}
		
		setPiece(pos, null);
		getPositionsByColor(getPiece(pos).getColor()).remove(pos);
	}
	
	public Piece getPiece(Position pos) {
		return getSquare(pos).getPiece();
	}
	
	public void setPiece(Position pos, Piece piece) {
		getSquare(pos).setPiece(piece);
	}
	
	public Square getSquare(Position pos) {
		return boardSquares[positionToIndex(pos)];
	}
	
	public void setSquare(Position pos, Square square) {
		boardSquares[positionToIndex(pos)] = square;
	}
	
	public boolean isOccupied(Position pos) {
		return getSquare(pos).isOccupied();
	}
	
	public boolean isMovePathClear(Position[] movePath) {
		if (movePath == null) {
			return false;
		}
		
		if (movePath.length <= 0) {
			return true;
		}
		
		for (Position pos : movePath) {
			if (isOccupied(pos)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isValidPosition(Position pos) {
		int i = positionToIndex(pos);
		return i >= 0 && i < getSquareCount();
	}
	
	public Set<Position> getPositionsByColor(ChessColor color) {
		return (color.isWhite()) ? whitePositions : blackPositions;
	}
	
	public Position getKingPositionByColor(ChessColor color) {
		return (color.isWhite()) ? whiteKingPosition : blackKingPosition;
	}
	
	public Board getCopy() {
		return new Board(this);
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public int getColCount() {
		return colCount;
	}
	
	public int getSquareCount() {
		return rowCount * colCount;
	}
	
	private void initSquares() {
		boolean isWhite = true;
		
		for (int i = 0, size = getSquareCount(); i < size; i++) {
			Square square;
			
			if (isWhite) {
				square = new Square(ChessColor.WHITE);
			}
			else {
				square = new Square(ChessColor.BLACK);
			}
			
			setSquare(i, square);
			isWhite = !isWhite; // Alternate between white and black squares
			
			// New row
			if (i % getRowCount() == 0) {
				isWhite = !isWhite; // First square in row is same color as last square in previous row
			}
		}
	}
	
	private int positionToIndex(Position pos) {
		return (pos.getRow() * getRowCount()) + pos.getCol();
	}
	
	private Position indexToPosition(int i) {
		return new Position(i / getRowCount(), i % getColCount());
	}
	
	private Square[] getSquares() {
		return boardSquares;
	}
	
	private Piece getPiece(int i) {
		return getSquare(i).getPiece();
	}
	
	private void setPiece(int i, Piece piece) {
		getSquare(i).setPiece(piece);
	}
	
	private Square getSquare(int i) {
		return boardSquares[i];
	}

	private void setSquare(int i, Square square) {
		boardSquares[i] = square;
	}
	
	private boolean isOccupied(int i) {
		return getSquare(i).isOccupied();
	}
	
	private void setPositionsByColor(ChessColor color, Set<Position> positions) {
		if (color.isWhite()) {
			whitePositions = positions;
		}
		else {
			blackPositions = positions;
		}
	}
	
	private void setKingPositionByColor(ChessColor color, Position pos) {
		if (color.isWhite()) {
			whiteKingPosition = pos;
		}
		else {
			blackKingPosition = pos;
		}
	}
	
	private void setSquares(Square[] squares) {
		boardSquares = squares;
	}
	
	private Square[] copySquares(Square[] squares) {
		Square[] squaresCopy = new Square[squares.length];
		
		for (int i = 0; i < squares.length; i++) {
			squaresCopy[i] = squares[i].getCopy();
		}
		
		return squaresCopy;
	}
	
	private Set<Position> copyPositions(Set<Position> positions) {
		Set<Position> positionsCopy = new HashSet<Position>();
		
		for (Position pos : positions) {
			positionsCopy.add(pos.getCopy());
		}
		
		return positionsCopy;
	}
	
	private void copyBoard(Board board) {
		// Copy squares
		setSquares(copySquares(board.getSquares()));
		
		// Copy positions
		setPositionsByColor(ChessColor.WHITE, copyPositions(board.getPositionsByColor(ChessColor.WHITE)));
		setPositionsByColor(ChessColor.BLACK, copyPositions(board.getPositionsByColor(ChessColor.BLACK)));
		
		setKingPositionByColor(ChessColor.WHITE, board.getKingPositionByColor(ChessColor.WHITE).getCopy());
		setKingPositionByColor(ChessColor.BLACK, board.getKingPositionByColor(ChessColor.BLACK).getCopy());
	}
}
