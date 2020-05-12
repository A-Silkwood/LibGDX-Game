package com.alphagame.silkwood.actors;

import java.awt.Point;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
	UP(new Vector2(0, 1), 1, new Point(1, 1)),
	RIGHT(new Vector2(1, 0), 2, new Point(1, 0)),
	DOWN(new Vector2(0, -1), 4, new Point(0, 0)),
	LEFT(new Vector2(-1, 0), 8, new Point(0, 1));
	
	private Point sheetLocation;
	private Vector2 direction;
	private int value;
	
	/** Initializes a Direction
	 * <p>
	 * Directions are meant to be associated with actors that face a direction
	 * @param dir Direction as a vector
	 * @param val Value associated with the vector
	 * @param location Location of sprite based on template */
	private Direction(Vector2 dir, int val, Point location) {
		sheetLocation = location;
		direction = dir;
		value = val;
	}
	
	public Point getSheetLocation() {
		return sheetLocation;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public int getValue() {
		return value;
	}
}