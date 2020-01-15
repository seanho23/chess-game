package com.seanhoapps.chessgame.gui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.seanhoapps.chessgame.ChessColor;
import com.seanhoapps.chessgame.PieceImage;
import com.seanhoapps.chessgame.pieces.PieceType;

public class ChessDialog {
	
	private static final PieceType[] promotionPieceTypes = {PieceType.BISHOP, PieceType.ROOK, PieceType.KNIGHT, PieceType.QUEEN};
	
	private ChessDialog() {}
	
	public static PieceType showPromotionDialog(JFrame frame, ChessColor color) {
		String message = color + " Pawn reached last rank! Promote the Pawn:";
		Icon[] options = createPromotionIcons(color);
		int response = JOptionPane.showOptionDialog(frame, message, "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		return response == JOptionPane.CLOSED_OPTION ? promotionPieceTypes[3] : promotionPieceTypes[response];
	}
	
	public static int showRematchDialog(JFrame frame, String message) {
		String[] options = {"Rematch", "Exit"};
		return JOptionPane.showOptionDialog(frame, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}
	
	private static Icon[] createPromotionIcons(ChessColor color) {
		Icon[] icons = new Icon[promotionPieceTypes.length];
		for (int i = 0; i < promotionPieceTypes.length; i++) {
			icons[i] = new ImageIcon(PieceImage.getImage(promotionPieceTypes[i], color));
		}
		return icons;
	}
}
