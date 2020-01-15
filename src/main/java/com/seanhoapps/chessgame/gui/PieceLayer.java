package com.seanhoapps.chessgame.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.ChessColor;

public class PieceLayer extends JPanel {
	private Board board;
		
	public PieceLayer(Board board) {
		super(new GridLayout(board.getRowCount(), board.getColCount()));
		this.board = board;
		
		initPanel();
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		int squareWidth = getWidth() / board.getColCount();
		int squareHeight = getHeight() / board.getRowCount();
		
		// Paint white pieces
		board.getPositionToPiece(ChessColor.WHITE).forEach((whitePosition, whitePiece) -> {
			int w = whitePosition.getCol() * squareWidth;
			int h = whitePosition.getRow() * squareWidth;
			Image pieceImage = whitePiece.getImage();
			g2d.drawImage(pieceImage, w + ((squareWidth - pieceImage.getWidth(null)) / 2), h + ((squareHeight - pieceImage.getHeight(null)) / 2), null);
		});
		
		// Paint black pieces
		board.getPositionToPiece(ChessColor.BLACK).forEach((blackPosition, blackPiece) -> {
			int w = blackPosition.getCol() * squareWidth;
			int h = blackPosition.getRow() * squareWidth;
			Image pieceImage = blackPiece.getImage();
			g2d.drawImage(pieceImage, w + ((squareWidth - pieceImage.getWidth(null)) / 2), h + ((squareHeight - pieceImage.getHeight(null)) / 2), null);
		});
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}
