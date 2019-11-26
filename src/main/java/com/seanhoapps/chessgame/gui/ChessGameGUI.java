package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.seanhoapps.chessgame.BoardManager;

public class ChessGameGUI {
	private BoardManager boardManager;
	
	private JFrame frame;
	
	// Controllers
	private BoardController boardController;
	
	public ChessGameGUI(BoardManager boardManager) {
		this.boardManager = boardManager;
		
		initGUI();
	}
	
	public void updateTitle() {
		frame.setTitle("Chess Game (" + boardManager.getTurnColor() + " TURN)");
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
		
		boardController = new BoardController(this, boardManager);
		frame.add(boardController.getGUI(), BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}