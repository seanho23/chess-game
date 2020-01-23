package com.seanhoapps.chessgame.pieces;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.seanhoapps.chessgame.ChessColor;

public class TestProviders {
	public static Set<ChessColor> colorProvider() {
		return ImmutableSet.of(ChessColor.WHITE, ChessColor.BLACK);
	}
}
