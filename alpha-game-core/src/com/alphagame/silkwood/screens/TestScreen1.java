package com.alphagame.silkwood.screens;

import com.alphagame.silkwood.actors.ActorLoc;
import com.alphagame.silkwood.actors.ConObject;
import com.alphagame.silkwood.actors.player.Player;

public class TestScreen1 extends BaseScreen {
	private static final String TEST_WALL = "testwall.png";
	private static final String TEST_FLOOR = "testfloor.png";
	
	public void resize(int width, int height) {}

	/**
	 * Initializes the screen and its actors
	 */
	public void initialize() {
		//3x3
		fill(13, 2, 15, 4, TEST_WALL, true);
		//3x3 w/ hole
		new ConObject(4, 4, TEST_FLOOR, false, tStage);
		fill(3, 3, 5, 5, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//x4 3x3 w/o 1 corner each
		new ConObject(15, 9, TEST_FLOOR, false, tStage);
		new ConObject(16, 7, TEST_FLOOR, false, tStage);
		new ConObject(17, 10, TEST_FLOOR, false, tStage);
		new ConObject(18, 8, TEST_FLOOR, false, tStage);
		fill(13, 7, 15, 9, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(16, 5, 18, 7, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(15, 10, 17, 12, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(18, 8, 20, 10, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//x4 2x3 w/o 1 corner each
		fillRow(6, 8, 10, TEST_FLOOR, false);
		fillColumn(9, 5, 7, TEST_FLOOR, false, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(6, 6, 8, 7, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(8, 3, 9, 5, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(9, 7, 10, 9, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(10, 5, 12, 6, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//x4 2x3 w/o 1 corner each w/ 1 in middle
		new ConObject(11, 13, TEST_WALL, true, tStage);
		fillRow(13, 10, 12, TEST_FLOOR, false, new String[] {ActorLoc.CON_OBJECT.toString()});
		fillColumn(11, 12, 14, TEST_FLOOR, false, new String[] {ActorLoc.CON_OBJECT.toString(),
				ActorLoc.CON_OBJECT.toString()});
		fill(8, 12, 10, 13, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(11, 10, 12, 12, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(10, 14, 11, 16, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(12, 13, 14, 14, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//x4 3x3 w/o 3 corners each
		new ConObject(2, 11, TEST_FLOOR, false, tStage);
		new ConObject(3, 12, TEST_FLOOR, false, tStage);
		new ConObject(3, 14, TEST_FLOOR, false, tStage);
		new ConObject(4, 11, TEST_FLOOR, false, tStage);
		new ConObject(4, 13, TEST_FLOOR, false, tStage);
		new ConObject(5, 10, TEST_FLOOR, false, tStage);
		new ConObject(5, 12, TEST_FLOOR, false, tStage);
		new ConObject(6, 13, TEST_FLOOR, false, tStage);
		fill(1, 13, 4, 14, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(5, 12, 6, 15, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(4, 10, 7, 11, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(2, 9, 3, 12, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//x2 3x3 w/o 2 corners each
		new ConObject(18, 13, TEST_FLOOR, false, tStage);
		new ConObject(20, 15, TEST_FLOOR, false, tStage);
		new ConObject(21, 13, TEST_FLOOR, false, tStage);
		new ConObject(23, 11, TEST_FLOOR, false, tStage);
		fill(18, 13, 20, 15, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(21, 11, 23, 13, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		//cross with 2x3
		new ConObject(24, 7, TEST_FLOOR, false, tStage);
		new ConObject(24, 9, TEST_FLOOR, false, tStage);
		new ConObject(26, 7, TEST_FLOOR, false, tStage);
		new ConObject(26, 9, TEST_FLOOR, false, tStage);
		fill(22, 7, 28, 9, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fill(24, 5, 26, 11, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString(),
				ActorLoc.CON_OBJECT.toString()});
		//cross with 1x3
		new ConObject(3, 21, TEST_FLOOR, false, tStage);
		fillRow(3, 20, 22, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fillColumn(21, 2, 4, TEST_WALL, true, new String[] {ActorLoc.CON_OBJECT.toString()});
		fillRow(5, 20, 22, TEST_WALL, true);
		fillRow(1, 20, 22, TEST_WALL, true);
		fillColumn(19, 2, 4, TEST_WALL, true);
		fillColumn(23, 2, 4, TEST_WALL, true);
		//ground fill
		fill(TEST_FLOOR, false, new String[] {ActorLoc.CON_OBJECT.toString(),ActorLoc.CON_OBJECT.toString()});
		
		initializeTerrain();
		
		
		new Player(256, 320, this.aStage, this.tStage);
	}
	
	/**
	 * Interactions specific to this screen
	 */
	public void update(float delta) {
		
	}

}