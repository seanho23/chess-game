package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Game;

public class GameGUI {
	private Game game;
	
	private JFrame frame;
	private JPanel boardPanel;
	
	public GameGUI(Game game) {
		this.game = game;
		
		initGUI();
	}
	
	public void updateTitle() {
		frame.setTitle("Chess Game (" + game.getTurnColor() + " TURN)");
	}
	
	private void initGUI() {		
		initFrame();
		updateTitle();
	}
	
	private void initFrame() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		boardPanel = new BoardPanel(this, game);
		frame.add(boardPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}