package com.alphagame.silkwood;

import com.alphagame.silkwood.screens.TestScreen1;
import com.alphagame.silkwood.screens.TestScreen2;

@SuppressWarnings("unused")
public class AlphaGame extends BaseGame {
	public void create() {
		super.create();
		
		System.out.println("Starting game");
		setActiveScreen(new TestScreen1());
	}
}