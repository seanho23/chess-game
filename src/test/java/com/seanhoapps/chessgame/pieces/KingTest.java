package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.PieceFactory;
import com.seanhoapps.chessgame.Position;

public class KingTest {
	
	@ParameterizedTest
	@MethodSource("legalMoveProvider")
	public void isPossibleMove_anyColorLegalMove_returnTrue(List<Object> arguments) {
		// Arrange
		ChessColor color = (ChessColor) arguments.get(0);
		Position startPosition = (Position) arguments.get(1);
		Position endPosition = (Position) arguments.get(2);
		
		// Act
		boolean isPossibleMove = CreateTarget(color).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertTrue(isPossibleMove);
	}
	
	@ParameterizedTest
	@MethodSource("illegalMoveProvider")
	public void isPossibleMove_anyColorIllegalMove_returnFalse(List<Object> arguments) {
		// Arrange
		ChessColor color = (ChessColor) arguments.get(0);
		Position startPosition = (Position) arguments.get(1);
		Position endPosition = (Position) arguments.get(2);
		
		// Act
		boolean isPossibleMove = CreateTarget(color).isPossibleMove(startPosition, endPosition);
		
		// Assert
		assertFalse(isPossibleMove);
	}
	
	@ParameterizedTest
	@MethodSource("com.seanhoapps.chessgame.pieces.TestProviders#colorProvider")
	public void getMovePath_anyColorLegalMove_returnEmptyArray(ChessColor color) {
		// Arrange
		Position[] expectedPath = new Position[0];
		
		// Act
		Position[] actualPath = CreateTarget(color).getMovePath(new Position(3, 3), new Position(4, 4));
		
		// Assert
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@ParameterizedTest
	@MethodSource("com.seanhoapps.chessgame.pieces.TestProviders#colorProvider")
	public void getMovePath_anyColorIllegalMove_returnNull(ChessColor color) {		
		// Act
		Position[] path = CreateTarget(color).getMovePath(new Position(3, 3), new Position(1, 1));
		
		// Assert
		assertNull(path);
	}

	private static Set<List<Object>> legalMoveProvider() {
		Position startPosition = new Position(3, 3);
		Set<Position> endPositions = ImmutableSet.of(
					new Position(2, 2),
					new Position(2, 3),
					new Position(2, 4),
					new Position(3, 2),
					new Position(3, 4),
					new Position(4, 2),
					new Position(4, 3),
					new Position(4, 4)
				);
		return Sets.cartesianProduct(TestProviders.colorProvider(), ImmutableSet.of(startPosition), endPositions);
	}
	
	private static Set<List<Object>> illegalMoveProvider() {
		Position startPosition = new Position(3, 3);
		Set<Position> endPositions = ImmutableSet.of(
					new Position(-1, -1),
					new Position(0, 3),
					new Position(0, 6),
					new Position(3, 0),
					new Position(3, 7),
					new Position(6, 0),
					new Position(6, 3),
					new Position(10, 10),
					new Position(4, 1)
				);
		return Sets.cartesianProduct(TestProviders.colorProvider(), ImmutableSet.of(startPosition), endPositions);
	}
	
	private static Piece CreateTarget(ChessColor color) {
		return PieceFactory.createPiece(PieceType.KING, color);
	}
	
}