package com.seanhoapps.chessgame.gui;

public enum HighlightType {
	INFO, WARNING, DANGER;
	
	public boolean isWarning() {
		return this == WARNING;
	}
}