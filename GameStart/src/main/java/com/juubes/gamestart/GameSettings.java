package com.juubes.gamestart;

import java.awt.Dimension;

public class GameSettings {
	public int targetFPS;
	public Dimension screenSize;
	public boolean screenBorders;

	public GameSettings() {
		this.targetFPS = 60;
		this.screenSize = new Dimension(1280, 720);
		this.screenBorders = true;
	}

}
