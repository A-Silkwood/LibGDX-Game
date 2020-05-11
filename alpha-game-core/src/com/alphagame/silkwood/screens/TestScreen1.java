package com.alphagame.silkwood.screens;

import com.alphagame.silkwood.actors.BaseActor;
import com.alphagame.silkwood.actors.player.Player;
import com.alphagame.silkwood.connectedobjects.Floor;
import com.alphagame.silkwood.connectedobjects.Wall;

public class TestScreen1 extends BaseScreen {
	private static final String TEST_WALL = "testwall.png";
	private static final String TEST_FLOOR = "testfloor.png";
	
	public void resize(int width, int height) {}

	/**
	 * Initializes the screen and its actors
	 */
	public void initialize() {
		//3x3
		fill(13, 2, 15, 4, TEST_WALL, WALL);
		//3x3 w/ hole
		new Floor(4, 4, TEST_FLOOR, tStage);
		fill(3, 3, 5, 5, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//x4 3x3 w/o 1 corner each
		new Floor(15, 9, TEST_FLOOR, tStage);
		new Floor(16, 7, TEST_FLOOR, tStage);
		new Floor(17, 10, TEST_FLOOR, tStage);
		new Floor(18, 8, TEST_FLOOR, tStage);
		fill(13, 7, 15, 9, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(16, 5, 18, 7, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(15, 10, 17, 12, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(18, 8, 20, 10, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//x4 2x3 w/o 1 corner each
		fillRow(6, 8, 10, TEST_FLOOR, FLOOR);
		fillColumn(9, 5, 7, TEST_FLOOR, FLOOR, new String[] {BaseActor.FLOOR});
		fill(6, 6, 8, 7, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(8, 3, 9, 5, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(9, 7, 10, 9, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(10, 5, 12, 6, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//x4 2x3 w/o 1 corner each w/ 1 in middle
		new Wall(11, 13, TEST_WALL, tStage);
		fillRow(13, 10, 12, TEST_FLOOR, FLOOR, new String[] {BaseActor.WALL});
		fillColumn(11, 12, 14, TEST_FLOOR, FLOOR, new String[] {BaseActor.FLOOR,
				BaseActor.WALL});
		fill(8, 12, 10, 13, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(11, 10, 12, 12, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(10, 14, 11, 16, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(12, 13, 14, 14, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//x4 3x3 w/o 3 corners each
		new Floor(2, 11, TEST_FLOOR, tStage);
		new Floor(3, 12, TEST_FLOOR, tStage);
		new Floor(3, 14, TEST_FLOOR, tStage);
		new Floor(4, 11, TEST_FLOOR, tStage);
		new Floor(4, 13, TEST_FLOOR, tStage);
		new Floor(5, 10, TEST_FLOOR, tStage);
		new Floor(5, 12, TEST_FLOOR, tStage);
		new Floor(6, 13, TEST_FLOOR, tStage);
		fill(1, 13, 4, 14, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(5, 12, 6, 15, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(4, 10, 7, 11, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(2, 9, 3, 12, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//x2 3x3 w/o 2 corners each
		new Floor(18, 13, TEST_FLOOR, tStage);
		new Floor(20, 15, TEST_FLOOR, tStage);
		new Floor(21, 13, TEST_FLOOR, tStage);
		new Floor(23, 11, TEST_FLOOR, tStage);
		fill(18, 13, 20, 15, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(21, 11, 23, 13, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		//cross with 2x3
		new Floor(24, 7, TEST_FLOOR, tStage);
		new Floor(24, 9, TEST_FLOOR, tStage);
		new Floor(26, 7, TEST_FLOOR, tStage);
		new Floor(26, 9, TEST_FLOOR, tStage);
		fill(22, 7, 28, 9, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fill(24, 5, 26, 11, TEST_WALL, WALL, new String[] {BaseActor.FLOOR,
				BaseActor.WALL});
		//cross with 1x3
		new Floor(3, 21, TEST_FLOOR, tStage);
		fillRow(3, 20, 22, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fillColumn(21, 2, 4, TEST_WALL, WALL, new String[] {BaseActor.FLOOR});
		fillRow(5, 20, 22, TEST_WALL, WALL);
		fillRow(1, 20, 22, TEST_WALL, WALL);
		fillColumn(19, 2, 4, TEST_WALL, WALL);
		fillColumn(23, 2, 4, TEST_WALL, WALL);
		//ground fill
		fill(TEST_FLOOR, FLOOR, new String[] {BaseActor.FLOOR,BaseActor.WALL});
		
		initializeTerrain();
		
		
		new Player(256, 320, this.aStage, this.tStage);
	}
	
	/**
	 * Interactions specific to this screen
	 */
	public void update(float delta) {
		
	}

}