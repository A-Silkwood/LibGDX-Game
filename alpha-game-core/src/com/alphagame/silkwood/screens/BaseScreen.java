package com.alphagame.silkwood.screens;

import com.alphagame.silkwood.actors.ActorLoc;
import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.ConObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class BaseScreen implements Screen, InputProcessor {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 576;
	public static final int SIZE = 32;
	
	protected static final String WALL = "wall";
	protected static final String FLOOR = "floor";
	
	//stages
	public Stage tStage;	//place terrain
	public Stage aStage;	//place characters, enemies, etc
	public Stage uiStage;	//place ui
	//tables
	protected Table uiTable;
	
	/**
	 * Creates a BaseScreen and its stages.
	 */
	public BaseScreen() {
		//create stages
		tStage = new Stage();
		aStage = new Stage();
		uiStage = new Stage();
		
		initialize();
	}
	
	protected void initializeTerrain() {
		System.out.println("Initializing Terrain");
		
		int inc = 1;
		for(Object conObject : BaseActor.getList(ConObject.class, tStage,
				ActorLoc.CON_OBJECT.toString())) {
			System.out.printf("Initializing Connected Object #%d%n", inc++);
			((ConObject)conObject).loadTexture();
		}
		
		System.out.println("Finished Terrain Initialization");
	}
	
	//fill
	//area
	protected void fill(String textureFile, boolean isWall) {
		fill(0, 0, WIDTH/SIZE - 1, HEIGHT/SIZE - 1, textureFile, isWall,
				new String[] {});
	}
	
	protected void fill(int xStart, int yStart, int xEnd, int yEnd,
			String textureFile, boolean isWall) {
		fill(xStart, yStart, xEnd, yEnd, textureFile, isWall, new String[] {});
	}
	
	protected void fill(String textureFile, boolean isWall, String[] avoidTypes) {
		fill(0, 0, WIDTH/SIZE - 1, HEIGHT/SIZE - 1, textureFile, isWall,
				avoidTypes);
	}
	
	protected void fill(int xStart, int yStart, int xEnd, int yEnd,
			String textureFile, boolean isWall, String[] avoidTypes) {
		if(xStart <= xEnd && yStart <= yEnd) {
			for(int row = yStart; row <= yEnd; row++) {
				fillRow(row, xStart, xEnd, textureFile, isWall, avoidTypes);
			}
		} else {
			System.out.println("Invalid start and end coordinates");
		}
	}
	
	//rows
	protected void fillRow(int row, String textureFile, boolean isWall) {
		fillRow(row, 0, HEIGHT/SIZE - 1, textureFile, isWall, new String[] {});
	}
	
	protected void fillRow(int row, int colStart, int colEnd,
			String textureFile, boolean isWall) {
		fillRow(row, colStart, colEnd, textureFile, isWall, new String[] {});
	}
	
	protected void fillRow(int row, String textureFile, boolean isWall,
			String[] avoidTypes) {
		fillRow(row, 0, HEIGHT/SIZE - 1, textureFile, isWall, avoidTypes);
	}
	
	protected void fillRow(int row, int colStart, int colEnd,
			String textureFile, boolean isWall, String[] avoidTypes) {
		if(colStart <= colEnd) {
			float[] checkVertices = {0, 0, SIZE, 0, SIZE, SIZE, SIZE, 0};
			Polygon check = new Polygon(checkVertices);
		
			for(int col = colStart; col <= colEnd; col++) {
				check.setPosition(col*SIZE, row*SIZE);
				if(avoidTypes.length == 0 || !BaseActor.isActor(check,
						avoidTypes, tStage)) {
					new ConObject(col, row, textureFile, isWall, tStage);
				}
			}
		} else {
			System.out.println("Invalid column start and end");
		}
	}
	
	//columns
	protected void fillColumn(int col, String textureFile, boolean isWall) {
		fillColumn(col, 0, HEIGHT/SIZE - 1, textureFile, isWall, new String[] {});
	}
	
	protected void fillColumn(int col, int rowStart, int rowEnd,
			String textureFile, boolean isWall) {
		fillColumn(col, rowStart, rowEnd, textureFile, isWall, new String[] {});
	}
	
	protected void fillColumn(int col, String textureFile, boolean isWall,
			String[] avoidTypes) {
		fillColumn(col, 0, HEIGHT/SIZE - 1, textureFile, isWall, avoidTypes);
	}
	
	protected void fillColumn(int col, int rowStart, int rowEnd,
			String textureFile, boolean isWall, String[] avoidTypes) {
		if(rowStart <= rowEnd) {
			float[] checkVertices = {0, 0, SIZE, 0, SIZE, SIZE, SIZE, 0};
			Polygon check = new Polygon(checkVertices);
		
			for(int row = rowStart; row <= rowEnd; row++) {
				check.setPosition(col*SIZE, row*SIZE);
				if(avoidTypes.length == 0 || !BaseActor.isActor(check,
						avoidTypes, tStage)) {
					new ConObject(col, row, textureFile, isWall, tStage);
				}
			}
		} else {
			System.out.println("Invalid row start and end");
		}
	}
	
	//screen implementation
	
	public abstract void initialize();
	
	public abstract void update(float delta);
	
	/**
	 * Renders the stages to the screen.
	 */
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
	
	/**
	 * Adds input processing to the stages.
	 */
	public void show() {
		InputMultiplexer input = (InputMultiplexer)Gdx.input.getInputProcessor();
		input.addProcessor(this);
		input.addProcessor(aStage);
		input.addProcessor(uiStage);
	}
	
	/**
	 * Removes input processing from the stages.
	 */
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