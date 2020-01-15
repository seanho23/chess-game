package com.seanhoapps.chessgame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class CheckerboardLayer extends JPanel {
	// Constants
	private static final Color LIGHT_COLOR = new Color(178, 203, 174);
	private static final Color DARK_COLOR = new Color(106, 146, 101);
	private static final int TEXT_OFFSET = 5;
	
	private int rows;
	private int columns;
	
	private BufferedImage checkerboard;
	private boolean isLoaded = false;
		
	public CheckerboardLayer(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Drawing BufferedImage of checkerboard is more efficient, especially since checkerboard is static and will not change during the game
		if (isLoaded) {
			g.drawImage(checkerboard, 0, 0, null);
			return;
		}
		
		// Draw checkerboard on another thread to minimize GUI start-up time
		// Initial calls to font and drawString are time consuming
		loadCheckerboard();
		
		// Display temporary checkerboard (no numbers or letters)
		int squareWidth = getWidth() / columns;
		int squareHeight = getHeight() / rows;
		for (int row = 0, h = 0; row < rows; row++, h += squareHeight) {
			for (int column = 0, w = 0; column < columns; column++, w += squareWidth) {
				Color squareColor = LIGHT_COLOR;
				if ((row + column) % 2 != 0) {
					squareColor = DARK_COLOR;
				}
				
				g.setColor(squareColor);
				g.fillRect(w, h, squareWidth, squareHeight);
			}
		}
	}
	
	private void loadCheckerboard() {
		SwingWorker<BufferedImage, Void> drawTask = new SwingWorker<BufferedImage, Void>() {

			@Override
			protected BufferedImage doInBackground() throws Exception {
				Font font = new Font("Arial", Font.BOLD, 16);
				FontMetrics metrics = getFontMetrics(font);
				int ascent = metrics.getAscent();
				int descent = metrics.getDescent();
				
				int width = getWidth();
				int height = getHeight();
				int squareWidth = width / columns;
				int squareHeight = height / rows;
				BufferedImage bgImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D) bgImage.getGraphics();
				
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setFont(font);
				
				int rowNumber = rows;
				char columnLetter = 'a';
				
				for (int row = 0, h = 0; row < rows; row++, h += squareHeight) {
					for (int column = 0, w = 0; column < columns; column++, w += squareWidth) {
						Color squareColor = LIGHT_COLOR;
						Color fontColor = DARK_COLOR;
						if ((row + column) % 2 != 0) {
							squareColor = DARK_COLOR;
							fontColor = LIGHT_COLOR;
						}
						
						g2d.setColor(squareColor);
						g2d.fillRect(w, h, squareWidth, squareHeight);
						g2d.setColor(fontColor);
						
						// Draw numbers on first column (top left of square)
						if (column == 0) {
							String text = "" + rowNumber;
							rowNumber--;
							g2d.drawString(text, w + TEXT_OFFSET, h + ascent + TEXT_OFFSET);
						}
						
						// Draw letters on last row (bottom right of square)
						if (row == rows - 1) {
							String text = "" + columnLetter;
							columnLetter++;
							Rectangle2D textBounds = metrics.getStringBounds(text, g2d);
							g2d.drawString(text, (w + squareWidth) - (int) textBounds.getWidth() - TEXT_OFFSET, (h + squareHeight) - descent - TEXT_OFFSET);
						}
					}
				}
				g2d.dispose();
				
				return bgImage;
			}
			
			@Override
			protected void done() {
				try {
					checkerboard = get();
					isLoaded = true;
					repaint();
				}
				catch (Exception e) {
					System.err.println("Unable to draw checkerboard to BufferedImage");
				}
			}
			
		};
		
		drawTask.execute();
	}
}
