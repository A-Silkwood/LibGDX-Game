package com.alphagame.silkwood.actors;

import java.awt.Point;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ConObject extends BaseActor {
	protected static final int ROWS = 8;
	protected static final int COLS = 8;
	protected static final int SIZE = BaseScreen.SIZE;
	
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
		
		private Side(int x, int y) {
			point = new Point(x, y);
		}
		
		public Point getPoint() {
			return point;
		}
	}
	
	protected String textureFile;
	protected Animation<TextureRegion> texture;
	protected boolean[][] nearObjects;
	protected boolean isWall;
	
	public ConObject(float x, float y, String textureFile, boolean isWall,
			Stage stage) {
		super(x*SIZE, y*SIZE, stage);
		
		this.textureFile = textureFile;
		this.isWall = isWall;
		
		setSize(SIZE, SIZE);
		setOrigin(SIZE/2, SIZE/2);
		setBoundary();
	}
	
	/**
	 * Loads the proper texture based on surrounding walls
	 */
	public void loadTexture() {
		Point location = findConnectedTexture();
		texture = loadTexture(textureFile, ROWS, COLS, location.x, location.y);
		setAnimation(texture);
	}
	
	/**
	 * Finds the proper object texture to use from a sprite sheets based on the
	 * surrounding objects.
	 * 
	 * @return A point that corresponds to the proper sprite on a sprite sheet
	 */
	private Point findConnectedTexture() {
		getNearbyObjects(isWall);
		int row = -1;
		int col = -1;
		
		int touching = 0;
		if(checkSide(Side.TOP)) {touching++;}
		if(checkSide(Side.RIGHT)) {touching++;}
		if(checkSide(Side.BOTTOM)) {touching++;}
		if(checkSide(Side.LEFT)) {touching++;}
		
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

		return new Point(row,col);
	}

	/**
	 * Finds what sides are touching certain objects given
	 * 
	 * @param types	Types of objects to check for
	 */
	public void getNearbyObjects(boolean lookForWalls) {
		nearObjects = new boolean[3][3];
		float[] boundaryVertices = getBoundary().getTransformedVertices();
		float size = getWidth();
		Polygon check;
		
		for(int i = 0; i < nearObjects.length; i++) {
			for(int j = 0; j < nearObjects[i].length; j++) {
				check = new Polygon(boundaryVertices);
				check.translate(size*(j - 1), size*(i - 1));
				nearObjects[i][j] = isConObject(check,lookForWalls,getStage());
			}
		}
	}
	
	public static boolean isConObject(Polygon check, boolean lookForWalls,
			Stage stage) {
		for(Object conObject : BaseActor.getList(ConObject.class, stage,
				ActorLoc.CON_OBJECT.toString())) {
			if(lookForWalls == ((ConObject)conObject).isWall() && check.
					getBoundingRectangle().overlaps(((BaseActor)conObject).
					getBoundary().getBoundingRectangle())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWall() {
		return isWall;
	}

	private boolean checkSide(Side side) {
		Point check = side.getPoint();
		return nearObjects[check.x][check.y];
	}
}