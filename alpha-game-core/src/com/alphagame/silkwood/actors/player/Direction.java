package com.alphagame.silkwood.actors.player;

import java.awt.Point;

import com.alphagame.silkwood.actors.BaseActor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public enum Direction {
	UP(new Vector2(0, 1), 1, new Point(1, 1)),
	RIGHT(new Vector2(1, 0), 2, new Point(1, 0)),
	DOWN(new Vector2(0, -1), 4, new Point(0, 0)),
	LEFT(new Vector2(-1, 0), 8, new Point(0, 1));
	
	private Animation<TextureRegion> texture;
	private Vector2 direction;
	private int value;
	
	private Direction(Vector2 dir, int val, Point location) {
		texture = BaseActor.loadTexture("directions.png", 2, 2, location.x,
				location.y);
		direction = dir;
		value = val;
	}
	
	public Animation<TextureRegion> getTexture(){
		return texture;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public int getValue() {
		return value;
	}
}