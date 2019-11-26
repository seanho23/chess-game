package com.seanhoapps.chessgame;

import javax.swing.SwingUtilities;

import com.seanhoapps.chessgame.gui.ChessGameGUI;

public class ChessGame {
	public static void main(String[] args) {
		BoardManager boardManager = new BoardManager();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(boardManager);
			}
		});
	}
	
	private static void createAndShowGUI(BoardManager boardManager) {
		new ChessGameGUI(boardManager);
	}
}
