package com.alphagame.silkwood.actors.player;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.ConnectedActor;
import com.alphagame.silkwood.actors.Direction;
import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends BaseActor {
	/** constants that affect how the player moves */
	public static final int SPEED = 600;
	public static final int SIZE = BaseScreen.SIZE;
	
	/** actors that help make up the player */
	private BaseActor facePlate;
	private Arrow upArrow;
	private Arrow rightArrow;
	private Arrow downArrow;
	private Arrow leftArrow;
	/** stage that contains terrain actors */
	Stage tStage;
	
	/** different textures that make up the player */
	private Animation<TextureRegion> facePlateTexture;
	private Animation<TextureRegion> backPlateTexture;
	private String arrowSprites = "directions.png";
	
	/** variable that is used to check what arrow was pressed first */
	private int firstPressed;
	
	/** Creates a player at the given place
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param aStage Stage to place the player in
	 * @param tStage Stage to store to interact with terrain */
	public Player(float x, float y, Stage aStage, Stage tStage) {
		super(x*SIZE, y*SIZE, aStage);
		x *= SIZE;
		y *= SIZE;
		
		this.tStage = tStage;
		
		/** other actors that make up the actor placed bottom to top */
		upArrow = new Arrow(x, y, aStage, Direction.UP.getValue());
		rightArrow = new Arrow(x, y, aStage, Direction.RIGHT.getValue());
		downArrow = new Arrow(x, y, aStage, Direction.DOWN.getValue());
		leftArrow = new Arrow(x, y, aStage, Direction.LEFT.getValue());
		facePlate = new BaseActor(x, y, aStage);
		
		/** loads the back plate of the player */
		backPlateTexture = loadTexture("playerbg.png");
		setAnimation(backPlateTexture);
		/** loads the textures for each of the arrows */
		Point sheetLocation = Direction.UP.getSheetLocation();
		upArrow.setAnimation(loadTexture(arrowSprites, 2, 2, sheetLocation.x,
				sheetLocation.y));
		sheetLocation = Direction.RIGHT.getSheetLocation();
		rightArrow.setAnimation(loadTexture(arrowSprites, 2, 2, sheetLocation.x
				, sheetLocation.y));
		sheetLocation = Direction.DOWN.getSheetLocation();
		downArrow.setAnimation(loadTexture(arrowSprites, 2, 2, sheetLocation.x,
				sheetLocation.y));
		sheetLocation = Direction.LEFT.getSheetLocation();
		leftArrow.setAnimation(loadTexture(arrowSprites, 2, 2, sheetLocation.x,
				sheetLocation.y));
		/** loads the face plate of the player */
		facePlateTexture = loadTexture("player.png");
		facePlate.setAnimation(facePlateTexture);
		
		/** set the player to its default variables */
		setBoundary();
		resetPlayer();
	}
	
	/** Actions performed each tick
	 * <p>
	 * Checks if the player is affected by the user's inputs
	 * @param delta Time since last call */
	public void act(float delta) {
		super.act(delta);
		
		if(!moving) {
			checkKeys();
		}
	}
	
	/** Performs actions based on user input */ 
	private void checkKeys() {
		/** Inputs that affect arrows */
		checkArrowPress(Keys.UP, Keys.W, upArrow, Direction.UP.getValue());
		checkArrowPress(Keys.RIGHT, Keys.D, rightArrow, Direction.RIGHT.getValue());
		checkArrowPress(Keys.DOWN, Keys.S, downArrow, Direction.DOWN.getValue());
		checkArrowPress(Keys.LEFT, Keys.A, leftArrow, Direction.LEFT.getValue());
		
		/** Activate ability button */
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if(getAbility() != 0) {
				activate(getAbility());
			}
		}
	}
	
	/** Checks if keys that affect arrows are pressed or released
	 * @param key1 Primary key
	 * @param key2 Secondary key
	 * @param arrow Arrow to activate or deactivate
	 * @param arrowVal Value of the arrow
	 */
	private void checkArrowPress(int key1, int key2, Arrow arrow,
			int arrowVal) {
		if(Gdx.input.isKeyPressed(key1)||Gdx.input.isKeyPressed(key2)) {
			if((Gdx.input.isKeyJustPressed(key1) || Gdx.input.
					isKeyJustPressed(key2)) && !arrow.isActive() &&
					!isWall(arrowVal)) {
				activateArrow(arrow, arrowVal);
			}
		} else if(arrow.isActive()) {
			deactivateArrow(arrow, arrowVal);
		}
	}
	
	/** Stops the player from moving once it gets to a point
	 * @param point Point to stop moving at */
	public void stopAt(Float point) {
		super.stopAt(point);
		
		resetPlayer();
	}
	
	/** Activates an arrow
	 * @param arrow Arrow to activate
	 * @param arrowValue The value of the arrow */
	private void activateArrow(Arrow arrow, int arrowValue) {
		arrow.toggleActive();
		if(firstPressed == 0) {
			firstPressed = arrowValue;
		}
	}
	
	/** Deactivates an arrow
	 * <p>
	 * If the arrow was the first one activated the player either resets or
	 * move based on whether other arrows were active
	 * @param arrow Arrow to deactivate
	 * @param arrowValue The value of the arrow */
	private void deactivateArrow(Arrow arrow, int arrowValue) {
		if(arrow.isActive()) {
			if(firstPressed == arrowValue) {
				if(getAbility() == 1 || getAbility() == 2 || getAbility() == 4
						|| getAbility() == 8) {
					activate(getAbility());
				} else {
					resetPlayer();
				}
			} else {
				arrow.toggleActive();
			}
		}
	}
	
	/** Checks if a wall is in the direction of an arrow
	 * @param arrowValue The value of the arrow
	 * @return If a was was found in the arrow's direction */
	private boolean isWall(int arrowValue) {
		//sets up polygon to test overlap with wall collisions
		Polygon check = getBoundary();
		switch(arrowValue) {
		case 1:
			check.translate(0, SIZE);
			break;
		case 2:
			check.translate(SIZE, 0);
			break;
		case 4:
			check.translate(0, -SIZE);
			break;
		case 8:
			check.translate(-SIZE, 0);
			break;
		default:
		}
		
		return ConnectedActor.isConnectedActor(check, true, tStage);
	}
	
	/** Activates one of the abilities of the player
	 * @param ability Value of the ability that is going to activated */
	private void activate(int ability) {
		System.out.printf("Activating Ability");
		//check for which move was chosen
		resetPlayer();
		switch(ability) {
		//1-key moves
		case 1:
			System.out.printf("%d (Move-Up)%n", ability);
			move(Direction.UP.getDirection());
			break;
		case 2:
			System.out.printf("%d (Move-Right)%n", ability);
			move(Direction.RIGHT.getDirection());
			break;
		case 4:
			System.out.printf("%d (Move-Down)%n", ability);
			move(Direction.DOWN.getDirection());
			break;
		case 8:
			System.out.printf("%d (Move-Left)%n", ability);
			move(Direction.LEFT.getDirection());
			break;
		//2-key moves
		//corner
		case 3:
			System.out.printf("%d (Corner-TopRight)%n", ability);
			break;
		case 6:
			System.out.printf("%d (Corner-BottomRight)%n", ability);
			break;
		case 9:
			System.out.printf("%d (Corner-BottomLeft)%n", ability);
			break;
		case 12:
			System.out.printf("%d (Corner-TopLeft)%n", ability);
			break;
		//line
		case 5:
			System.out.printf("%d (Line-Vertical)%n", ability);
			break;
		case 10:
			System.out.printf("%d (Line-Horizontal)%n", ability);
			break;
		//3-key moves
		case 7:
			System.out.printf("%d (Side-Right)%n", ability);
			break;
		case 11:
			System.out.printf("%d (Side-Up)%n", ability);
			break;
		case 13:
			System.out.printf("%d (Side-Left)%n", ability);
			break;
		case 14:
			System.out.printf("%d (Side-Down)%n", ability);
			break;
		//4-key move
		case 15:
			System.out.printf("%d (Ultimate)%n", ability);
			break;
		default:
		}
	}
	
	/** Determines what ability should be activated based on the currently
	 * active arrows
	 * @return The number that corresponds to an ability */
	private int getAbility() {
		int ability = 0;
		if(downArrow.isActive())
		ability += Direction.DOWN.getValue();
		if(leftArrow.isActive())
		ability += Direction.LEFT.getValue();
		if(rightArrow.isActive())
		ability += Direction.RIGHT.getValue();
		if(upArrow.isActive())
		ability += Direction.UP.getValue();
		
		return ability;
	}
	
	/** Ability: Move
	 * <p>
	 * Moves the player one block in any direction
	 * @param direction Direction to move one block towards
	 */
	private void move(Vector2 direction) {
		destination = new Float(getX() + (direction.x * SIZE),
				getY() + (direction.y * SIZE));
		moveTo(destination, SPEED);
	}
	
	/** Resets the player to its default state */
	private void resetPlayer() {
		facePlate.centerAtActor(this);
		firstPressed = 0;
		
		resetArrow(downArrow);
		resetArrow(leftArrow);
		resetArrow(rightArrow);
		resetArrow(upArrow);
	}
	
	/** Resets an arrow to its default state
	 * @param arrow Arrow to reset */
	private void resetArrow(Arrow arrow){
		arrow.centerAtActor(this);
		arrow.reset();
	}
}