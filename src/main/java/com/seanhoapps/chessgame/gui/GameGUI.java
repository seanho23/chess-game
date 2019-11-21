package com.seanhoapps.chessgame.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Game;

public class GameGUI {
	// Constants
	private static final int MIN_WIDTH = 800;
	private static final int MIN_HEIGHT = 800;
	
	private Game game;
	
	private JFrame gameWindow;
	private JPanel boardPanel;
				
	public GameGUI(Game game) {
		this.game = game;
		initGameGUI();
	}
	
	private void initGameGUI() {
		initGameWindow();
	}
	
	private void initGameWindow() {
		gameWindow = new JFrame("Chess Game");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		gameWindow.setResizable(false);
		gameWindow.setLocationRelativeTo(null);
		
		boardPanel = new BoardPanel(game);
		gameWindow.add(boardPanel);
		
		gameWindow.pack();
		gameWindow.setVisible(true);
	}
}