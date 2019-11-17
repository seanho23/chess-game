package com.seanhoapps.chessgame;

import java.util.HashSet;
import java.util.Set;

import com.seanhoapps.chessgame.pieces.Piece;

public class Board {
	private final int rowCount;
	private final int colCount;
	private final int squareCount;
	
	private Square[] boardSquares;
	private Set<Position> whitePositions = new HashSet<Position>();
	private Set<Position> blackPositions = new HashSet<Position>();
	private Position whiteKingPosition;
	private Position blackKingPosition;
	
	public Board(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.squareCount = this.rowCount * this.colCount;
		boardSquares = new Square[getSquareCount()];
		initSquares();
	}
	
	// Copy constructor
	private Board(Board board) {
		rowCount = board.getRowCount();
		colCount = board.getColCount();
		squareCount = board.getSquareCount();
		boardSquares = new Square[squareCount];
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
		int i = positionToIndex(pos);
		return i >= 0 && i < getSquareCount();
	}
	
	public Set<Position> getPositionsByColor(ChessColor color) {
		return (color.isWhite()) ? whitePositions : blackPositions;
	}
	
	public Position getKingPositionByColor(ChessColor color) {
		return (color.isWhite()) ? whiteKingPosition : blackKingPosition;
	}
	
	public Piece getPiece(int i) {
		rangeCheck(i);
		
		return getSquare(i).getPiece();
	}
	
	public Piece getPiece(Position pos) {
		rangeCheck(pos);
		
		return getSquare(pos).getPiece();
    
	// Private methods
	
	private void initSquares() {
		boolean isWhite = true;
		
		for (int i = 0; i < squareCount; i++) {
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
			if (i % rowCount == 0) {
				isWhite = !isWhite; // First square in row is same color as last square in previous row
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
	
	private Square[] getSquares() {
		return boardSquares;
	}
	
	private void setSquares(Square[] squares) {
		boardSquares = squares;
	}
	
	private Square getSquare(int i) {
		rangeCheck(i);
		
		return boardSquares[i];
	}

	private void setSquare(int i, Square square) {
		rangeCheck(i);
		
		boardSquares[i] = square;
	}
	
	private Square getSquare(Position pos) {
		rangeCheck(pos);
		
		return boardSquares[positionToIndex(pos)];
	}
	
	private void setSquare(Position pos, Square square) {
		rangeCheck(pos);
		
		boardSquares[positionToIndex(pos)] = square;
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
		
		if (row < 0 || row > rowCount || col < 0 || col > colCount) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(row, col));
		}
	}
  
	private void rangeCheck(int index) {
		if (index < 0 || index > squareCount) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
		}
	}
	
	private String outOfBoundsMessage(int row, int col) {
		return "Row: " + row + ", Column: " + col + ", Rows: " + rowCount + ", Columns: " + colCount;
	}
	
	private String outOfBoundsMessage(int index) {
		return "Index: " + index + ", Size: " + squareCount;
	}
}
