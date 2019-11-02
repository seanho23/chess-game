package com.seanhoapps.chessgame;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightTest {
	private Piece knight;
	
	@BeforeEach
	public void setUp () {
		knight = new Knight(ChessColor.BLACK);
	}
	
	/*
	 * 
	 *	Tests for Knight.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveUpLeftLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 1);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpUpLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(2, 2);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpUpRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(2, 4);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpRightRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 5);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDownRightRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(5, 5);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDownDownRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 4);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDownDownLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 2);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDownLeftLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(5, 1);
		assertTrue(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_startEqualsEnd_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 3);
		assertFalse(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveUpOneSquare_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 3);
		assertFalse(knight.isPossibleMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(1, 6);
		assertFalse(knight.isPossibleMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for Knight.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveUpLeftLeft_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = knight.getMovePath(new Position(4, 3), new Position(3, 1));
		assertArrayEquals(expectedPath, actualPath);
	}
}