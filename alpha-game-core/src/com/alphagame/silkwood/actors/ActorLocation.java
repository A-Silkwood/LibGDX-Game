package com.alphagame.silkwood.actors;

public enum ActorLocation {
	BASE_ACTOR("com.alphagame.silkwood.actors.BaseActor"),
	CONNECTED_ACTOR("com.alphagame.silkwood.actors.ConnectedActor"),
	PLAYER("com.alphagame.silkwood.actors.player.Player"),
	ARROW("com.alphagame.silkwood.actors.player.Arrow");
	
	private String location;
	
	/** Initializes an ActorLocation(ActorLoc)
	 * <p>
	 * The location points to the file that contains the code for the actor
	 * @param loc Location of the actor in string format */
	private ActorLocation(String loc) {
		location = loc;
	}
	
	@Override
	public String toString() {
		return location;
	}
}