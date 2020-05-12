package com.alphagame.silkwood.actors;

import java.awt.Point;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ConnectedActor extends BaseActor {
	/** variables that are used for position and collision */
	protected static final int ROWS = 8;
	protected static final int COLS = 8;
	protected static final int SIZE = BaseScreen.SIZE;
	
	/** variables for texture and animation */
	protected String textureFile;
	protected Animation<TextureRegion> texture;
	protected boolean[][] nearbyActors;
	protected boolean isWall;
	
	/** used for checking nearObjects */
	public enum Side {
		TOP(2, 1),
		RIGHT(1, 2),
		BOTTOM(0, 1),
		LEFT(1, 0),
		TOP_RIGHT(2, 2),
		BOTTOM_RIGHT(0, 2),
		BOTTOM_LEFT(0, 0),
		TOP_LEFT(2, 0);
		
		private Point point;
		
		/** Initializes a Side
		 * @param x X-Coordinate
		 * @param y Y-Coordinate */
		private Side(int x, int y) {
			point = new Point(x, y);
		}
		
		public Point getPoint() {
			return point;
		}
	}
	
	/** Creates a connected actor with the following components
	 * <p>
	 * A connected actor is an actor that can seamlessly connect [visually] to
	 * other connected actors. It will connect only to connected actors that
	 * have the same isWall value
	 * </p>
	 * <p>
	 * The actor needs a sprite sheet of a specific layout to work properly
	 * @param x	X-coordinate
	 * @param y Y-coordinate
	 * @param textureFile Sprite sheet to pull texture from
	 * @param isWall If this actor should be treated as a wall
	 * @param stage Stage to place actor on */
	public ConnectedActor(float x, float y, String textureFile, boolean isWall,
			Stage stage) {
		super(x*SIZE, y*SIZE, stage);
		
		/** set the actor to its default variables */
		this.textureFile = textureFile;
		this.isWall = isWall;
		setSize(SIZE, SIZE);
		setOrigin(SIZE/2, SIZE/2);
		setBoundary();
	}
	
	/** Loads the proper sprite based on nearby connected actors
	 * <p>
	 * Should be called once all connected actors have been initialized */
	public void loadTexture() {
		Point location = findConnectedTexture();
		texture = loadTexture(textureFile, ROWS, COLS, location.x, location.y);
		setAnimation(texture);
	}
	
	/** Gets the row and column of the sprite that should be used from the
	 * sprite sheet
	 * @return A point to where the sprite is on the sheet (row, column) */
	private Point findConnectedTexture() {
		updateNearbyActors(isWall);
		int row = -1;
		int col = -1;
		
		/** amount of connected actors directly touching */
		int touching = 0;
		if(checkSide(Side.TOP)) {touching++;}
		if(checkSide(Side.RIGHT)) {touching++;}
		if(checkSide(Side.BOTTOM)) {touching++;}
		if(checkSide(Side.LEFT)) {touching++;}
		
		/** gets the correct row and column */
		switch(touching) {
		case 0:
			row = 0; col = 0;
			break;
		case 1:
			row = 1;
			if(checkSide(Side.TOP)) {col = 0;}
			else if(checkSide(Side.RIGHT)) {col = 1;}
			else if(checkSide(Side.BOTTOM)) {col = 2;}
			else if(checkSide(Side.LEFT)) {col = 3;}
			else {row = 3; col = 7;}
			break;
		case 2:
			if(checkSide(Side.TOP) && checkSide(Side.RIGHT)) {col = 0;}
			else if(checkSide(Side.RIGHT) && checkSide(Side.BOTTOM)) {col = 1;}
			else if(checkSide(Side.BOTTOM) && checkSide(Side.LEFT)) {col = 2;}
			else if(checkSide(Side.LEFT) && checkSide(Side.TOP)) {col = 3;}
			else if(checkSide(Side.LEFT) && checkSide(Side.RIGHT)) {col = 2; row = 0;}
			else if(checkSide(Side.BOTTOM) && checkSide(Side.TOP)) {col = 3; row = 0;}
			if(row == -1) {
				row = 2;
				switch(col) {
				case 0:
					if(!checkSide(Side.TOP_RIGHT)) {row++;}
					break;
				case 1:
					if(!checkSide(Side.BOTTOM_RIGHT)) {row++;}
					break;
				case 2:
					if(!checkSide(Side.BOTTOM_LEFT)) {row++;}
					break;
				case 3:
					if(!checkSide(Side.TOP_LEFT)) {row++;}
					break;
				default:
					row = 3; col = 7;
				}
			}
			break;
		case 3:
			row = 4;
			if(!checkSide(Side.BOTTOM)) {col = 0;}
			else if(!checkSide(Side.LEFT)) {col = 1;}
			else if(!checkSide(Side.TOP)) {col = 2;}
			else if(!checkSide(Side.RIGHT)) {col = 3;}
			switch(col) {
			case 0:
				if(!checkSide(Side.TOP_RIGHT)) {row++;}
				break;
			case 1:
				if(!checkSide(Side.BOTTOM_RIGHT)) {row++;}
				break;
			case 2:
				if(!checkSide(Side.BOTTOM_LEFT)) {row++;}
				break;
			case 3:
				if(!checkSide(Side.TOP_LEFT)) {row++;}
				break;
			default:
				row = 3; col = 7;
			}
			if(row != 3 || col != 7) {
				switch(col) {
				case 0:
					if(!checkSide(Side.TOP_LEFT)) {row += 2;}
					break;
				case 1:
					if(!checkSide(Side.TOP_RIGHT)) {row += 2;}
					break;
				case 2:
					if(!checkSide(Side.BOTTOM_RIGHT)) {row += 2;}
					break;
				case 3:
					if(!checkSide(Side.BOTTOM_LEFT)) {row += 2;}
					break;
				default:
					row = 3; col = 7;
				}
			}
			break;
		case 4:
			/** amount of connected actors diagonally touching */
			int emptyCorners = 0;
			if(!checkSide(Side.TOP_RIGHT)) {emptyCorners++;}
			if(!checkSide(Side.BOTTOM_RIGHT)) {emptyCorners++;}
			if(!checkSide(Side.BOTTOM_LEFT)) {emptyCorners++;}
			if(!checkSide(Side.TOP_LEFT)) {emptyCorners++;}
			
			switch(emptyCorners) {
			case 0:
				row = 0; col = 1;
				break;
			case 1:
				row = 0;
				if(!checkSide(Side.TOP_RIGHT)) {col = 4;}
				else if(!checkSide(Side.BOTTOM_RIGHT)) {col = 5;}
				else if(!checkSide(Side.BOTTOM_LEFT)) {col = 6;}
				else if(!checkSide(Side.TOP_LEFT)) {col = 7;}
				else {row = 3; col = 7;}
				break;
			case 2:
				row = 1;
				if(!checkSide(Side.TOP_RIGHT) && !checkSide(Side.BOTTOM_RIGHT)) {col = 4;}
				else if(!checkSide(Side.BOTTOM_RIGHT) && !checkSide(Side.BOTTOM_LEFT)) {col = 5;}
				else if(!checkSide(Side.BOTTOM_LEFT) && !checkSide(Side.TOP_LEFT)) {col = 6;}
				else if(!checkSide(Side.TOP_LEFT) && !checkSide(Side.TOP_RIGHT)) {col = 7;}
				else if(!checkSide(Side.TOP_RIGHT) && !checkSide(Side.BOTTOM_LEFT)) {row = 3; col = 5;}
				else if(!checkSide(Side.TOP_LEFT) && !checkSide(Side.BOTTOM_RIGHT)) {row = 3; col = 6;}
				else {row = 3; col = 7;}
				break;
			case 3:
				row = 2;
				if(checkSide(Side.TOP_LEFT)) {col = 4;}
				else if(checkSide(Side.TOP_RIGHT)) {col = 5;}
				else if(checkSide(Side.BOTTOM_RIGHT)) {col = 6;}
				else if(checkSide(Side.BOTTOM_LEFT)) {col = 7;}
				else {row = 3; col = 4;}
				break;
			case 4:
				row = 3; col = 4;
				break;
			default:
				row = 3; col = 7;
			}
			
			break;
		default:
			row = 3; col = 7;
		}
		
		/** row: 3 | col: 7 means something went wrong */
		return new Point(row,col);
	}

	/** Updates nearbyActors with the current connected actors
	 * @param lookForWalls If nearby actors need to be walls or not */
	public void updateNearbyActors(boolean lookForWalls) {
		nearbyActors = new boolean[3][3];
		float[] boundaryVertices = getBoundary().getTransformedVertices();
		float size = getWidth();
		Polygon check;
		
		for(int i = 0; i < nearbyActors.length; i++) {
			for(int j = 0; j < nearbyActors[i].length; j++) {
				check = new Polygon(boundaryVertices);
				check.translate(size*(j - 1), size*(i - 1));
				nearbyActors[i][j] = isConnectedActor(check, lookForWalls,
						getStage());
			}
		}
	}
	
	/** If a side of the actor had a connected actor
	 * @param side Side to check
	 * @return If the side had a connected actor */
	private boolean checkSide(Side side) {
		Point check = side.getPoint();
		return nearbyActors[check.x][check.y];
	}
	
	/** Checks an area for connected actors
	 * @param check	Area to look for connected actors
	 * @param lookForWalls If connected actors found need to be walls or not
	 * @param stage Stage to search for connected actors
	 * @return If a connected actor was found */
	public static boolean isConnectedActor(Polygon check, boolean lookForWalls,
			Stage stage) {
		for(Object connectedActor : BaseActor.getList(ConnectedActor.class,
				stage, ActorLocation.CONNECTED_ACTOR.toString())) {
			if(lookForWalls == ((ConnectedActor)connectedActor).isWall() &&
					check.getBoundingRectangle().overlaps(
					((BaseActor)connectedActor).getBoundary().
					getBoundingRectangle())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWall() {
		return isWall;
	}
}