package com.seanhoapps.chessgame.gui;

import com.seanhoapps.chessgame.GameEvent;

public interface Observer {
	public void onGameEvent(GameEvent state);
}
