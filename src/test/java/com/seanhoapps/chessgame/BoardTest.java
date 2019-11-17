package com.seanhoapps.chessgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.Sets;
import com.seanhoapps.chessgame.pieces.*;

public class BoardTest {
	// Constants
	private static final int ROW_COUNT = 8;
	private static final int COL_COUNT = 8;
	
	private Board board;
	
	@BeforeEach
	public void setUp() {
		board = new Board(ROW_COUNT, COL_COUNT);
	}
	
	@ParameterizedTest
	@MethodSource("validPositionAnyPieceProvider")
	public void initPiece_validPositionAnyPiece_placesPieceUpdatesPositions(List<Object> parameters) {
		// Arrange
		Position position = (Position) parameters.get(0);
		Piece piece = (Piece) parameters.get(1);
		
		// Act
		board.initPiece(position, piece);
		
		// Assert
		Set<Position> actualPositions = board.getPositionsByColor(piece.getColor());
		
		assertEquals(piece, board.getPiece(position));
		assertTrue(actualPositions.contains(position));
	}
	
	@ParameterizedTest
	@MethodSource("validPositionAnyKingProvider")
	public void initPiece_validPositionAnyKing_updatesKingPosition(List<Object> parameters) {
		// Arrange
		Position position = (Position) parameters.get(0);
		Piece king = (Piece) parameters.get(1);
		
		// Act
		board.initPiece(position, king);
		
		// Assert
		assertEquals(position, board.getKingPositionByColor(king.getColor()));
	}
	
	@ParameterizedTest
	@MethodSource("invalidPositionAnyPieceProvider")
	public void initPiece_invalidPositionAnyPiece_throwsIndexOutOfBoundsException(List<Object> parameters) {
		// Arrange
		Position position = (Position) parameters.get(0);
		Piece piece = (Piece) parameters.get(1);
		
		// Act and Assert
		assertThrows(IndexOutOfBoundsException.class, () -> {
			board.initPiece(position, piece);
		});
	}
	
	@ParameterizedTest
	@MethodSource("validStartEndPositionAnyPieceProvider")
	public void movePiece_validStartEndPositionAnyPiece_movesPieceUpdatesPositions(List<Object> parameters) {
		// Arrange
		Position startPosition = (Position) parameters.get(0);
		Position endPosition = (Position) parameters.get(1);
		Piece piece = (Piece) parameters.get(2);
		
		board.initPiece(startPosition, piece);
		
		// Act
		board.movePiece(startPosition, endPosition);
		
		// Assert
		Set<Position> actualPositions = board.getPositionsByColor(piece.getColor());
		
		assertEquals(piece, board.getPiece(endPosition));
		assertNull(board.getPiece(startPosition));
		assertTrue(actualPositions.contains(endPosition));
		assertFalse(actualPositions.contains(startPosition));
		assertTrue(piece.hasMoved());
	}
	
	@ParameterizedTest
	@MethodSource("validStartEndPositionAnyKingProvider")
	public void movePiece_validStartEndPositionAnyKing_updatesKingPosition(List<Object> parameters) {
		// Arrange
		Position startPosition = (Position) parameters.get(0);
		Position endPosition = (Position) parameters.get(1);
		Piece king = (Piece) parameters.get(2);
		
		board.initPiece(startPosition, king);
		
		// Act
		board.movePiece(startPosition, endPosition);
		
		// Assert		
		assertEquals(endPosition, board.getKingPositionByColor(king.getColor()));
	}
	
	@ParameterizedTest
	@MethodSource("validStartInvalidEndPositionAnyPieceProvider")
	public void movePiece_validStartInvalidEndPositionAnyPiece_throwsIndexOutOfBoundsException(List<Object> parameters) {
		// Arrange
		Position startPosition = (Position) parameters.get(0);
		Position endPosition = (Position) parameters.get(1);
		Piece piece = (Piece) parameters.get(2);
		
		board.initPiece(startPosition, piece);
		
		// Act and Assert
		assertThrows(IndexOutOfBoundsException.class, () -> {
			board.movePiece(startPosition, endPosition);
		});
	}
	
	@ParameterizedTest
	@MethodSource("validPositionAnyPieceProvider")
	public void removePiece_validPositionAnyPiece_removesPieceUpdatesPositions(List<Object> parameters) {
		// Arrange
		Position position = (Position) parameters.get(0);
		Piece piece = (Piece) parameters.get(1);
		
		board.initPiece(position, piece);
		
		// Act
		board.removePiece(position);
		
		// Assert
		Set<Position> actualPositions = board.getPositionsByColor(piece.getColor());
		
		assertNull(board.getPiece(position));
		assertFalse(actualPositions.contains(position));
	}
	
	@ParameterizedTest
	@MethodSource("validStartInvalidEndPositionAnyPieceProvider")
	public void removePiece_invalidPositionAnyPiece_throwsIndexOutOfBoundsException(List<Object> parameters) {
		// Arrange
		Position validPosition = (Position) parameters.get(0);
		Position invalidPosition = (Position) parameters.get(1);
		Piece piece = (Piece) parameters.get(2);
		
		board.initPiece(validPosition, piece);
		
		// Act and Assert
		assertThrows(IndexOutOfBoundsException.class, () -> {
			board.removePiece(invalidPosition);
		});
	}
	
	@ParameterizedTest
	@MethodSource("validStartInvalidEndPositionAnyPieceProvider")
	public void removePiece_invalidPosition_throwsIndexOutOfBoundsException(List<Object> parameters) {
		// Arrange
		Position validPosition = (Position) parameters.get(0);
		Position invalidPosition = (Position) parameters.get(1);
		Piece piece = (Piece) parameters.get(2);
		
		board.initPiece(validPosition, piece);
		
		// Act and Assert
		assertThrows(IndexOutOfBoundsException.class, () -> {
			board.removePiece(invalidPosition);
		});
	}
	
	
	private static Set<List<Object>> validPositionAnyPieceProvider() {
		return Sets.cartesianProduct(validStartPositionsProvider(), allPiecesProvider());
	}
	
	private static Set<List<Object>> validPositionAnyKingProvider() {
		return Sets.cartesianProduct(validStartPositionsProvider(), kingsProvider());
	}
	
	private static Set<List<Object>> invalidPositionAnyPieceProvider() {
		return Sets.cartesianProduct(invalidPositionsProvider(), allPiecesProvider());
	}
	
	private static Set<List<Object>> validStartEndPositionAnyPieceProvider() {
		return Sets.cartesianProduct(validStartPositionsProvider(), validEndPositionsProvider(), allPiecesProvider());
	}
	
	private static Set<List<Object>> validStartEndPositionAnyKingProvider() {
		return Sets.cartesianProduct(validStartPositionsProvider(), validEndPositionsProvider(), kingsProvider());
	}
	
	private static Set<List<Object>> validStartInvalidEndPositionAnyPieceProvider() {
		return Sets.cartesianProduct(validStartPositionsProvider(), invalidPositionsProvider(), allPiecesProvider());
	}
	
	private static Set<Position> validStartPositionsProvider() {
		Set<Position> positions = new HashSet<Position>();
		
		positions.add(new Position(2, 3));
		positions.add(new Position(5, 0));
		positions.add(new Position(7, 7));
		positions.add(new Position(0, 0));
		positions.add(new Position(0, 5));
		
		return positions;
	}
	
	private static Set<Position> validEndPositionsProvider() {
		Set<Position> positions = new HashSet<Position>();
		
		positions.add(new Position(5, 2));
		positions.add(new Position(1, 6));
		positions.add(new Position(7, 2));
		positions.add(new Position(6, 0));
		positions.add(new Position(2, 1));
		
		return positions;
	}
	
	private static Set<Position> invalidPositionsProvider() {
		Set<Position> positions = new HashSet<Position>();
		
		positions.add(new Position(-3, -3));
		positions.add(new Position(-1, 3));
		positions.add(new Position(3, -1));
		positions.add(new Position(9, 3));
		positions.add(new Position(3, 9));
		
		return positions;
	}
	
	private static Set<Piece> allPiecesProvider() {
		ChessColor[] colors = new ChessColor[] {ChessColor.WHITE, ChessColor.BLACK};
		Set<Piece> pieces = new HashSet<Piece>();
		
		for (ChessColor color : colors) {
			pieces.add(new King(color));
			pieces.add(new Queen(color));
			pieces.add(new Rook(color));
			pieces.add(new Knight(color));
			pieces.add(new Bishop(color));
			pieces.add(new Pawn(color));
		}
		
		return pieces;
	}
	
	private static Set<Piece> kingsProvider() {
		ChessColor[] colors = new ChessColor[] {ChessColor.WHITE, ChessColor.BLACK};
		Set<Piece> kings = new HashSet<Piece>();
		
		for (ChessColor color : colors) {
			kings.add(new King(color));
		}
		
		return kings;
	}
}
