package com.alphagame.silkwood.connectedobjects;

import java.awt.Point;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.screens.BaseScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ConnectedObject extends BaseActor {
	protected static final int ROWS = 8;
	protected static final int COLS = 8;
	protected static final int SIZE = BaseScreen.SIZE;
	
	private final static Point T = new Point(2,1);	//top
	private final static Point R = new Point(1,2);	//right
	private final static Point B = new Point(0,1);	//bottom
	private final static Point L = new Point(1,0);	//left
	private final static Point TR = new Point(2,2);	//top-right
	private final static Point BR = new Point(0,2);	//bottom-right
	private final static Point BL = new Point(0,0);	//bottom-left
	private final static Point TL = new Point(2,0);	//top-left
	
	protected String textureFile;
	protected Animation<TextureRegion> texture;
	protected boolean[][] nearObjects;
	protected String type;
	
	public ConnectedObject(float x, float y, String textureFile, Stage stage) {
		super(x, y, stage);
		
		this.textureFile = textureFile;
		setSize(SIZE, SIZE);
		setOrigin(SIZE/2, SIZE/2);
		setBoundary();
	}
	
	/**
	 * Loads the proper texture based on surrounding walls
	 */
	public void loadTexture() {
		Point location = findConnectedTexture(type);
		texture = loadTexture(textureFile, ROWS, COLS, location.x, location.y);
		setAnimation(texture);
	}
	
	/**
	 * Finds the proper object texture to use from a sprite sheets based on the
	 * surrounding objects.
	 * 
	 * @return A point that corresponds to the proper sprite on a sprite sheet
	 */
	private Point findConnectedTexture(String type) {
		getNearbyObjects(type);
		int row = -1;
		int col = -1;
		
		int touching = 0;
		if(checkSide(T)) {touching++;}
		if(checkSide(R)) {touching++;}
		if(checkSide(B)) {touching++;}
		if(checkSide(L)) {touching++;}
		
		switch(touching) {
		case 0:
			row = 0; col = 0;
			break;
		case 1:
			row = 1;
			if(checkSide(T)) {col = 0;}
			else if(checkSide(R)) {col = 1;}
			else if(checkSide(B)) {col = 2;}
			else if(checkSide(L)) {col = 3;}
			else {row = 3; col = 7;}
			break;
		case 2:
			if(checkSide(T) && checkSide(R)) {col = 0;}
			else if(checkSide(R) && checkSide(B)) {col = 1;}
			else if(checkSide(B) && checkSide(L)) {col = 2;}
			else if(checkSide(L) && checkSide(T)) {col = 3;}
			else if(checkSide(L) && checkSide(R)) {col = 2; row = 0;}
			else if(checkSide(B) && checkSide(T)) {col = 3; row = 0;}
			if(row == -1) {
				row = 2;
				switch(col) {
				case 0:
					if(!checkSide(TR)) {row++;}
					break;
				case 1:
					if(!checkSide(BR)) {row++;}
					break;
				case 2:
					if(!checkSide(BL)) {row++;}
					break;
				case 3:
					if(!checkSide(TL)) {row++;}
					break;
				default:
					row = 3; col = 7;
				}
			}
			break;
		case 3:
			row = 4;
			if(!checkSide(B)) {col = 0;}
			else if(!checkSide(L)) {col = 1;}
			else if(!checkSide(T)) {col = 2;}
			else if(!checkSide(R)) {col = 3;}
			switch(col) {
			case 0:
				if(!checkSide(TR)) {row++;}
				break;
			case 1:
				if(!checkSide(BR)) {row++;}
				break;
			case 2:
				if(!checkSide(BL)) {row++;}
				break;
			case 3:
				if(!checkSide(TL)) {row++;}
				break;
			default:
				row = 3; col = 7;
			}
			if(row != 3 || col != 7) {
				switch(col) {
				case 0:
					if(!checkSide(TL)) {row += 2;}
					break;
				case 1:
					if(!checkSide(TR)) {row += 2;}
					break;
				case 2:
					if(!checkSide(BR)) {row += 2;}
					break;
				case 3:
					if(!checkSide(BL)) {row += 2;}
					break;
				default:
					row = 3; col = 7;
				}
			}
			break;
		case 4:
			int emptyCorners = 0;
			if(!checkSide(TR)) {emptyCorners++;}
			if(!checkSide(BR)) {emptyCorners++;}
			if(!checkSide(BL)) {emptyCorners++;}
			if(!checkSide(TL)) {emptyCorners++;}
			switch(emptyCorners) {
			case 0:
				row = 0; col = 1;
				break;
			case 1:
				row = 0;
				if(!checkSide(TR)) {col = 4;}
				else if(!checkSide(BR)) {col = 5;}
				else if(!checkSide(BL)) {col = 6;}
				else if(!checkSide(TL)) {col = 7;}
				else {row = 3; col = 7;}
				break;
			case 2:
				row = 1;
				if(!checkSide(TR) && !checkSide(BR)) {col = 4;}
				else if(!checkSide(BR) && !checkSide(BL)) {col = 5;}
				else if(!checkSide(BL) && !checkSide(TL)) {col = 6;}
				else if(!checkSide(TL) && !checkSide(TR)) {col = 7;}
				else if(!checkSide(TR) && !checkSide(BL)) {row = 3; col = 5;}
				else if(!checkSide(TL) && !checkSide(BR)) {row = 3; col = 6;}
				else {row = 3; col = 7;}
				break;
			case 3:
				row = 2;
				if(checkSide(TL)) {col = 4;}
				else if(checkSide(TR)) {col = 5;}
				else if(checkSide(BR)) {col = 6;}
				else if(checkSide(BL)) {col = 7;}
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
	public void getNearbyObjects(String type) {
		nearObjects = new boolean[3][3];
		float[] boundaryVertices = getBoundary().getTransformedVertices();
		float size = getWidth();
		Polygon check;
		
		for(int i = 0; i < nearObjects.length; i++) {
			for(int j = 0; j < nearObjects[i].length; j++) {
				check = new Polygon(boundaryVertices);
				check.translate(size*(j - 1), size*(i - 1));
				nearObjects[i][j] = BaseActor.isActor(check,new String[]{type},
						getStage());
			}
		}
	}

	private boolean checkSide(Point check) {
		return nearObjects[check.x][check.y];
	}
}