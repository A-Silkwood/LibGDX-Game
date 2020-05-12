package com.alphagame.silkwood.screens;

import java.awt.Point;

import com.alphagame.silkwood.actors.ActorLocation;
import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.ConnectedActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen, InputProcessor {
	/** size of the screen and tiles */
	public static final int WIDTH = 960;
	public static final int HEIGHT = 576;
	public static final int SIZE = 32;
	
	/** stages that make up the screen */
	public Stage tStage;	//terrain
	public Stage aStage;	//characters, enemies, etc
	public Stage uiStage;	//user interface
	
	/** Creates a screen and its stages */
	public BaseScreen() {
		tStage = new Stage();
		aStage = new Stage();
		uiStage = new Stage();
		
		initialize();
	}
	
	/** Goes through all connected actors within tStage and loads textures */
	protected void initializeTerrain() {
		System.out.printf("Initializing Terrain%n");
		
		int inc = 1;
		for(Object conObject : BaseActor.getList(ConnectedActor.class, tStage,
				ActorLocation.CONNECTED_ACTOR.toString())) {
			System.out.printf("Initializing Connected Object #%d%n", inc++);
			((ConnectedActor)conObject).loadTexture();
		}
		
		System.out.printf("Finished Terrain Initialization%n");
	}
	
	/** Different fill commands for row, column, and areas */
	
	/** Area fills */
	/** Fills the entire screen with connected actors
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fill(String textureFile, boolean isWall) {
		fill(new Point(0, 0), new Point(WIDTH/SIZE - 1, HEIGHT/SIZE - 1),
				textureFile, isWall, new String[] {});
	}
	
	/** Fills an area on the screen between two points with connected actors
	 * @param start Starting point
	 * @param end Ending point
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fill(Point start, Point end, String textureFile,
			boolean isWall) {
		fill(start, end, textureFile, isWall,
				new String[] {});
	}
	
	/** Fills the entire screen with connected actors and avoids placing on top
	 * of given actor types
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fill(String textureFile, boolean isWall,
			String[] avoidTypes) {
		fill(new Point(0, 0), new Point(WIDTH/SIZE - 1, HEIGHT/SIZE - 1),
				textureFile, isWall, avoidTypes);
	}
	
	/** Fills an area on the screen between two points with connected actors
	 * @param start Starting point
	 * @param end Ending point
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fill(Point start, Point end,	String textureFile,
			boolean isWall, String[] avoidTypes) {
		if(start.x <= end.x && start.y <= end.y) {
			for(int row = start.y; row <= end.y; row++) {
				fillRow(row, start.x, end.x, textureFile, isWall, avoidTypes);
			}
		} else {
			System.out.printf("Invalid start and end coordinates%n");
		}
	}
	
	/** Row Fills */
	/** Fills an entire row with connected actors
	 * @param row Row to fill with actors
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fillRow(int row, String textureFile, boolean isWall) {
		fillRow(row, 0, HEIGHT/SIZE - 1, textureFile, isWall, new String[] {});
	}
	
	/** Fills a row between two columns with connected actors
	 * @param row Row to fill with actors
	 * @param colStart Column to start from
	 * @param colEnd Column to end on
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fillRow(int row, int colStart, int colEnd,
			String textureFile, boolean isWall) {
		fillRow(row, colStart, colEnd, textureFile, isWall, new String[] {});
	}
	
	/** Fills an entire row with connected actors and avoids placing on top of
	 * given actor types
	 * @param row Row to fill with actors
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fillRow(int row, String textureFile, boolean isWall,
			String[] avoidTypes) {
		fillRow(row, 0, HEIGHT/SIZE - 1, textureFile, isWall, avoidTypes);
	}
	
	/** Fills a row between two columns with connected actors and avoid placing
	 * on top of given actor type
	 * @param row Row to fill with actors
	 * @param colStart Column to start from
	 * @param colEnd Column to end on
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fillRow(int row, int colStart, int colEnd,
			String textureFile, boolean isWall, String[] avoidTypes) {
		if(colStart <= colEnd) {
			float[] checkVertices = {0, 0, SIZE, 0, SIZE, SIZE, SIZE, 0};
			Polygon check = new Polygon(checkVertices);
		
			for(int col = colStart; col <= colEnd; col++) {
				check.setPosition(col*SIZE, row*SIZE);
				if(avoidTypes.length == 0 || !BaseActor.isActor(check,
						avoidTypes, tStage)) {
					new ConnectedActor(col, row, textureFile, isWall, tStage);
				}
			}
		} else {
			System.out.printf("Invalid column start and end%n");
		}
	}
	
	/** Column Fills */
	/** Fills an entire column with connected actors
	 * @param col Column to fill with actors
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fillColumn(int col, String textureFile, boolean isWall) {
		fillColumn(col, 0, HEIGHT/SIZE - 1, textureFile, isWall, new String[] {});
	}
	
	/** Fills a column with connected actors between two rows
	 * @param col Column to fill with actors
	 * @param rowStart Row to start from
	 * @param rowEnd Row to end on
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls */
	protected void fillColumn(int col, int rowStart, int rowEnd,
			String textureFile, boolean isWall) {
		fillColumn(col, rowStart, rowEnd, textureFile, isWall, new String[] {});
	}
	
	/** Fills an entire column with connected actors and avoids placing on top
	 * of given actor types
	 * @param col Column to fill with actors
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fillColumn(int col, String textureFile, boolean isWall,
			String[] avoidTypes) {
		fillColumn(col, 0, HEIGHT/SIZE - 1, textureFile, isWall, avoidTypes);
	}
	
	/** Fills a column between two rows with connected actors and avoid placing
	 * on top of given actor type
	 * @param col Column to fill with actors
	 * @param rowStart Row to start from
	 * @param rowEnd Row to end on
	 * @param textureFile Sprite sheet for actors
	 * @param isWall If the actors will be walls
	 * @param avoidTypes What actor types to not place actors on */
	protected void fillColumn(int col, int rowStart, int rowEnd,
			String textureFile, boolean isWall, String[] avoidTypes) {
		if(rowStart <= rowEnd) {
			float[] checkVertices = {0, 0, SIZE, 0, SIZE, SIZE, SIZE, 0};
			Polygon check = new Polygon(checkVertices);
		
			for(int row = rowStart; row <= rowEnd; row++) {
				check.setPosition(col*SIZE, row*SIZE);
				if(avoidTypes.length == 0 || !BaseActor.isActor(check,
						avoidTypes, tStage)) {
					new ConnectedActor(col, row, textureFile, isWall, tStage);
				}
			}
		} else {
			System.out.printf("Invalid row start and end%n");
		}
	}
	
	//screen implementation
	
	public abstract void initialize();
	
	public abstract void update(float delta);
	
	/** Renders the stages to the screen
	 * @param delta Time passed since last call */
	public void render(float delta) {
		tStage.act();
		aStage.act();
		uiStage.act();
		
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tStage.draw();
		aStage.draw();
		uiStage.draw();
	}
	
	public void pause() {}
	
	public void resume() {}
	
	public void dispose() {}
	
	/** Adds input processing to the stages */
	public void show() {
		InputMultiplexer input = (InputMultiplexer)Gdx.input.getInputProcessor();
		input.addProcessor(this);
		input.addProcessor(aStage);
		input.addProcessor(uiStage);
	}
	
	/** Removes input processing from the stages */
	public void hide() {
		InputMultiplexer input = (InputMultiplexer)Gdx.input.getInputProcessor();
		input.removeProcessor(this);
		input.removeProcessor(aStage);
		input.removeProcessor(uiStage);
	}
	
	//InputProcessor implementation
	
	public boolean keyDown(int keycode) {
		return false;
	}
	
	public boolean keyUp(int keycode) {
		return false;
	}
	
	public boolean keyTyped(char c) {
		return false;
	}
	
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	
	public boolean scrolled(int amount) {
		return false;
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}