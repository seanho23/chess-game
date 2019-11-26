package com.seanhoapps.chessgame.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Game;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.Piece;

public class BoardPanel extends JPanel {
	// Constants
	private static final int SQUARE_WIDTH = 100;
	private static final int SQUARE_HEIGHT = 100;
	private static final String imageFolder = "/images/pieces";
	
	private GameGUI gameGUI;
	private Game game;
	private Board board;
	private Map<String, Image> pieceImages = new HashMap<String, Image>();
	
	private JLayeredPane layers;
	private JPanel pieceLayer;
	private JPanel highlightLayer;
	private JPanel boardLayer;
	
	public BoardPanel(GameGUI gameGUI, Game game) {
		super(new BorderLayout());
		this.gameGUI = gameGUI;
		this.game = game;
		board = game.getBoard();
		
		initPanel();
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
		
		board.clearAllHighlights();
		
		try {
			game.move(startPosition, endPosition);
			
			board.setHighlight(startPosition, HighlightType.WARNING);
			board.setHighlight(endPosition, HighlightType.WARNING);
			
			game.saveBoardState();
		}
		catch (Exception e) {
			// Illegal move
			board.setHighlight(startPosition, HighlightType.WARNING);
			board.setHighlight(endPosition, HighlightType.DANGER);
		}
		finally {
			repaint();
		}
				
		gameGUI.updateTitle();
	}
	
	private Position xyToPosition(int x, int y) {
		int squareWidth = getWidth() / game.getBoard().getRowCount();
		int squareHeight = getHeight() / game.getBoard().getColCount();
		return new Position(y / squareHeight, x / squareWidth);
	}
	
	private void initPanel() {
		initPieceImages();
		setPieceImages();
		
		initBoardLayers();
		add(layers);
		
		addMouseListener(new BoardListener(this));
	}
	
	private void initBoardLayers() {
		Dimension boardSize = new Dimension(SQUARE_WIDTH * board.getColCount(), SQUARE_HEIGHT * board.getRowCount());
		
		layers = new JLayeredPane();
		layers.setPreferredSize(boardSize);
		
		// Bottom layer
		boardLayer = new BoardLayerPanel(board);
		boardLayer.setSize(boardSize);
		layers.add(boardLayer, JLayeredPane.DEFAULT_LAYER, -1);
		
		highlightLayer = new HighlightLayerPanel(board);
		highlightLayer.setSize(boardSize);
		layers.add(highlightLayer, JLayeredPane.DEFAULT_LAYER, 0);
		
		// Top layer
		pieceLayer = new PieceLayerPanel(board);
		pieceLayer.setSize(boardSize);
		layers.add(pieceLayer, JLayeredPane.DRAG_LAYER, 0);
	}
	
	private void setPieceImages() {
		// Set images for white pieces
		Set<Position> whitePositions = board.getPositionsByColor(ChessColor.WHITE);
		for (Position whitePosition : whitePositions) {
			Piece piece = board.getPiece(whitePosition);
			piece.setImage(pieceImages.get(("" + piece.getColor() + piece.getType()).toLowerCase()));
		}
		
		// Set images for black pieces
		Set<Position> blackPositions = board.getPositionsByColor(ChessColor.BLACK);
		for (Position blackPosition : blackPositions) {
			Piece piece = board.getPiece(blackPosition);
			piece.setImage(pieceImages.get(("" + piece.getColor() + piece.getType()).toLowerCase()));
		}
	}
	
	private void initPieceImages() {
		// Obtain list of image files
		File[] files = new File[0];
		try {
			File directory = new File(getClass().getResource(imageFolder).toURI());
			// Filter out non-images
			files = directory.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".png");
			});
		}
		catch (URISyntaxException e) {
			System.err.println("Unable to parse URI: " + getClass().getResource(imageFolder));
		}
		
		// Read and cache images
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
}