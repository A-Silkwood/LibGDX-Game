package com.alphagame.silkwood;

import com.alphagame.silkwood.screens.TestScreen1;
import com.alphagame.silkwood.screens.TestScreen2;

@SuppressWarnings("unused")
public class AlphaGame extends BaseGame {
	/** Actions performed on creation */
	public void create() {
		super.create();
		
		System.out.printf("Starting game%n");
		setActiveScreen(new TestScreen1());
	}
}