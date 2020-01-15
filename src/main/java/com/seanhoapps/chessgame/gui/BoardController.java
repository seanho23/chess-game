package com.seanhoapps.chessgame.gui;

import com.seanhoapps.chessgame.Board;
import com.seanhoapps.chessgame.GameManager;
import com.seanhoapps.chessgame.GameEvent;
import com.seanhoapps.chessgame.Position;

public class BoardController implements Observer {
	private GameManager gameManager;
	
	private BoardPanel boardView;
		
	public BoardController(GameManager gameManager, BoardPanel boardView) {
		this.gameManager = gameManager;
		this.boardView = boardView;
		
		initBoardController();
	}
	
	public void onMove(Position startPosition, Position endPosition) {
		// Cancel move
		if (endPosition.equals(startPosition)) {
			return;
		}
		
		// System.out.print("(" + startPosition.getRow() + ", " + startPosition.getCol() + ")");
		// System.out.println(" --> (" + endPosition.getRow() + ", " + endPosition.getCol() + ")");
		
		try {
			gameManager.move(startPosition, endPosition);
		}
		catch (Exception e) {
			// Silently ignore
		}
		
		boardView.repaint();
	}
	
	public Position xyToPosition(int x, int y) {
		Board board = gameManager.getBoard();
		int squareWidth = boardView.getWidth() / board.getRowCount();
		int squareHeight = boardView.getHeight() / board.getColCount();
		return new Position(y / squareHeight, x / squareWidth);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		switch (event) {
			case NEW_GAME_STARTED:
				onNewGame();
				break;
			default:
				// Do nothing
		}
		
		boardView.repaint();
	}
	
	private void onNewGame() {
		boardView.updateBoard(gameManager.getBoard());
	}
	
	private void initBoardController() {
		gameManager.addObserver(this);
		boardView.addMouseListener(new BoardListener(this));
	}
}