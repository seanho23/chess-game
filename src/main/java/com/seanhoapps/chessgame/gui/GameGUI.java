package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.Game;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.Square;
import com.seanhoapps.chessgame.pieces.Piece;

public class GameGUI {
	//Constants
	private static final String imageFolder = "/images/pieces";
	private static final Color WARNING_HIGHLIGHT_COLOR = new Color(255, 255, 153, 175);
	private static final Color DANGER_HIGHLIGHT_COLOR = new Color(255, 89, 89);
		
	private Game game;
	private Board board;
	private Map<String, Image> pieceImages = new HashMap<String, Image>();
	
	private JFrame frame;
	private JPanel boardPanel;
	
	public GameGUI(Game game) {
		this.game = game;
		board = game.getBoard();
		
		initGUI();
	}
	
	public void onMove(MouseEvent startEvent, MouseEvent endEvent) {
		Position startPosition = xyToPosition(startEvent.getX(), startEvent.getY());
		Position endPosition = xyToPosition(endEvent.getX(), endEvent.getY());
		
		// Cancel move
		if (endPosition.equals(startPosition)) {
			return;
		}
		
		System.out.print("(" + startPosition.getRow() + ", " + startPosition.getCol() + ")");
		System.out.println(" --> (" + endPosition.getRow() + ", " + endPosition.getCol() + ")");
		
		try {
			game.move(startPosition, endPosition);
			highlightSquare(startPosition);
			highlightSquare(endPosition);
			
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			frame.repaint();
		}
		
		updateTitle();
	}
	
	private void highlightSquare(Position position) {
		board.getSquare(position).setHighlightColor(WARNING_HIGHLIGHT_COLOR);
	}
	
	private void updateTitle() {
		frame.setTitle("Chess Game (" + game.getTurnColor() + " TURN)");
	}
	
	private void initGUI() {
		initPieceImages();
		setPieceImages();
		
		initFrame();
		updateTitle();
	}
	
	private void initFrame() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		boardPanel = new BoardPanel(board, this);
		frame.add(boardPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void setPieceImages() {
		for (int row = 0, rows = board.getRowCount(); row < rows; row++) {
			for (int col = 0, cols = board.getColCount(); col < cols; col++) {
				Square square = board.getSquare(row, col);
				if (square.isOccupied()) {
					Piece piece = square.getPiece();
					piece.setImage(pieceImages.get(("" + piece.getColor() + piece.getType()).toLowerCase()));
				}
			}
		}
	}
	
	private void initPieceImages() {
		File[] files = new File[0];
		try {
			File directory = new File(getClass().getResource(imageFolder).toURI());
			// Filter out non-images
			files = directory.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".png");
			});
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Cache images
		for (File file : files) {
			String filename = file.getName();
			filename = filename.substring(0, filename.lastIndexOf("."));
			try {
				pieceImages.put(filename, ImageIO.read(file));
			} catch (IOException e) {
				System.err.println("Unable to read: " + file.getName());
			}
		}
	}
	
	private Position xyToPosition(int x, int y) {
		int squareWidth = boardPanel.getWidth() / board.getRowCount();
		int squareHeight = boardPanel.getHeight() / board.getColCount();
		return new Position(y / squareHeight, x / squareWidth);
	}
}