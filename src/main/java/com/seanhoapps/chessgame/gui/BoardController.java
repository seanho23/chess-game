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
import com.seanhoapps.chessgame.BoardManager;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.Position;
import com.seanhoapps.chessgame.pieces.Piece;

public class BoardController {
	// Constants
	private static final int SQUARE_WIDTH = 100;
	private static final int SQUARE_HEIGHT = 100;
	private static final String imageFolder = "/images/pieces";
	
	private ChessGameGUI gameGUI;
	private BoardManager boardManager;
	private Board board;
	private Map<String, Image> pieceImages = new HashMap<String, Image>();
	
	private JPanel boardPanel;
	private JLayeredPane layers;
	private JPanel pieceLayer;
	private JPanel highlightLayer;
	private JPanel boardLayer;
	
	public BoardController(ChessGameGUI gameGUI, BoardManager boardManager) {
		this.gameGUI = gameGUI;
		this.boardManager = boardManager;
		board = boardManager.getBoard();
		
		initBoardPanel();
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
			boardManager.move(startPosition, endPosition);
			
			board.setHighlight(startPosition, HighlightType.WARNING);
			board.setHighlight(endPosition, HighlightType.WARNING);
			
			boardManager.saveBoardState();
		}
		catch (Exception e) {
			// Illegal move
			board.setHighlight(startPosition, HighlightType.WARNING);
			board.setHighlight(endPosition, HighlightType.DANGER);
		}
		finally {
			boardPanel.repaint();
		}
				
		gameGUI.updateTitle();
	}
	
	public JPanel getGUI() {
		return boardPanel;
	}
	
	private Position xyToPosition(int x, int y) {
		int squareWidth = boardPanel.getWidth() / board.getRowCount();
		int squareHeight = boardPanel.getHeight() / board.getColCount();
		return new Position(y / squareHeight, x / squareWidth);
	}
	
	private void initBoardPanel() {
		initPieceImages();
		setPieceImages();
		
		boardPanel = new JPanel(new BorderLayout());
		initBoardLayers();
		boardPanel.add(layers);
		boardPanel.addMouseListener(new BoardListener(this));
	}
	
	private void initBoardLayers() {
		Dimension boardSize = new Dimension(SQUARE_WIDTH * board.getColCount(), SQUARE_HEIGHT * board.getRowCount());
		
		layers = new JLayeredPane();
		layers.setPreferredSize(boardSize);
		
		// Bottom layer
		boardLayer = new BoardLayer(board);
		boardLayer.setSize(boardSize);
		layers.add(boardLayer, JLayeredPane.DEFAULT_LAYER, -1);
		
		highlightLayer = new HighlightLayer(board);
		highlightLayer.setSize(boardSize);
		layers.add(highlightLayer, JLayeredPane.DEFAULT_LAYER, 0);
		
		// Top layer
		pieceLayer = new PieceLayer(board);
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