package com.seanhoapps.chessgame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.seanhoapps.chessgame.gui.HighlightType;
import com.seanhoapps.chessgame.pieces.Piece;

public class Board {
	private int rows;
	private int columns;

	private Set<Position> emptyPositions = new HashSet<Position>();
	private Map<Position, Piece> whitePositionToPiece = new HashMap<Position, Piece>();
	private Map<Position, Piece> blackPositionToPiece = new HashMap<Position, Piece>();
	private Position whiteKingPosition;
	private Position blackKingPosition;
	private Map<Position, HighlightType> positionToHighlightType = new HashMap<Position, HighlightType>();
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		initEmptyPositions();
	}
	
	// Copy constructor
	private Board(Board board) {
		rows = board.getRowCount();
		columns = board.getColCount();
		copyBoardProperties(board);
	}
	
	public Piece getPiece(Position position) {
		Piece piece;
		if (whitePositionToPiece.containsKey(position)) {
			piece = whitePositionToPiece.get(position);
		}
		else if (blackPositionToPiece.containsKey(position)) {
			piece = blackPositionToPiece.get(position);
		}
		else {
			piece = null;
		}
		return piece;
	}
	
	public void setPiece(Position position, Piece piece) {
		rangeCheck(position);
		
		ChessColor color = piece.getColor();
		getPositionToPiece(color).put(position, piece);
		
		if (piece.getType().isKing()) {
			setKingPosition(color, position);
		}
		
		emptyPositions.remove(position);
	}
	
	public void removePiece(Position position) {
		rangeCheck(position);
		
		// Cannot remove nonexistent piece from empty position
		if (!isOccupied(position)) {
			return;
		}
				
		ChessColor color = getPiece(position).getColor();
		getPositionToPiece(color).remove(position);
		
		emptyPositions.add(position);
	}
	
	public void movePiece(Position startPosition, Position endPosition) {
		rangeCheck(startPosition);
		rangeCheck(endPosition);
		
		// Place piece at new position
		Piece piece = getPiece(startPosition);
		setPiece(endPosition, piece);
		piece.setMoved(true);
		
		// Clear old position
		removePiece(startPosition);
	}
	
	public boolean isOccupied(Position position) {
		rangeCheck(position);
		
		if (getPiece(position) == null) {
			return false;
		}
		return true;
	}
	
	public boolean isValidPosition(Position position) {
		int row = position.getRow();
		int column = position.getCol();
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public Set<Position> getEmptyPositions() {
		return emptyPositions;
	}
	
	public Map<Position, Piece> getPositionToPiece(ChessColor color) {
		return color.isWhite() ? whitePositionToPiece : blackPositionToPiece;
	}
	
	public Position getKingPosition(ChessColor color) {
		return color.isWhite() ? whiteKingPosition : blackKingPosition;
	}
	
	public Map<Position, HighlightType> getPositionToHighlightType() {
		return positionToHighlightType;
	}
	
	public void setHighlight(Position position, HighlightType highlightType) {
		positionToHighlightType.put(position, highlightType);
	}
	
	public void removeHighlight(Position position) {
		positionToHighlightType.remove(position);
	}
	
	public void resetHighlights() {
		positionToHighlightType = new HashMap<Position, HighlightType>();
	}
	
	public int getRowCount() {
		return rows;
	}
	
	public int getColCount() {
		return columns;
	}
	
	public Board getCopy() {
		return new Board(this);
	}
    
	private void initEmptyPositions() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				emptyPositions.add(new Position(row, column));
			}
		}
	}
	
	private void setEmptyPositions(Set<Position> positions) {
		emptyPositions = positions;
	}
	
	private void setPositionToPiece(ChessColor color, Map<Position, Piece> positionToPiece) {
		if (color.isWhite()) {
			whitePositionToPiece = positionToPiece;
		}
		else {
			blackPositionToPiece = positionToPiece;
		}
	}
	
	private void setKingPosition(ChessColor color, Position position) {
		if (color.isWhite()) {
			whiteKingPosition = position;
		}
		else {
			blackKingPosition = position;
		}
	}
	
	private void setPositionToHighlightType(Map<Position, HighlightType> positionToHighlightType) {
		this.positionToHighlightType = positionToHighlightType;
	}
	
	private Set<Position> getEmptyPositionsCopy() {
		Set<Position> copy = new HashSet<Position>();
		for (Position position : getEmptyPositions()) {
			copy.add(position);
		}
		return copy;
	}
	
	private Map<Position, Piece> getPositionToPieceCopy(ChessColor color) {
		Map<Position, Piece> copy = new HashMap<Position, Piece>();
		getPositionToPiece(color).forEach((position, piece) -> {
			copy.put(position, piece.getCopy());
		});
		return copy;
	}
	
	private Map<Position, HighlightType> getPositionToHighlightTypeCopy() {
		Map<Position, HighlightType> copy = new HashMap<Position, HighlightType>();
		getPositionToHighlightType().forEach((position, highlightType) -> {
			copy.put(position, highlightType);
		});
		return copy;
	}
	
	private void copyBoardProperties(Board board) {
		setEmptyPositions(board.getEmptyPositionsCopy());
		
		setPositionToPiece(ChessColor.WHITE, board.getPositionToPieceCopy(ChessColor.WHITE));
		setPositionToPiece(ChessColor.BLACK, board.getPositionToPieceCopy(ChessColor.BLACK));
		
		setKingPosition(ChessColor.WHITE, board.getKingPosition(ChessColor.WHITE));
		setKingPosition(ChessColor.BLACK, board.getKingPosition(ChessColor.BLACK));
		
		setPositionToHighlightType(board.getPositionToHighlightTypeCopy());
	}
	
	private void rangeCheck(Position position) {
		if (!isValidPosition(position)) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(position));
		}
	}
	
	private String outOfBoundsMessage(Position position) {
		return "Row: " + position.getRow() + ", Column: " + position.getCol() + ", Rows: " + rows + ", Columns: " + columns;
	}
}