package com.seanhoapps.chessgame;

import com.seanhoapps.chessgame.pieces.Bishop;
import com.seanhoapps.chessgame.pieces.King;
import com.seanhoapps.chessgame.pieces.Knight;
import com.seanhoapps.chessgame.pieces.Pawn;
import com.seanhoapps.chessgame.pieces.Piece;
import com.seanhoapps.chessgame.pieces.PieceType;
import com.seanhoapps.chessgame.pieces.Queen;
import com.seanhoapps.chessgame.pieces.Rook;

public class PieceFactory {

	private PieceFactory() {}
	
	public static Piece createPiece(PieceType type, ChessColor color) {
		Piece piece;
		
		switch (type) {
			case KING:
				piece = new King(color);
				break;
			case QUEEN:
				piece = new Queen(color);
				break;
			case BISHOP:
				piece = new Bishop(color);
				break;
			case KNIGHT:
				piece = new Knight(color);
				break;
			case ROOK:
				piece = new Rook(color);
				break;
			case PAWN:
				piece = new Pawn(color);
				break;
			default:
				throw new AssertionError("Invalid PieceType");
		}

		return piece;
	}
}
