package com.seanhoapps.chessgame.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.seanhoapps.chessgame.Position;

public class BoardListener implements MouseListener {
	private BoardController boardController;
	private MouseEvent startMouseEvent = null;
			
	public BoardListener(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startMouseEvent = e;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (startMouseEvent == null) {
			return;
		}
		
		Position startPosition = boardController.xyToPosition(startMouseEvent.getX(), startMouseEvent.getY());
		Position endPosition = boardController.xyToPosition(e.getX(), e.getY());
		boardController.onMove(startPosition, endPosition);
		startMouseEvent = null;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		startMouseEvent = null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Not used
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used
	}
}
