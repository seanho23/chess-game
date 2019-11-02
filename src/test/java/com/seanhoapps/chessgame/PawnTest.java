package com.seanhoapps.chessgame;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnTest {
	private Piece whitePawn;
	private Piece blackPawn;
	
	@BeforeEach
	public void setUp () {
		whitePawn = new Pawn(ChessColor.WHITE);
		blackPawn = new Pawn(ChessColor.BLACK);
	}
	
	/*
	 * 
	 *	Tests for Pawn.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveUpOne_returnTrue() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(5, 3);
		assertTrue(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpTwoBeforeMoving_returnTrue() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(4, 3);
		assertTrue(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpLeft_returnTrue() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(5, 2);
		assertTrue(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpRight_returnTrue() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(5, 4);
		assertTrue(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_startEqualsEnd_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(6, 3);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpTwoAfterMoving_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(4, 3);
		whitePawn.hasMoved(true);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpThree_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(3, 3);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveLeft_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(6, 2);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveRight_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(6, 4);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_whiteMoveBackward_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(7, 3);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_blackMoveBackward_returnFalse() {
		Position startPos = new Position(1, 4);
		Position endPos = new Position(0, 4);
		assertFalse(blackPawn.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(6, 3);
		Position endPos = new Position(1, 6);
		assertFalse(whitePawn.isPossibleMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for Pawn.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = whitePawn.getMovePath(new Position(6, 3), new Position(5, 3));
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_moveDiagonallyOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = whitePawn.getMovePath(new Position(6, 3), new Position(5, 2));
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_moveTwoSquares_returnPosArray() {
		Position[] expectedPath = new Position[] {new Position(5, 3)};
		Position[] actualPath = whitePawn.getMovePath(new Position(6, 3), new Position(4, 3));
		assertArrayEquals(expectedPath, actualPath);
	}
}