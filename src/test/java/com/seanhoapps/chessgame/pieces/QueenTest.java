package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.Piece;
import com.seanhoapps.chessgame.pieces.Queen;

public class QueenTest {
	private Piece queen;
	
	@BeforeEach
	public void setUp () {
		queen = new Queen(ChessColor.WHITE);
	}
	
	/*
	 * 
	 *	Tests for Queen.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveUp_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(0, 3);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDown_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 3);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 2);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 5);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalUpLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(1, 0);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalUpRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 4);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 1);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(7, 6);
		assertTrue(queen.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 6);
		assertFalse(queen.canMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for Queen.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = queen.getMovePath(new Position(4, 3), new Position(3, 3));
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_moveMultipleSquares_returnPosArray() {
		Position[] expectedPath = new Position[] {new Position(4, 4), new Position(4, 5), new Position(4, 6)};
		Position[] actualPath = queen.getMovePath(new Position(4, 3), new Position(4, 7));
		assertArrayEquals(expectedPath, actualPath);
	}
}