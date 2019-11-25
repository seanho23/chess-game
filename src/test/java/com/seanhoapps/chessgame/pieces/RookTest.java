package com.seanhoapps.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.Piece;
import com.seanhoapps.chessgame.pieces.Rook;

public class RookTest {
	private Piece rook;
	
	@BeforeEach
	public void setUp () {
		rook = new Rook(ChessColor.WHITE);
	}
	
	/*
	 * 
	 *	Tests for Rook.isPossibleMove(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void isPossibleMove_moveUp_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(0, 3);
		assertTrue(rook.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDown_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(6, 3);
		assertTrue(rook.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveLeft_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 2);
		assertTrue(rook.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveRight_returnTrue() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(4, 5);
		assertTrue(rook.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_moveDiagonal_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(1, 6);
		assertFalse(rook.canMove(startPos, endPos));
	}
	
	@Test
	public void isPossibleMove_illegalMove_returnFalse() {
		Position startPos = new Position(4, 3);
		Position endPos = new Position(3, 6);
		assertFalse(rook.canMove(startPos, endPos));
	}
	
	/*
	 * 
	 *	Tests for Rook.getMovePath(Position startPos, Position endPos)
	 * 
	*/
	
	@Test
	public void getMovePath_moveOneSquare_returnEmptyPosArray() {
		Position[] expectedPath = new Position[0];
		Position[] actualPath = rook.getMovePath(new Position(4, 3), new Position(3, 3));
		assertArrayEquals(expectedPath, actualPath);
	}
	
	@Test
	public void getMovePath_moveMultipleSquares_returnPosArray() {
		Position[] expectedPath = new Position[] {new Position(4, 4), new Position(4, 5), new Position(4, 6)};
		Position[] actualPath = rook.getMovePath(new Position(4, 3), new Position(4, 7));
		assertArrayEquals(expectedPath, actualPath);
	}
}