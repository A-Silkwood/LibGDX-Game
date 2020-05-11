package com.alphagame.silkwood.actors.player;

import java.awt.geom.Point2D.Float;

import com.alphagame.silkwood.actors.BaseActor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Arrow extends BaseActor {
	//movement
	public static final int SPEED = 150;
	public static final int SIZE = 25;
	
	//arrow's direction
	private final Vector2 arrowDirection;
	
	//activation
	private boolean active;
	private Float inactiveLocation;
	private Float activeLocation;
	
	/**
	 * Constructor for an arrow used by the player
	 * 
	 * @param x	Starting x-coordinate
	 * @param y	Starting y-coordinate
	 * @param stage	Stage to place the character
	 */
	public Arrow(float x, float y, Stage stage, int direction) {
		super(x, y, stage);
		
		switch(direction) {
			case 1:
				arrowDirection = Direction.UP.getDirection();
				break;
			case 2:
				arrowDirection = Direction.RIGHT.getDirection();
				break;
			case 4:
				arrowDirection = Direction.DOWN.getDirection();
				break;
			case 8:
				arrowDirection = Direction.LEFT.getDirection();
				break;
			default:
				arrowDirection = new Vector2();
		}
		
		inactiveLocation = new Float();
		activeLocation = new Float();
		active = false;
	}
	
	/**
	 * Sets the arrow in proper direction when turned on or off
	 * 
	 * @param direction	Direction to go when active and go the opposite way
	 * 					while inactive
	 */
	public void toggleActive() {
		if(!active) {
			//when activated it goes with the direction
			active = true;
			moveTo(activeLocation, SPEED);
		} else {
			//when deactivated it goes in the opposite direction of direction
			active = false;
			moveTo(inactiveLocation, SPEED);
		}
	}
	
	/**
	 * Tests if the arrow is active or not
	 * 
	 * @return	Whether the arrow is active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Actions to take each tick
	 */
	public void act(float delta) {
		super.act(delta);
	}
	
	/**
	 * Resets the arrow to all its default values, but position
	 */
	public void reset() {
		moving = false;
		active = false;
	}
	
	/**
	 * Resets the locations for when the arrow is active or inactive
	 */
	public void resetLocations() {
		inactiveLocation.setLocation(getX(), getY());
		activeLocation.setLocation(getX() + (arrowDirection.x *
				SIZE), getY() + (arrowDirection.y *
				SIZE));
	}
}
