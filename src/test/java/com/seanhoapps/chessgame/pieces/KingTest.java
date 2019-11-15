package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.King;
import com.seanhoapps.chessgame.pieces.Piece;

public class KingTest {
	private Piece king;
	
	@BeforeEach
	public void setUp () {
		king = new King(ChessColor.WHITE);
	}
	
	/*
	 * 
	 *	Tests for King.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveUp_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 3);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDown_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(5, 3);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 2);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 4);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalUpLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 2);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalUpRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 4);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(5, 2);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(5, 4);
		assertTrue(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveMultipleSquares_returnFalse() {
		Position startPos = new Position(7, 4);
		Position endPos = new Position(5, 4);
		assertFalse(king.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(2, 7);
		assertFalse(king.isPossibleMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for King.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = king.getMovePath(new Position(4, 3), new Position(3, 3));
		assertArrayEquals(expectedPath, actualPath);
	}
}