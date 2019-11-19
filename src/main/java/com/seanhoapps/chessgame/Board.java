package com.seanhoapps.chessgame;

import java.util.HashSet;
import java.util.Set;

import com.seanhoapps.chessgame.pieces.Piece;

public class Board {
	private Square[][] boardSquares;
	private Set<Position> whitePositions = new HashSet<Position>();
	private Set<Position> blackPositions = new HashSet<Position>();
	private Position whiteKingPosition;
	private Position blackKingPosition;
	
	public Board(int rowCount, int colCount) {
		boardSquares = new Square[rowCount][colCount];
		initSquares();
	}
	
	// Copy constructor
	private Board(Board board) {
		boardSquares = new Square[board.getRowCount()][board.getColCount()];
		copyBoard(board);
	}
	
	public void initPiece(Position pos, Piece piece) {
		rangeCheck(pos);
		
		setPiece(pos, piece);
		
		// Store positions for faster access later
		ChessColor color = piece.getColor();
		
		getPositionsByColor(color).add(pos);
		
		// Store King position separately
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, pos);
		}
	}
	
	public void movePiece(Position startPos, Position endPos) {
		rangeCheck(startPos);
		rangeCheck(endPos);
		
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
		
		// Store King position separately
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, endPos);
		}
	}
	
  public void removePiece(Position pos) {
		rangeCheck(pos);
		
		ChessColor color = getPiece(pos).getColor();
		
		setPiece(pos, null);
		getPositionsByColor(color).remove(pos);
	}
	
	public boolean isMovePathClear(Position[] movePath) {
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
		int row = pos.getRow();
		int col = pos.getCol();
		
		return row >= 0 && row < getRowCount() && col >= 0 && col < getColCount();
	}
	
	public Set<Position> getPositionsByColor(ChessColor color) {
		return (color.isWhite()) ? whitePositions : blackPositions;
	}
	
	public Position getKingPositionByColor(ChessColor color) {
		return (color.isWhite()) ? whiteKingPosition : blackKingPosition;
	}
	
	public Piece getPiece(Position pos) {
		rangeCheck(pos);
		
		return getSquare(pos).getPiece();
	}
	
	public void setPiece(Position pos, Piece piece) {
		rangeCheck(pos);
		
		getSquare(pos).setPiece(piece);
	}
	
	public boolean isOccupied(Position pos) {
		rangeCheck(pos);
		
		return getSquare(pos).isOccupied();
	}
	
	public Square[][] getSquares() {
		return boardSquares;
	}
	
	public int getRowCount() {
		return boardSquares.length;
	}
	
	public int getColCount() {
		return boardSquares[0].length;
	}
	
	public Board getCopy() {
		return new Board(this);
	}
    
	// Private methods
	
	private void initSquares() {
		for (int row = 0, rows = getRowCount(); row < rows; row++) {
			for (int col = 0, cols = getColCount(); col < cols; col++) {
				Square square;
				
				if ((row + col) % 2 == 0) {
					square = new Square(ChessColor.WHITE);
				}
				else {
					square = new Square(ChessColor.BLACK);
				}
				
				setSquare(new Position(row, col), square);
			}
		}
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
	
	private void setSquares(Square[][] squares) {
		boardSquares = squares;
	}
	
	private Square getSquare(Position pos) {
		rangeCheck(pos);
		
		return boardSquares[pos.getRow()][pos.getCol()];
	}
	
	private void setSquare(Position pos, Square square) {
		rangeCheck(pos);
		
		boardSquares[pos.getRow()][pos.getCol()] = square;
	}
	
	private Square[][] copySquares(Square[][] squares) {
		int rows = squares.length;
		int cols = squares[0].length;
		Square[][] squaresCopy = new Square[rows][cols];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				squaresCopy[row][col] = squares[row][col].getCopy();
			}
		}
		
		return squaresCopy;
	}
	
	private Set<Position> copyPositions(Set<Position> positions) {
		Set<Position> positionsCopy = new HashSet<Position>();
		
		for (Position pos : positions) {
			positionsCopy.add(pos);
		}
		
		return positionsCopy;
	}
	
	private void copyBoard(Board board) {
		// Copy squares
		setSquares(copySquares(board.getSquares()));
		
		// Copy positions
		setPositionsByColor(ChessColor.WHITE, copyPositions(board.getPositionsByColor(ChessColor.WHITE)));
		setPositionsByColor(ChessColor.BLACK, copyPositions(board.getPositionsByColor(ChessColor.BLACK)));
		
		setKingPositionByColor(ChessColor.WHITE, board.getKingPositionByColor(ChessColor.WHITE));
		setKingPositionByColor(ChessColor.BLACK, board.getKingPositionByColor(ChessColor.BLACK));
	}
	
	private void rangeCheck(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		
		if (row < 0 || row > getRowCount() || col < 0 || col > getColCount()) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(row, col));
		}
	}
	
	private String outOfBoundsMessage(int row, int col) {
		return "Row: " + row + ", Column: " + col + ", Rows: " + getRowCount() + ", Columns: " + getColCount();
	}
}
