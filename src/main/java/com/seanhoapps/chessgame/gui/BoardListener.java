package com.seanhoapps.chessgame.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardListener implements MouseListener {
	private BoardController boardController;
	private MouseEvent startEvent = null;
			
	public BoardListener(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startEvent = e;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (startEvent == null) {
			return;
		}
		
		boardController.onMove(startEvent, e);
		startEvent = null;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		startEvent = null;
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
