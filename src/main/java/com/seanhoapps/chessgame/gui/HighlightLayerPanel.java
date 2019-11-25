package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Square;

public class HighlightLayerPanel extends JPanel {
	//Constants
	private static final Color WARNING_HIGHLIGHT_COLOR = new Color(255, 255, 153, 175);
	private static final Color DANGER_HIGHLIGHT_COLOR = new Color(255, 89, 89);
		
	private Board board;
	
	public HighlightLayerPanel(Board board) {
		this.board = board;
		
		initPanel();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int rows = board.getRowCount();
		int cols = board.getColCount();
		int boardWidth = getWidth();
		int boardHeight = getHeight();
		int squareWidth = boardWidth / cols;
		int squareHeight = boardHeight / rows;
		for (int row = 0, h = 0; row < rows; row++, h += squareHeight) {
			for (int col = 0, w = 0; col < cols; col++, w += squareWidth) {
				Square square = board.getSquare(row, col);
				if (square.isHighlighted()) {
					g.setColor(square.getHighlightColor());
					g.fillRect(w, h, squareWidth, squareHeight);
					square.setHighlightColor(null);
				}
			}
		}
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}
