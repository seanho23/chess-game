package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.Bishop;
import com.seanhoapps.chessgame.pieces.Piece;

public class BishopTest {
	private Piece bishop;
	
	@BeforeEach
	public void setUp () {
		bishop = new Bishop(ChessColor.WHITE);
	}
	
	/*
	 * 
	 *	Tests for Bishop.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveDiagonalUpLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(1, 0);
		assertTrue(bishop.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalUpRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 4);
		assertTrue(bishop.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 1);
		assertTrue(bishop.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonalDownRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(7, 6);
		assertTrue(bishop.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUp_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(1, 3);
		assertFalse(bishop.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 6);
		assertFalse(bishop.canMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for Bishop.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = bishop.getMovePath(new Position(4, 3), new Position(3, 4));
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_moveMultipleSquares_returnPosArray() {
		Position[] expectedPath = new Position[] {new Position(2, 2), new Position(3, 3), new Position(4, 4), new Position(5, 5)};
		Position[] actualPath = bishop.getMovePath(new Position(1, 1), new Position(6, 6));
		assertArrayEquals(expectedPath, actualPath);
	}
}