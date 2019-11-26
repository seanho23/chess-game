package com.seanhoapps.chessgame.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Set;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;

public class PieceLayer extends JPanel {
	private Board board;
		
	public PieceLayer(Board board) {
		super(new GridLayout(board.getRowCount(), board.getColCount()));
		this.board = board;
		
		initPanel();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Clear pieces
		super.paintComponent(g);
		
		// Paint pieces
		int rows = board.getRowCount();
		int cols = board.getColCount();
		int boardWidth = getWidth();
		int boardHeight = getHeight();
		int squareWidth = boardWidth / cols;
		int squareHeight = boardHeight / rows;
		
		// Paint white pieces
		Set<Position> whitePositions = board.getPositionsByColor(ChessColor.WHITE);
		for (Position whitePosition : whitePositions) {
			int w = whitePosition.getCol() * squareWidth;
			int h = whitePosition.getRow() * squareWidth;
			Image image = board.getPiece(whitePosition).getImage();
			g.drawImage(image, w + ((squareWidth - image.getWidth(null)) / 2), h + ((squareHeight - image.getHeight(null)) / 2), null);
		}
		
		// Paint black pieces
		Set<Position> blackPositions = board.getPositionsByColor(ChessColor.BLACK);
		for (Position blackPosition : blackPositions) {
			int w = blackPosition.getCol() * squareWidth;
			int h = blackPosition.getRow() * squareWidth;
			Image image = board.getPiece(blackPosition).getImage();
			g.drawImage(image, w + ((squareWidth - image.getWidth(null)) / 2), h + ((squareHeight - image.getHeight(null)) / 2), null);
		}
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}