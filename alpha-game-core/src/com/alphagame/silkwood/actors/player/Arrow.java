package com.alphagame.silkwood.actors.player;

import java.awt.geom.Point2D.Float;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.Direction;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Arrow extends BaseActor {
	/* constants that affect how the arrow moves */
	public static final int SPEED = 150;
	public static final int SIZE = 25;
	
	/* vector in the direction the arrow faces */
	private final Vector2 arrowDirection;
	
	/* variables to handle if the arrow is active or not */
	private boolean active;
	private Float inactiveLocation;
	private Float activeLocation;
	

	/** Creates an arrow at the given place
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param stage Stage to place the arrow in
	 * @param direction Direction the arrow faces */
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
	
	/** Actions performed each tick
	 * @param delta Time since last call */
	public void act(float delta) {
		super.act(delta);
	}
	
	/** Toggles the arrow between active and inactive */
	public void toggleActive() {
		if(!active) {
			active = true;
			moveTo(activeLocation, SPEED);
		} else {
			active = false;
			moveTo(inactiveLocation, SPEED);
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	/** Resets the arrow to its default state */
	public void reset() {
		moving = false;
		active = false;
		/** Resets the active and inactive locations based on position*/       
		inactiveLocation.setLocation(getX(), getY());
		activeLocation.setLocation(getX() + (arrowDirection.x * SIZE),
				getY() + (arrowDirection.y * SIZE));
	}
}
