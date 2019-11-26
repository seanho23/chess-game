package com.seanhoapps.chessgame.gui;

public enum HighlightType {
	WARNING, DANGER;
	
	public boolean isWarning() {
		return this == WARNING;
	}
}