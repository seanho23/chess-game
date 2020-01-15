package com.seanhoapps.chessgame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.seanhoapps.chessgame.gui.BoardController;
import com.seanhoapps.chessgame.gui.BoardPanel;
import com.seanhoapps.chessgame.gui.GameController;

public class ChessGame {
	public static void main(String[] args) {
		GameManager gameManager = new GameManager();
		gameManager.start();
		
		// Create and display GUI on EDT
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				createAndShowGUI(gameManager);
			}
			
		});
		
		// Continue to load resources on initial thread
		loadDialogResources();
	}
	
	private static void createAndShowGUI(GameManager gameManager) {
		// Create window GUI
		JFrame windowView = new JFrame();
		windowView.setLayout(new BorderLayout());
		windowView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowView.setResizable(false);
		new GameController(gameManager, windowView);
		
		// Create board GUI
		BoardPanel boardView = new BoardPanel(gameManager.getBoard());
		windowView.add(boardView, BorderLayout.CENTER);
		new BoardController(gameManager, boardView);
		
		// Display game GUI
		windowView.pack();
		windowView.setLocationRelativeTo(null);
		windowView.setVisible(true);
	}
	
	/*
	 * Loads dialog resources by creating and disposing dummy dialog
	 * This method is called once during start-up to load resources and significantly reduce instantiation times of subsequent dialog creations
	 */
	private static void loadDialogResources() {
		(new JOptionPane()).createDialog(null).dispose();
	}
}