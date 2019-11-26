package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Square;

public class BoardLayerPanel extends JPanel {
	// Constants
	private static final Color LIGHT_COLOR = new Color(178, 203, 174);
	private static final Color DARK_COLOR = new Color(106, 146, 101);
	
	private Board board;
	
	public BoardLayerPanel(Board board) {
		this.board = board;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Clear squares
		super.paintComponent(g);
		
		// Paint squares
		int rows = board.getRowCount();
		int cols = board.getColCount();
		int boardWidth = getWidth();
		int boardHeight = getHeight();
		int squareWidth = boardWidth / cols;
		int squareHeight = boardHeight / rows;
		for (int row = 0, h = 0; row < rows; row++, h += squareHeight) {
			for (int col = 0, w = 0; col < cols; col++, w += squareWidth) {
				Square square = board.getSquare(row, col);
				Color color = square.isWhite() ? LIGHT_COLOR : DARK_COLOR;
				g.setColor(color);
				g.fillRect(w, h, squareWidth, squareHeight);
			}
		}
	}
}
