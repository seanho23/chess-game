package com.seanhoapps.chessgame.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.seanhoapps.chessgame.GameManager;
import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.GameEvent;
import com.seanhoapps.chessgame.pieces.PieceType;

public class GameController implements Observer {
	private final GameManager gameManager;
	private final JFrame frame;
	
	public GameController(GameManager gameManager, JFrame frame) {
		this.gameManager = gameManager;
		this.frame = frame;
		
		initWindowController();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		switch (event) {
			case AWAITING_PROMOTION_RESPONSE:
				onPromotion();
				break;
			case GAME_OVER_CHECKMATED:
				onCheckmate();
				break;
			case GAME_OVER_STALEMATED:
				onStalemate();
			default:
				// Do nothing
		}
		
		updateTitle();
	}
	
	private void onPromotion() {
		PieceType type = ChessDialog.showPromotionDialog(frame, gameManager.getTurnColor());
		gameManager.promote(type);
	}
	
	private void onCheckmate() {
		ChessColor winnerColor = gameManager.getTurnColor();
		ChessColor loserColor = gameManager.getEnemyColor(winnerColor);
		String message = winnerColor + " checkmated " + loserColor + "! " + winnerColor + " wins!";
		int response = ChessDialog.showRematchDialog(frame, message);
		onRematchResponse(response);
	}
	
	private void onStalemate() {
		String message = "No moves left for either player. Game ends in tie.";
		int response = ChessDialog.showRematchDialog(frame, message);
		onRematchResponse(response);
	}
	
	private void onRematchResponse(int response) {
		if (response != JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
		gameManager.start();
	}
	
	private void updateTitle() {
		frame.setTitle("Chess Game - " + gameManager.getTurnColor() + " TURN");
	}
	
	private void initWindowController() {
		updateTitle();
		gameManager.addObserver(this);
	}
}
