package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Game;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.Square;
import com.seanhoapps.chessgame.pieces.Piece;

public class BoardPanel extends JPanel {
	// Constants
	private static final Color lightColor = new Color(245, 245, 220);
	private static final Color darkColor = new Color(139, 69, 19);
	
	private Game game;
	private Board board;
	
	private JLabel[][] squareLabels;
	
	private static final String iconFolder = "/images/pieces";
	private Map<String, ImageIcon> pieceIcons = new HashMap<String, ImageIcon>();
	
	public BoardPanel(Game game) {
		this.game = game;
		board = game.getBoard();
		
		initBoardPanel();
	}
	
	public void onMouseDrag(MouseEvent startEvent, MouseEvent endEvent) {
		Position startPosition = xyToPosition(startEvent.getX(), startEvent.getY());
		Position endPosition = xyToPosition(endEvent.getX(), endEvent.getY());
		
		// Cancel move
		if (endPosition.equals(startPosition)) {
			System.out.println("Cancelled move");
			return;
		}
		
		System.out.print("(" + startPosition.getRow() + ", " + startPosition.getCol() + ")");
		System.out.println(" --> (" + endPosition.getRow() + ", " + endPosition.getCol() + ")");
		
		game.move(startPosition, endPosition);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int row = 0, rows = board.getRowCount(); row < rows; row++) {
			for (int col = 0, cols = board.getColCount(); col < cols; col++) {
				Square square = board.getSquare(row, col);
				ImageIcon icon = null;
				
				if (square.isOccupied()) {
					icon = square.getPiece().getIcon();
				}
				
				squareLabels[row][col].setIcon(icon);
			}
		}
	}
	
	private void initBoardPanel() {
		setLayout(new GridLayout(board.getRowCount(), board.getColCount()));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new BoardListener(this));
		
		initPieceIcons();
		initSquareLabels();
	}
	
	private void initSquareLabels() {
		int rows = board.getRowCount();
		int cols = board.getColCount();
		squareLabels = new JLabel[rows][cols];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Square square = board.getSquare(row, col);
				Color color = square.isWhite() ? lightColor : darkColor;
				ImageIcon icon = null;
				
				if (square.isOccupied()) {
					Piece piece = square.getPiece();
					icon = pieceIcons.get(("" + piece.getColor() + piece.getType()).toLowerCase());
					piece.setIcon(icon);
				}
				
				JLabel squareLabel = new JLabel(icon);
				squareLabel.setBackground(color);
				squareLabel.setOpaque(true);
				
				add(squareLabel);
				squareLabels[row][col] = squareLabel;
			}
		}
	}
	
	private void initPieceIcons() {
		File[] files = new File[0];
		
		try {
			File directory = new File(getClass().getResource(iconFolder).toURI());
			
			// Filter non-images
			files = directory.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".png");
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Cache images
		for (File file : files) {
			String filename = file.getName();
			filename = filename.substring(0, filename.lastIndexOf("."));
			pieceIcons.put(filename, new ImageIcon(file.getAbsolutePath()));
		}
	}
	
	private Position xyToPosition(int x, int y) {
		int squareWidth = getWidth() / board.getRowCount();
		int squareHeight = getHeight() / board.getColCount();
		return new Position(y / squareHeight, x / squareWidth);
	}
}
