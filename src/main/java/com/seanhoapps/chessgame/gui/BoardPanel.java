package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;

public class BoardPanel extends JPanel {
	// Constants
	private static final int BOARD_WIDTH = 800;
	private static final int BOARD_HEIGHT = 800;
	private static final Dimension BOARD_SIZE = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
	
	private Board board;
	
	private JLayeredPane layers;
	private JPanel pieceLayer;
	private JPanel highlightLayer;
	private JPanel boardLayer;
	
	public BoardPanel(Board board, GameGUI gameGUI) {
		super(new BorderLayout());
		this.board = board;
		
		initPanel(gameGUI);
	}
	
	private void initPanel(GameGUI gameGUI) {
		initBoardLayers();
		add(layers);
		
		addMouseListener(new BoardListener(gameGUI));
	}
	
	private void initBoardLayers() {
		layers = new JLayeredPane();
		layers.setPreferredSize(BOARD_SIZE);
		
		// Bottom layer
		boardLayer = new BoardLayerPanel(board);
		boardLayer.setSize(BOARD_SIZE);
		layers.add(boardLayer, JLayeredPane.DEFAULT_LAYER, -1);
		
		highlightLayer = new HighlightLayerPanel(board);
		highlightLayer.setSize(BOARD_SIZE);
		layers.add(highlightLayer, JLayeredPane.DEFAULT_LAYER, 0);
		
		// Top layer
		pieceLayer = new PieceLayerPanel(board);
		pieceLayer.setSize(BOARD_SIZE);
		layers.add(pieceLayer, JLayeredPane.DRAG_LAYER, 0);
  }
}