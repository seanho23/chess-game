package com.seanhoapps.chessgame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.seanhoapps.chessgame.gui.HighlightType;
import com.seanhoapps.chessgame.pieces.Piece;

public class Board {
	private Square[][] boardSquares;
	private Map<Position, Square> highlightedSquares = new HashMap<Position, Square>();
	
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
				
		// Move piece to new square
		Piece piece = getPiece(startPos);
		setPiece(endPos, piece);
		piece.setMoved(true);
		
		// Store positions for faster access later
		ChessColor color = piece.getColor();
		getPositionsByColor(color).add(endPos);
				
		// Store King position separately
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, endPos);
		}
		
		// Clear old square
		removePiece(startPos);
	}
	
  public void removePiece(Position pos) {
		rangeCheck(pos);
		
		if (!isOccupied(pos)) {
			return;
		}
		
		Piece piece = getPiece(pos);
		ChessColor color = piece.getColor();
		setPiece(pos, null);
		getPositionsByColor(color).remove(pos);
		
		// Set King position
		// This should not happen because game should end before King is captured
		if (piece.getType().isKing()) {
			setKingPositionByColor(color, null);
		}
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
	
	public Piece getPiece(int row, int col) {
		rangeCheck(row, col);
		
		return getSquare(row, col).getPiece();
	}
	
	public Piece getPiece(Position pos) {
		rangeCheck(pos);
		
		return getSquare(pos).getPiece();
	}
	
	public void setPiece(Position pos, Piece piece) {
		rangeCheck(pos);
		
		getSquare(pos).setPiece(piece);
	}
	
	public boolean isOccupied(int row, int col) {
		rangeCheck(row, col);
		
		return getSquare(row, col).isOccupied();
	}
	
	public boolean isOccupied(Position pos) {
		rangeCheck(pos);
		
		return getSquare(pos).isOccupied();
	}
	
	public Map<Position, Square> getHighlightedSquares() {
		return highlightedSquares;
	}
		
	public HighlightType getHighlight(Position position) {
		rangeCheck(position);
		
		return getSquare(position).getHighlight();
	}
	
	public void setHighlight(Position position, HighlightType highlight) {
		rangeCheck(position);
		
		Square square = getSquare(position);
		square.setHighlight(highlight);
		highlightedSquares.put(position, square);
	}
	
	public void clearHighlight(Position position) {
		rangeCheck(position);
		
		getSquare(position).setHighlight(null);
		highlightedSquares.remove(position);
	}
	
	public void clearAllHighlights() {
		Iterator<Entry<Position, Square>> iterator = highlightedSquares.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Position, Square> entry = iterator.next();
			entry.getValue().setHighlight(null);
			iterator.remove();
		}
	}
	
	public boolean isHighlighted(Position position) {
		rangeCheck(position);
		
		return getSquare(position).isHighlighted();
	}
	
	public Square getSquare(int row, int col) {
		rangeCheck(row, col);
		
		return boardSquares[row][col];
	}
	
	public Square getSquare(Position pos) {
		rangeCheck(pos);
		
		return boardSquares[pos.getRow()][pos.getCol()];
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
	
	private Square[][] getSquares() {
		return boardSquares;
	}
	
	private void setSquares(Square[][] squares) {
		boardSquares = squares;
	}
	
	private void setHighlightedSquares(Map<Position, Square> highlightedSquares) {
		this.highlightedSquares = highlightedSquares;
	}
	
	private void setSquare(Position pos, Square square) {
		rangeCheck(pos);
		
		boardSquares[pos.getRow()][pos.getCol()] = square;
	}
	
	private Square[][] copySquares(Square[][] squares) {
		int rows = getRowCount();
		int cols = getColCount();
		Square[][] squaresCopy = new Square[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				squaresCopy[row][col] = squares[row][col].getCopy();;
			}
		}
		return squaresCopy;
	}
	
	private Map<Position, Square> copyHighlightedSquares(Map<Position, Square> highlightedSquares) {
		Map<Position, Square> highlightedSquaresCopy = new HashMap<Position, Square>();
		highlightedSquares.forEach((position, square) -> {
			highlightedSquaresCopy.put(position, square);
		});
		return highlightedSquaresCopy;
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
		setHighlightedSquares(copyHighlightedSquares(board.getHighlightedSquares()));
		
		// Copy positions
		setPositionsByColor(ChessColor.WHITE, copyPositions(board.getPositionsByColor(ChessColor.WHITE)));
		setPositionsByColor(ChessColor.BLACK, copyPositions(board.getPositionsByColor(ChessColor.BLACK)));
		
		setKingPositionByColor(ChessColor.WHITE, board.getKingPositionByColor(ChessColor.WHITE));
		setKingPositionByColor(ChessColor.BLACK, board.getKingPositionByColor(ChessColor.BLACK));
	}
	
	private void rangeCheck(int row, int col) {
		if (row < 0 || row > getRowCount() || col < 0 || col > getColCount()) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(row, col));
		}
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
	
	public void printPieces() {
		for (int row = 0, rows = getRowCount(); row < rows; row++) {
			String space = "";
			for (int col = 0, cols = getColCount(); col < cols; col++) {
				char abbr = ' ';
				if (isOccupied(row, col)) {
					Piece piece = getPiece(row, col);
					abbr = piece.getAbbreviation();
					abbr = piece.isWhite() ? Character.toUpperCase(abbr) : Character.toLowerCase(abbr);
				}
				System.out.print(space + abbr);
				space = " ";
			}
			System.out.println();
		}
	}
}
