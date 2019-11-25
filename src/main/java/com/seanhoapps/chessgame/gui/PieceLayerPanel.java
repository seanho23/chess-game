package com.seanhoapps.chessgame.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Square;

public class PieceLayerPanel extends JPanel {
	private Board board;	
		
	public PieceLayerPanel(Board board) {
		super(new GridLayout(board.getRowCount(), board.getColCount()));
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
				if (square.isOccupied()) {
					Image image = square.getPiece().getImage();
					g.drawImage(image, w + ((squareWidth - image.getWidth(null)) / 2), h + ((squareHeight - image.getHeight(null)) / 2), null);
				}
			}
		}
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}
