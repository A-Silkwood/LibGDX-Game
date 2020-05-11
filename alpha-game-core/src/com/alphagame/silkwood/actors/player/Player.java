package com.alphagame.silkwood.actors.player;

import java.awt.geom.Point2D.Float;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.ConObject;
import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends BaseActor {
	//movement
	public static final int SPEED = 600;
	public static final int SIZE = BaseScreen.SIZE;
	
	//stages
	Stage tStage;
	
	//arrows
	private BaseActor player;
	private Arrow upArrow;
	private Arrow rightArrow;
	private Arrow downArrow;
	private Arrow leftArrow;
	private int firstPressed;
	public static final int UP_ARROW = 1;
	public static final int RIGHT_ARROW = 2;
	public static final int DOWN_ARROW = 4;
	public static final int LEFT_ARROW = 8;
	//directions
	public static final Vector2 UP = new Vector2(0, 1);
	public static final Vector2 RIGHT = new Vector2(1, 0);
	public static final Vector2 DOWN = new Vector2(0, -1);
	public static final Vector2 LEFT = new Vector2(-1, 0);
	
	
	//textures
	private Animation<TextureRegion> playerTexture;
	private Animation<TextureRegion> backgroundTexture;
	private Animation<TextureRegion> upTexture;
	private Animation<TextureRegion> rightTexture;
	private Animation<TextureRegion> downTexture;
	private Animation<TextureRegion> leftTexture;
	
	//keys
	private static final int[] KEYS = {Keys.W, Keys.D, Keys.S, Keys.A};
	
	/**
	 * Initializes the player character onto a stage
	 * 
	 * @param x	Starting x-coordinate
	 * @param y	Starting y-coordinate
	 * @param aStage	Player's own stage
	 * @param tStage	Terrain stage
	 * @param stage	Stage to place the character
	 */
	public Player(float x, float y, Stage aStage, Stage tStage) {
		super(x, y, aStage);
		this.tStage = tStage;
		
		//initialize actors that help create the player
		upArrow = new Arrow(x, y, aStage, UP_ARROW);
		rightArrow = new Arrow(x, y, aStage, RIGHT_ARROW);
		downArrow = new Arrow(x, y, aStage, DOWN_ARROW);
		leftArrow = new Arrow(x, y, aStage, LEFT_ARROW);
		player = new BaseActor(x, y, aStage);	//must be last to be on top
		
		//load textures
		//background of the character
		backgroundTexture = loadTexture("playerbg.png");
		setAnimation(backgroundTexture);
		//arrows used by the character
		downTexture = loadTexture("directions.png", 2, 2, 0, 0);
		downArrow.setAnimation(downTexture);
		leftTexture = loadTexture("directions.png", 2, 2, 0, 1);
		leftArrow.setAnimation(leftTexture);
		rightTexture = loadTexture("directions.png", 2, 2, 1, 0);
		rightArrow.setAnimation(rightTexture);
		upTexture = loadTexture("directions.png", 2, 2, 1, 1);
		upArrow.setAnimation(upTexture);
		//main texture for the character itself
		playerTexture = loadTexture("player.png");
		player.setAnimation(playerTexture);
		setBoundary();
		firstPressed = 0;
		
		//sets all of the actors into their default states
		resetPlayer();
	}
	
	/**
	 * Actions to take each tick
	 */
	public void act(float delta) {
		super.act(delta);
		
		if(!moving) {
			checkKeys();
		}
	}
	
	/**
	 * Stops moving once it is at a point
	 * 
	 * @param point	Point to stop at
	 */
	public void stopAt(Float point) {
		super.stopAt(point);
		
		resetPlayer();
	}
	
	/**
	 * Performs actions based on what keys were just pressed
	 */
	private void checkKeys() {
		for(int key : KEYS) {
			if(Gdx.input.isKeyPressed(key)) {
				if(Gdx.input.isKeyJustPressed(key)) {
					switch(key) {
					case Keys.W:
						if(!willCollide(UP_ARROW)) {
							activateArrow(upArrow,UP_ARROW);
						}
						break;
					case Keys.D:
						if(!willCollide(RIGHT_ARROW)) {
							activateArrow(rightArrow,RIGHT_ARROW);
						}
						break;
					case Keys.S:
						if(!willCollide(DOWN_ARROW)) {
							activateArrow(downArrow,DOWN_ARROW);
						}
						break;
					case Keys.A:
						if(!willCollide(LEFT_ARROW)) {
							activateArrow(leftArrow,LEFT_ARROW);
						}
						break;
					default:
					}
				}
			} else {
				switch(key) {
				case Keys.W:
					if(upArrow.isActive()) {
						deactivateArrow(upArrow,UP_ARROW);
					}
					break;
				case Keys.D:
					if(rightArrow.isActive()) {
						deactivateArrow(rightArrow,RIGHT_ARROW);
					}
					break;
				case Keys.S:
					if(downArrow.isActive()) {
						deactivateArrow(downArrow,DOWN_ARROW);
					}
					break;
				case Keys.A:
					if(leftArrow.isActive()) {
						deactivateArrow(leftArrow,LEFT_ARROW);
					}
					break;
				default:
				}
			}
		}
		//Press space
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			activate(getAbility());
		}
	}
	
	/**
	 * Activates the given arrow
	 * 
	 * @param arrow	Arrow to activate
	 * @param arrowValue	Value to set if the arrow was pressed first
	 */
	private void activateArrow(Arrow arrow, int arrowValue) {
		arrow.toggleActive();
		if(firstPressed == 0) {
			firstPressed = arrowValue;
		}
	}
	
	/**
	 * Deactivates the given arrow
	 * 
	 * @param arrow	Arrow to deactivate
	 * @param arrowValue	Value to check if it was the first arrow pressed
	 */
	private void deactivateArrow(Arrow arrow, int arrowValue) {
		if(arrow.isActive()) {
			if(firstPressed == arrowValue) {
				activate(getAbility());
			} else {
				arrow.toggleActive();
			}
		}
	}
	
	/**
	 * Checks if a wall is in the arrow direction
	 * 
	 * @param arrowValue	What arrow to check for walls
	 * @return	If there was a wall in the given arrow direction
	 */
	private boolean willCollide(int arrowValue) {
		//sets up polygon to test overlap with wall collisions
		Polygon check = getBoundary();
		switch(arrowValue) {
		case UP_ARROW:
			check.translate(0, SIZE);
			break;
		case RIGHT_ARROW:
			check.translate(SIZE, 0);
			break;
		case DOWN_ARROW:
			check.translate(0, -SIZE);
			break;
		case LEFT_ARROW:
			check.translate(-SIZE, 0);
			break;
		default:
		}
		
		return ConObject.isConObject(check, true, tStage);
	}
	
	/**
	 * Activates the appropriate move based on the active arrows
	 */
	private void activate(int ability) {
		System.out.print("Activating Ability");
		//check for which move was chosen
		resetPlayer();
		switch(ability) {
		//1-key moves
		case 1:
			System.out.println("1(Move-Up)");
			move(UP);
			break;
		case 2:
			System.out.println("2(Move-Right)");
			move(RIGHT);
			break;
		case 4:
			System.out.println("4(Move-Down)");
			move(DOWN);
			break;
		case 8:
			System.out.println("8(Move-Left)");
			move(LEFT);
			break;
		//2-key moves
		//corner
		case 3:
			System.out.println("3(Corner-TopRight)");
			break;
		case 6:
			System.out.println("6(Corner-BottomRight)");
			break;
		case 9:
			System.out.println("9(Corner-BottomLeft)");
			break;
		case 12:
			System.out.println("12(Corner-TopLeft)");
			break;
		//line
		case 5:
			System.out.println("5(Line-Vertical)");
			break;
		case 10:
			System.out.println("10(Line-Horizontal)");
			break;
		//3-key moves
		case 7:
			System.out.println("7(Side-Right)");
			break;
		case 11:
			System.out.println("11(Side-Up)");
			break;
		case 13:
			System.out.println("13(Side-Left)");
			break;
		case 14:
			System.out.println("14(Side-Down)");
			break;
		//4-key move
		case 15:
			System.out.println("15(Ultimate)");
			break;
		default:
		}
	}
	
	/**
	 * Activates the ability 'move'
	 * 'move' moves the player one tile in one of the four cardinal directions
	 * 
	 * @param direction	Direction to move to
	 */
	private void move(Vector2 direction) {
		destination = new Float(getX() + (direction.x * SIZE),
				getY() + (direction.y * SIZE));
		moveTo(destination, SPEED);
	}
	
	/**
	 *	Returns the number corresponding to a player's move based on inputs
	 */
	private int getAbility() {
		int ability = 0;
		//adds to ability if the arrow was active
		if(downArrow.isActive()) {
			ability += DOWN_ARROW;
		}
		if(leftArrow.isActive()) {
			ability += LEFT_ARROW;
		}
		if(rightArrow.isActive()) {
			ability += RIGHT_ARROW;
		}
		if(upArrow.isActive()) {
			ability += UP_ARROW;
		}
		
		return ability;
	}
	
	/**
	 * Resets the all the actors for player onto the main player actor
	 */
	private void resetPlayer() {
		player.centerAtActor(this);
		firstPressed = 0;
		
		resetArrow(downArrow);
		resetArrow(leftArrow);
		resetArrow(rightArrow);
		resetArrow(upArrow);
	}
	
	/**
	 * Resets an arrow's variables
	 * 
	 * @param arrow	What arrow is being reset
	 */
	private void resetArrow(Arrow arrow){
		arrow.reset();
		arrow.centerAtActor(this);
		arrow.resetLocations();
	}
}