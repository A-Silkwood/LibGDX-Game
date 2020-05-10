package com.alphagame.silkwood.screens;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.player.Player;
import com.alphagame.silkwood.connectedobjects.Wall;

public class TestScreen1 extends BaseScreen {
	private static final String TEST_WALL = "testwall.png";
	private static final String TEST_FLOOR = "testfloor.png";
	
	public void resize(int width, int height) {}

	/**
	 * Initializes the screen and its actors
	 */
	public void initialize() {
		//BaseActor bg = new BaseActor(0,0,this.gStage);
		//bg.setAnimation(BaseActor.loadTexture("bg.png"));
		
		//3x3 group of walls
		new Wall(1, 13, TEST_WALL, this.tStage);
		new Wall(1, 14, TEST_WALL, this.tStage);
		new Wall(2, 9, TEST_WALL, this.tStage);
		new Wall(2, 10, TEST_WALL, this.tStage);
		new Wall(2, 12, TEST_WALL, this.tStage);
		new Wall(2, 13, TEST_WALL, this.tStage);
		new Wall(2, 14, TEST_WALL, this.tStage);
		new Wall(3, 3, TEST_WALL, this.tStage);
		new Wall(3, 4, TEST_WALL, this.tStage);
		new Wall(3, 5, TEST_WALL, this.tStage);
		new Wall(3, 9, TEST_WALL, this.tStage);
		new Wall(3, 10, TEST_WALL, this.tStage);
		new Wall(3, 11, TEST_WALL, this.tStage);
		new Wall(3, 13, TEST_WALL, this.tStage);
		new Wall(4, 3, TEST_WALL, this.tStage);
		new Wall(4, 5, TEST_WALL, this.tStage);
		new Wall(4, 10, TEST_WALL, this.tStage);
		new Wall(4, 14, TEST_WALL, this.tStage);
		new Wall(5, 3, TEST_WALL, this.tStage);
		new Wall(5, 4, TEST_WALL, this.tStage);
		new Wall(5, 5, TEST_WALL, this.tStage);
		new Wall(5, 11, TEST_WALL, this.tStage);
		new Wall(5, 13, TEST_WALL, this.tStage);
		new Wall(5, 14, TEST_WALL, this.tStage);
		new Wall(5, 15, TEST_WALL, this.tStage);
		new Wall(6, 6, TEST_WALL, this.tStage);
		new Wall(6, 7, TEST_WALL, this.tStage);
		new Wall(6, 10, TEST_WALL, this.tStage);
		new Wall(6, 11, TEST_WALL, this.tStage);
		new Wall(6, 12, TEST_WALL, this.tStage);
		new Wall(6, 14, TEST_WALL, this.tStage);
		new Wall(6, 15, TEST_WALL, this.tStage);
		new Wall(7, 6, TEST_WALL, this.tStage);
		new Wall(7, 7, TEST_WALL, this.tStage);
		new Wall(7, 10, TEST_WALL, this.tStage);
		new Wall(7, 11, TEST_WALL, this.tStage);
		new Wall(8, 3, TEST_WALL, this.tStage);
		new Wall(8, 4, TEST_WALL, this.tStage);
		new Wall(8, 5, TEST_WALL, this.tStage);
		new Wall(8, 7, TEST_WALL, this.tStage);
		new Wall(8, 12, TEST_WALL, this.tStage);
		new Wall(8, 13, TEST_WALL, this.tStage);
		new Wall(9, 3, TEST_WALL, this.tStage);
		new Wall(9, 4, TEST_WALL, this.tStage);
		new Wall(9, 8, TEST_WALL, this.tStage);
		new Wall(9, 9, TEST_WALL, this.tStage);
		new Wall(9, 12, TEST_WALL, this.tStage);
		new Wall(9, 13, TEST_WALL, this.tStage);
		new Wall(10, 5, TEST_WALL, this.tStage);
		new Wall(10, 7, TEST_WALL, this.tStage);
		new Wall(10, 8, TEST_WALL, this.tStage);
		new Wall(10, 9, TEST_WALL, this.tStage);
		new Wall(10, 12, TEST_WALL, this.tStage);
		new Wall(10, 14, TEST_WALL, this.tStage);
		new Wall(10, 15, TEST_WALL, this.tStage);
		new Wall(10, 16, TEST_WALL, this.tStage);
		new Wall(11, 5, TEST_WALL, this.tStage);
		new Wall(11, 6, TEST_WALL, this.tStage);
		new Wall(11, 10, TEST_WALL, this.tStage);
		new Wall(11, 11, TEST_WALL, this.tStage);
		new Wall(11, 13, TEST_WALL, this.tStage);
		new Wall(11, 15, TEST_WALL, this.tStage);
		new Wall(11, 16, TEST_WALL, this.tStage);
		new Wall(12, 5, TEST_WALL, this.tStage);
		new Wall(12, 6, TEST_WALL, this.tStage);
		new Wall(12, 10, TEST_WALL, this.tStage);
		new Wall(12, 11, TEST_WALL, this.tStage);
		new Wall(12, 12, TEST_WALL, this.tStage);
		new Wall(12, 14, TEST_WALL, this.tStage);
		new Wall(13, 2, TEST_WALL, this.tStage);
		new Wall(13, 3, TEST_WALL, this.tStage);
		new Wall(13, 4, TEST_WALL, this.tStage);
		new Wall(13, 7, TEST_WALL, this.tStage);
		new Wall(13, 8, TEST_WALL, this.tStage);
		new Wall(13, 9, TEST_WALL, this.tStage);
		new Wall(13, 13, TEST_WALL, this.tStage);
		new Wall(13, 14, TEST_WALL, this.tStage);
		new Wall(14, 2, TEST_WALL, this.tStage);
		new Wall(14, 3, TEST_WALL, this.tStage);
		new Wall(14, 4, TEST_WALL, this.tStage);
		new Wall(14, 7, TEST_WALL, this.tStage);
		new Wall(14, 8, TEST_WALL, this.tStage);
		new Wall(14, 9, TEST_WALL, this.tStage);
		new Wall(14, 13, TEST_WALL, this.tStage);
		new Wall(14, 14, TEST_WALL, this.tStage);
		new Wall(15, 2, TEST_WALL, this.tStage);
		new Wall(15, 3, TEST_WALL, this.tStage);
		new Wall(15, 4, TEST_WALL, this.tStage);
		new Wall(15, 7, TEST_WALL, this.tStage);
		new Wall(15, 8, TEST_WALL, this.tStage);
		new Wall(15, 10, TEST_WALL, this.tStage);
		new Wall(15, 11, TEST_WALL, this.tStage);
		new Wall(15, 12, TEST_WALL, this.tStage);
		new Wall(16, 5, TEST_WALL, this.tStage);
		new Wall(16, 6, TEST_WALL, this.tStage);
		new Wall(16, 10, TEST_WALL, this.tStage);
		new Wall(16, 11, TEST_WALL, this.tStage);
		new Wall(16, 12, TEST_WALL, this.tStage);
		new Wall(17, 5, TEST_WALL, this.tStage);
		new Wall(17, 6, TEST_WALL, this.tStage);
		new Wall(17, 7, TEST_WALL, this.tStage);
		new Wall(17, 11, TEST_WALL, this.tStage);
		new Wall(17, 12, TEST_WALL, this.tStage);
		new Wall(18, 5, TEST_WALL, this.tStage);
		new Wall(18, 6, TEST_WALL, this.tStage);
		new Wall(18, 7, TEST_WALL, this.tStage);
		new Wall(18, 9, TEST_WALL, this.tStage);
		new Wall(18, 10, TEST_WALL, this.tStage);
		new Wall(18, 14, TEST_WALL, this.tStage);
		new Wall(18, 15, TEST_WALL, this.tStage);
		new Wall(19, 2, TEST_WALL, this.tStage);
		new Wall(19, 3, TEST_WALL, this.tStage);
		new Wall(19, 4, TEST_WALL, this.tStage);
		new Wall(19, 8, TEST_WALL, this.tStage);
		new Wall(19, 9, TEST_WALL, this.tStage);
		new Wall(19, 10, TEST_WALL, this.tStage);
		new Wall(19, 13, TEST_WALL, this.tStage);
		new Wall(19, 14, TEST_WALL, this.tStage);
		new Wall(19, 15, TEST_WALL, this.tStage);
		new Wall(20, 1, TEST_WALL, this.tStage);
		new Wall(20, 3, TEST_WALL, this.tStage);
		new Wall(20, 5, TEST_WALL, this.tStage);
		new Wall(20, 8, TEST_WALL, this.tStage);
		new Wall(20, 9, TEST_WALL, this.tStage);
		new Wall(20, 10, TEST_WALL, this.tStage);
		new Wall(20, 13, TEST_WALL, this.tStage);
		new Wall(20, 14, TEST_WALL, this.tStage);
		new Wall(21, 1, TEST_WALL, this.tStage);
		new Wall(21, 2, TEST_WALL, this.tStage);
		new Wall(21, 4, TEST_WALL, this.tStage);
		new Wall(21, 5, TEST_WALL, this.tStage);
		new Wall(21, 11, TEST_WALL, this.tStage);
		new Wall(21, 12, TEST_WALL, this.tStage);
		new Wall(22, 1, TEST_WALL, this.tStage);
		new Wall(22, 3, TEST_WALL, this.tStage);
		new Wall(22, 5, TEST_WALL, this.tStage);
		new Wall(22, 7, TEST_WALL, this.tStage);
		new Wall(22, 8, TEST_WALL, this.tStage);
		new Wall(22, 9, TEST_WALL, this.tStage);
		new Wall(22, 11, TEST_WALL, this.tStage);
		new Wall(22, 12, TEST_WALL, this.tStage);
		new Wall(22, 13, TEST_WALL, this.tStage);
		new Wall(23, 2, TEST_WALL, this.tStage);
		new Wall(23, 3, TEST_WALL, this.tStage);
		new Wall(23, 4, TEST_WALL, this.tStage);
		new Wall(23, 7, TEST_WALL, this.tStage);
		new Wall(23, 8, TEST_WALL, this.tStage);
		new Wall(23, 9, TEST_WALL, this.tStage);
		new Wall(23, 12, TEST_WALL, this.tStage);
		new Wall(23, 13, TEST_WALL, this.tStage);
		new Wall(24, 5, TEST_WALL, this.tStage);
		new Wall(24, 6, TEST_WALL, this.tStage);
		new Wall(24, 8, TEST_WALL, this.tStage);
		new Wall(24, 10, TEST_WALL, this.tStage);
		new Wall(24, 11, TEST_WALL, this.tStage);
		new Wall(25, 5, TEST_WALL, this.tStage);
		new Wall(25, 6, TEST_WALL, this.tStage);
		new Wall(25, 7, TEST_WALL, this.tStage);
		new Wall(25, 8, TEST_WALL, this.tStage);
		new Wall(25, 9, TEST_WALL, this.tStage);
		new Wall(25, 10, TEST_WALL, this.tStage);
		new Wall(25, 11, TEST_WALL, this.tStage);
		new Wall(26, 5, TEST_WALL, this.tStage);
		new Wall(26, 6, TEST_WALL, this.tStage);
		new Wall(26, 8, TEST_WALL, this.tStage);
		new Wall(26, 10, TEST_WALL, this.tStage);
		new Wall(26, 11, TEST_WALL, this.tStage);
		new Wall(27, 7, TEST_WALL, this.tStage);
		new Wall(27, 8, TEST_WALL, this.tStage);
		new Wall(27, 9, TEST_WALL, this.tStage);
		new Wall(28, 7, TEST_WALL, this.tStage);
		new Wall(28, 8, TEST_WALL, this.tStage);
		new Wall(28, 9, TEST_WALL, this.tStage);
		
		//fillColumn(3, TEST_FLOOR, FLOOR, new String[]{BaseActor.WALL});
		
		initializeTerrain();
		
		
		new Player(256, 320, this.aStage, this.tStage);
	}
	
	/**
	 * Interactions specific to this screen
	 */
	public void update(float delta) {
		
	}

}