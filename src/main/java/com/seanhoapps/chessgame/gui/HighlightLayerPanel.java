package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.Square;

public class HighlightLayerPanel extends JPanel {
	//Constants
	private static final Color WARNING_COLOR = new Color(255, 255, 153, 175);
	private static final Color DANGER_COLOR = new Color(255, 153, 153);
		
	private Board board;
	
	public HighlightLayerPanel(Board board) {
		this.board = board;
		
		initPanel();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Clear highlights
		super.paintComponent(g);
		
		// Paint highlights
		int rows = board.getRowCount();
		int cols = board.getColCount();
		int boardWidth = getWidth();
		int boardHeight = getHeight();
		int squareWidth = boardWidth / cols;
		int squareHeight = boardHeight / rows;
		Map<Position, Square> highlightedSquares = board.getHighlightedSquares();
		highlightedSquares.forEach((position, square) -> {
			int w = position.getCol() * squareWidth;
			int h = position.getRow() * squareWidth;
			Color highlightColor = square.getHighlight().isWarning() ? WARNING_COLOR : DANGER_COLOR;
			g.setColor(highlightColor);
			g.fillRect(w, h, squareWidth, squareHeight);
		});
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}
