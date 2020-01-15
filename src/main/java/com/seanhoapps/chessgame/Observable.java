package com.seanhoapps.chessgame;

import com.seanhoapps.chessgame.gui.Observer;

public interface Observable {
	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notifyObservers(GameEvent state);
}
