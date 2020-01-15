package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;

public class BoardPanel extends JPanel {
	// Constants
	private static final int SQUARE_WIDTH = 100;
	private static final int SQUARE_HEIGHT = 100;
		
	private JLayeredPane layers;
	private CheckerboardLayer boardLayer;
	private HighlightLayer highlightLayer;
	private PieceLayer pieceLayer;
	
	public BoardPanel(Board board) {
		initBoardPanel(board);
	}
	
	public void updateBoard(Board board) {
		highlightLayer.setBoard(board);
		pieceLayer.setBoard(board);
	}
	
	private void initBoardPanel(Board board) {
		setLayout(new BorderLayout());
		initBoardLayers(board);
		add(layers, BorderLayout.CENTER);
	}
	
	private void initBoardLayers(Board board) {
		Dimension boardSize = new Dimension(SQUARE_WIDTH * board.getColCount(), SQUARE_HEIGHT * board.getRowCount());
		
		layers = new JLayeredPane();
		layers.setPreferredSize(boardSize);
		
		// Bottom layer
		boardLayer = new CheckerboardLayer(board.getRowCount(), board.getColCount());
		boardLayer.setSize(boardSize);
		layers.add(boardLayer, JLayeredPane.DEFAULT_LAYER, -1);
		
		highlightLayer = new HighlightLayer(board);
		highlightLayer.setSize(boardSize);
		layers.add(highlightLayer, JLayeredPane.DEFAULT_LAYER, 0);
		
		// Top layer
		pieceLayer = new PieceLayer(board);
		pieceLayer.setSize(boardSize);
		layers.add(pieceLayer, JLayeredPane.DRAG_LAYER, 0);
	}
}
