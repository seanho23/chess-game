package com.seanhoapps.chessgame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.seanhoapps.chessgame.pieces.PieceType;

public class PieceImage {
	// Constants
	private static final String imageFolder = "/images/pieces";

	private static Map<String, Image> pieceImages;
	
	private PieceImage() {}
	
	public static Image getImage(PieceType type, ChessColor color) {
		// Read and cache images on first use
		if (pieceImages == null) {
			initImages();
		}
		
		String imageName = ("" + color + type).toLowerCase();
		if (!pieceImages.containsKey(imageName)) {
			System.err.println("Unable to find image: " + imageName);
		}
		
		return pieceImages.get(imageName);
	}
	
	private static void initImages() {
		pieceImages  = new HashMap<String, Image>();
		// Obtain list of image files
		File[] files = new File[0];
		try {
			File directory = new File(PieceImage.class.getResource(imageFolder).toURI());
			// Filter out non-images
			files = directory.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".png");
			});
		}
		catch (URISyntaxException e) {
			System.err.println("Unable to parse URI: " + PieceImage.class.getResource(imageFolder));
		}
		
		// Read and cache images
		for (File file : files) {
			String filename = file.getName();
			String imageName = filename.substring(0, filename.lastIndexOf("."));
			try {
				pieceImages.put(imageName, ImageIO.read(file));
			} catch (IOException e) {
				System.err.println("Unable to read file: " + file.getName());
			}
		}
	}
}
