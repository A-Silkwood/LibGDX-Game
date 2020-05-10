package com.alphagame.silkwood.connectedobjects;

import com.alphagame.silkwood.actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Floor extends ConnectedObject {
	/**
	 * Initializes a floor onto a stage
	 * 
	 * @param x	X-position
	 * @param y	Y-position
	 * @param stage	Stage to place wall
	 */
	public Floor(float x, float y, String textureFile, Stage stage) {
		super(x*SIZE, y*SIZE, textureFile, stage);
		
		type = BaseActor.FLOOR;
	}
}