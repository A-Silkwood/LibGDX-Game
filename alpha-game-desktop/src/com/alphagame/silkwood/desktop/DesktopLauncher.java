package com.alphagame.silkwood.desktop;

import com.alphagame.silkwood.AlphaGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopLauncher {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Game alphaGame = new AlphaGame();
		System.out.println("Initializing Launch...");
		LwjglApplication launcher = new LwjglApplication(alphaGame,
				"Alpha Game", 960, 576);
	}
}