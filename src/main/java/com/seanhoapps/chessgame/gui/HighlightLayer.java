package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;

import javax.swing.JPanel;

import com.seanhoapps.chessgame.Board;

public class HighlightLayer extends JPanel {
	//Constants
	private static final Color INFO_COLOR = new Color(255, 255, 153, 150);
	private static final Color DANGER_COLOR = new Color(255, 0, 0);
	private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
	
	// Radial gradient constants
	private static final float[] RADIAL_GRADIENT_DISTRIBUTION = {0.0f, 1.0f};
	private static final Color[] RADIAL_GRADIENT_COLORS = {DANGER_COLOR, TRANSPARENT_COLOR};
		
	private Board board;
	
	public HighlightLayer(Board board) {
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
		int squareWidth = getWidth() / board.getColCount();
		int squareHeight = getHeight() / board.getRowCount();
		board.getPositionToHighlightType().forEach((position, highlightType) -> {
			int w = position.getCol() * squareWidth;
			int h = position.getRow() * squareHeight;
			switch (highlightType) {
				case INFO:
					g2d.setColor(INFO_COLOR);
					g2d.fillRect(w, h, squareWidth, squareHeight);
					break;
				case DANGER:
					float radius = squareWidth / 2f;
					float centerX = w + radius;
					float centerY = h + radius;
					Paint radialGradient = new RadialGradientPaint(centerX, centerY, radius, RADIAL_GRADIENT_DISTRIBUTION, RADIAL_GRADIENT_COLORS);
					g2d.setPaint(radialGradient);
					g2d.fillRect(w, h, squareWidth, squareHeight);
					break;
				default:
					// Do nothing
			}
		});
	}
	
	private void initPanel() {
		setOpaque(false);
	}
}
