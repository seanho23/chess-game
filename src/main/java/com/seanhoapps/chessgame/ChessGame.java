package com.seanhoapps.chessgame;

import javax.swing.SwingUtilities;

import com.seanhoapps.chessgame.gui.GameGUI;

public class ChessGame {
	public static void main(String[] args) {
		Game game = new Game();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI(game);
			}
		});
	}
	
	private static void createAndShowGUI(Game game) {
		new GameGUI(game);
	}
}
