package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.PieceFactory;
import com.seanhoapps.chessgame.Position;

public class PawnTest {
	
	@ParameterizedTest
	@MethodSource("whiteLegalMoveProvider")
	public void isPossibleMove_whiteLegalMove_returnTrue(List<Object> arguments) {
		// Arrange
		Position startPosition = (Position) arguments.get(0);
		Position endPosition = (Position) arguments.get(1);
		
		// Act
		boolean isPossibleMove = CreateTarget(ChessColor.WHITE).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertTrue(isPossibleMove);
	}
	
	@ParameterizedTest
	@MethodSource("blackLegalMoveProvider")
	public void isPossibleMove_blackLegalMove_returnTrue(List<Object> arguments) {
		// Arrange
		Position startPosition = (Position) arguments.get(0);
		Position endPosition = (Position) arguments.get(1);
		
		// Act
		boolean isPossibleMove = CreateTarget(ChessColor.BLACK).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertTrue(isPossibleMove);
	}
	
	@ParameterizedTest
	@MethodSource("whiteIllegalMoveProvider")
	public void isPossibleMove_whiteIllegalMove_returnFalse(List<Object> arguments) {
		// Arrange
		Position startPosition = (Position) arguments.get(0);
		Position endPosition = (Position) arguments.get(1);
		
		// Act
		boolean isPossibleMove = CreateTarget(ChessColor.WHITE).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertFalse(isPossibleMove);
	}
	
	@ParameterizedTest
	@MethodSource("blackIllegalMoveProvider")
	public void isPossibleMove_blackIllegalMove_returnFalse(List<Object> arguments) {
		// Arrange
		Position startPosition = (Position) arguments.get(0);
		Position endPosition = (Position) arguments.get(1);
		
		// Act
		boolean isPossibleMove = CreateTarget(ChessColor.BLACK).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertFalse(isPossibleMove);
	}
	
	@Test
	public void isPossibleMove_whiteTwoSquaresPreviouslyMoved_returnFalse() {
		// Arrange
		Piece pawn = CreateTarget(ChessColor.WHITE);
		pawn.setMoved(true);
		
		// Act
		boolean isPossibleMove = pawn.isPossibleMove(new Position(6, 3), new Position(4, 3));
		
		// Assert
		assertFalse(isPossibleMove);
	}
	
	@Test
	public void isPossibleMove_blackTwoSquaresPreviouslyMoved_returnFalse() {
		// Arrange
		Piece pawn = CreateTarget(ChessColor.BLACK);
		pawn.setMoved(true);
		
		// Act
		boolean isPossibleMove = pawn.isPossibleMove(new Position(1, 3), new Position(3, 3));
		
		// Assert
		assertFalse(isPossibleMove);
	}
	
	@Test
	public void getMovePath_whiteLegalMoveOneSquare_returnEmptyArray() {
		// Arrange
		Position[] expectedPath = {new Position(5, 3)};
		
		// Act
		Position[] actualPath = CreateTarget(ChessColor.WHITE).getMovePath(new Position(6, 3), new Position(5, 3));
		
		// Assert
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_blackLegalMoveOneSquare_returnEmptyArray() {
		// Arrange
		Position[] expectedPath = {new Position(2, 3)};
		
		// Act
		Position[] actualPath = CreateTarget(ChessColor.BLACK).getMovePath(new Position(1, 3), new Position(2, 3));
		
		// Assert
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_whiteLegalMoveTwoSquares_returnPositionArray() {
		// Arrange
		Position[] expectedPath = {new Position(5, 3), new Position(4, 3)};
		
		// Act
		Position[] actualPath = CreateTarget(ChessColor.WHITE).getMovePath(new Position(6, 3), new Position(4, 3));
		
		// Assert
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_blackLegalMoveTwoSquares_returnPositionArray() {
		// Arrange
		Position[] expectedPath = {new Position(2, 3), new Position(3, 3)};
		
		// Act
		Position[] actualPath = CreateTarget(ChessColor.BLACK).getMovePath(new Position(1, 3), new Position(3, 3));
		
		// Assert
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@ParameterizedTest
	@MethodSource("com.seanhoapps.chessgame.pieces.TestProviders#colorProvider")
	public void getMovePath_anyColorIllegalMove_returnNull(ChessColor color) {		
		// Act
		Position[] path = CreateTarget(color).getMovePath(new Position(6, 3), new Position(6, 2));
		
		// Assert
		assertNull(path);
	}
	
	private static Set<List<Object>> whiteLegalMoveProvider() {
		// List: {startPosition, endPosition}
		return ImmutableSet.of(
					ImmutableList.of(new Position(6, 3), new Position(5, 3)),
					ImmutableList.of(new Position(6, 3), new Position(4, 3)),
					ImmutableList.of(new Position(6, -3), new Position(5, -3)),
					ImmutableList.of(new Position(6, -3), new Position(4, -3)),
					ImmutableList.of(new Position(6, 13), new Position(5, 13)),
					ImmutableList.of(new Position(6, 13), new Position(4, 13)),
					ImmutableList.of(new Position(1, 3), new Position(0, 3))
				);
	}
	
	private static Set<List<Object>> blackLegalMoveProvider() {
		// List: {startPosition, endPosition}
		return ImmutableSet.of(
					ImmutableList.of(new Position(1, 3), new Position(2, 3)),
					ImmutableList.of(new Position(1, 3), new Position(3, 3)),
					ImmutableList.of(new Position(1, -3), new Position(2, -3)),
					ImmutableList.of(new Position(1, -3), new Position(3, -3)),
					ImmutableList.of(new Position(1, 13), new Position(2, 13)),
					ImmutableList.of(new Position(1, 13), new Position(2, 13)),
					ImmutableList.of(new Position(6, 3), new Position(7, 3))
				);
	}
	
	private static Set<List<Object>> whiteIllegalMoveProvider() {
		// List: {startPosition, endPosition}
		return ImmutableSet.of(
					ImmutableList.of(new Position(6, 3), new Position(6, 2)),
					ImmutableList.of(new Position(6, 3), new Position(6, 4)),
					ImmutableList.of(new Position(6, 3), new Position(7, 3)),
					ImmutableList.of(new Position(6, 3), new Position(5, 2)),
					ImmutableList.of(new Position(6, 3), new Position(5, 4)),
					ImmutableList.of(new Position(6, 3), new Position(3, 3)),
					ImmutableList.of(new Position(1, 3), new Position(2, 3))
					
				);
	}
	
	private static Set<List<Object>> blackIllegalMoveProvider() {
		// List: {startPosition, endPosition}
		return ImmutableSet.of(
					ImmutableList.of(new Position(1, 3), new Position(1, 2)),
					ImmutableList.of(new Position(1, 3), new Position(1, 4)),
					ImmutableList.of(new Position(1, 3), new Position(0, 3)),
					ImmutableList.of(new Position(1, 3), new Position(2, 2)),
					ImmutableList.of(new Position(1, 3), new Position(2, 4)),
					ImmutableList.of(new Position(1, 3), new Position(4, 3)),
					ImmutableList.of(new Position(6, 3), new Position(5, 3))
					
				);
	}
	
	private static Piece CreateTarget(ChessColor color) {
		return PieceFactory.createPiece(PieceType.PAWN, color);
	}
	
}