package com.alphagame.silkwood.screens;

import java.awt.Point;

import com.alphagame.silkwood.actors.ActorLocation;
import com.alphagame.silkwood.actors.ConnectedActor;
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
		fill(new Point(13, 2), new Point(15, 4), TEST_WALL, true);
		//3x3 w/ hole
		new ConnectedActor(4, 4, TEST_FLOOR, false, tStage);
		fill(new Point(3, 3), new Point(5, 5), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//x4 3x3 w/o 1 corner each
		new ConnectedActor(15, 9, TEST_FLOOR, false, tStage);
		new ConnectedActor(16, 7, TEST_FLOOR, false, tStage);
		new ConnectedActor(17, 10, TEST_FLOOR, false, tStage);
		new ConnectedActor(18, 8, TEST_FLOOR, false, tStage);
		fill(new Point(13, 7), new Point(15, 9), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(16, 5), new Point(18, 7), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(15, 10), new Point(17, 12), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(18, 8), new Point(20, 10), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//x4 2x3 w/o 1 corner each
		fillRow(6, 8, 10, TEST_FLOOR, false);
		fillColumn(9, 5, 7, TEST_FLOOR, false, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(6, 6), new Point(8, 7), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(8, 3), new Point(9, 5), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(9, 7), new Point(10, 9), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(10, 5), new Point(12, 6), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//x4 2x3 w/o 1 corner each w/ 1 in middle
		new ConnectedActor(11, 13, TEST_WALL, true, tStage);
		fillRow(13, 10, 12, TEST_FLOOR, false, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fillColumn(11, 12, 14, TEST_FLOOR, false, new String[] {ActorLocation.CONNECTED_ACTOR.toString(), ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(8, 12), new Point(10, 13), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(11, 10), new Point(12, 12), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(10, 14), new Point(11, 16), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(12, 13), new Point(14, 14), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//x4 3x3 w/o 3 corners each
		new ConnectedActor(2, 11, TEST_FLOOR, false, tStage);
		new ConnectedActor(3, 12, TEST_FLOOR, false, tStage);
		new ConnectedActor(3, 14, TEST_FLOOR, false, tStage);
		new ConnectedActor(4, 11, TEST_FLOOR, false, tStage);
		new ConnectedActor(4, 13, TEST_FLOOR, false, tStage);
		new ConnectedActor(5, 10, TEST_FLOOR, false, tStage);
		new ConnectedActor(5, 12, TEST_FLOOR, false, tStage);
		new ConnectedActor(6, 13, TEST_FLOOR, false, tStage);
		fill(new Point(1, 13), new Point(4, 14), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(5, 12), new Point(6, 15), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(4, 10), new Point(7, 11), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(2, 9), new Point(3, 12), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//x2 3x3 w/o 2 corners each
		new ConnectedActor(18, 13, TEST_FLOOR, false, tStage);
		new ConnectedActor(20, 15, TEST_FLOOR, false, tStage);
		new ConnectedActor(21, 13, TEST_FLOOR, false, tStage);
		new ConnectedActor(23, 11, TEST_FLOOR, false, tStage);
		fill(new Point(18, 13), new Point(20, 15), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(21, 11), new Point(23, 13), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		//cross with 2x3
		new ConnectedActor(24, 7, TEST_FLOOR, false, tStage);
		new ConnectedActor(24, 9, TEST_FLOOR, false, tStage);
		new ConnectedActor(26, 7, TEST_FLOOR, false, tStage);
		new ConnectedActor(26, 9, TEST_FLOOR, false, tStage);
		fill(new Point(22, 7), new Point(28, 9), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fill(new Point(24, 5), new Point(26, 11), TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString(), ActorLocation.CONNECTED_ACTOR.toString()});
		//cross with 1x3
		new ConnectedActor(3, 21, TEST_FLOOR, false, tStage);
		fillRow(3, 20, 22, TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fillColumn(21, 2, 4, TEST_WALL, true, new String[] {ActorLocation.CONNECTED_ACTOR.toString()});
		fillRow(5, 20, 22, TEST_WALL, true);
		fillRow(1, 20, 22, TEST_WALL, true);
		fillColumn(19, 2, 4, TEST_WALL, true);
		fillColumn(23, 2, 4, TEST_WALL, true);
		//ground fill
		fill(TEST_FLOOR, false, new String[] {ActorLocation.CONNECTED_ACTOR.toString(),ActorLocation.CONNECTED_ACTOR.toString()});
		
		initializeTerrain();
		
		
		new Player(1, 1, this.aStage, this.tStage);
	}
	
	/**
	 * Interactions specific to this screen
	 */
	public void update(float delta) {
		
	}

}