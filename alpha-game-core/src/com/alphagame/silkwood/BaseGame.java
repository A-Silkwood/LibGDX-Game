package com.alphagame.silkwood;

import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public abstract class BaseGame extends Game {
	private static BaseGame game;
	public static LabelStyle labelStyle;
	public static TextButtonStyle textButtonStyle;
	
	/**
	 * Creates the game
	 */
	public BaseGame() {
		game = this;
	}
	
	/**
	 * Initializes input on creation
	 */
	public void create() {
		InputMultiplexer input = new InputMultiplexer();
		Gdx.input.setInputProcessor(input);
	}
	
	/**
	 * Select what screen to use
	 * 
	 * @param screen	Screen to switch to
	 */
	public static void setActiveScreen(BaseScreen screen) {
		game.setScreen(screen);
	}
}