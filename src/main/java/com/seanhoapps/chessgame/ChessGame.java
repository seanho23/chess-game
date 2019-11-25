package com.seanhoapps.chessgame;

import javax.swing.SwingUtilities;

import com.seanhoapps.chessgame.gui.GameGUI;

public class ChessGame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new GameGUI(new Game());
			}
			
		});
	}
}
