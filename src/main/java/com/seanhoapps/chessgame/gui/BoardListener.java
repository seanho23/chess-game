package com.seanhoapps.chessgame.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardListener implements MouseListener {
	private GameGUI gameGUI;
	private MouseEvent startEvent = null;
			
	public BoardListener(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
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
		
		gameGUI.onMove(startEvent, e);
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
