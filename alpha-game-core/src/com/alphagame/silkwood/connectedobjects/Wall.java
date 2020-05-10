package com.alphagame.silkwood.connectedobjects;

import com.alphagame.silkwood.actors.BaseActor;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends ConnectedObject {
	/**
	 * Initializes a wall onto a stage
	 * 
	 * @param x	X-position
	 * @param y	Y-position
	 * @param stage	Stage to place wall
	 */
	public Wall(float x, float y, String textureFile, Stage stage) {
		super(x*SIZE, y*SIZE, textureFile, stage);
		
		type = BaseActor.WALL;
	}
	
	/**
	 * Checks if the given area has a wall
	 * 
	 * @param check	Area to check for walls
	 * @param stage	Stage to look for walls
	 * @return	If there was a wall in the check area
	 */
	public static boolean isWall(Polygon check, Stage stage) {
		return BaseActor.isActor(check, new String[]{BaseActor.WALL}, stage);
	}
}