package com.alphagame.silkwood.actors;

public enum ActorLoc {
	BASE_ACTOR("com.alphagame.silkwood.actors.BaseActor"),
	CON_OBJECT("com.alphagame.silkwood.actors.ConObject"),
	//player
	PLAYER("com.alphagame.silkwood.actors.player.Player"),
	ARROW("com.alphagame.silkwood.actors.player.Arrow");
	
	private String location;
	
	private ActorLoc(String loc) {
		location = loc;
	}
	
	@Override
	public String toString() {
		return location;
	}
}