package com.alphagame.silkwood.actors;

import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class BaseActor extends Actor {
	/** variables that are used for movement, position, and collision */
	protected boolean moving;
	protected float speed;
	protected Vector2 direction;
	protected Float destination;
	protected boolean hasDestination;
	protected float totalTravel;
	protected float distance;
	protected Polygon boundary;
	
	/** variables for texture and animation */
	protected Animation<TextureRegion> animation;
	protected float elapsedTime;
	protected boolean animationPaused;
	
	/** Creates an actor object in its default configuration
	 * @param x	X-coordinate
	 * @param y	Y-coordinate
	 * @param stage	Stage to place the actor in */
	public BaseActor(float x, float y, Stage stage) {
		/** places the actor onto the stage */
		setPosition(x, y);
		stage.addActor(this);
		
		/** set the actor to its default variables */
		animation = null;
		elapsedTime = 0;
		animationPaused = false;
		speed = 0;
		moving = false;
		direction = new Vector2();
		destination = new Float();
		hasDestination = false;
	}
	
	/** Actions performed each tick
	 * <p>
	 * Updates animation and position
	 * @param delta Time since last call */
	public void act(float delta) {
		super.act(delta);
		
		if(!animationPaused) {
			elapsedTime += delta;
		}
		
		if(moving) {
			updatePosition(delta);
		}
	}
	
	/** Updates the position of the actor based on time passed since last call
	 * @param delta Time since last called */
	public void updatePosition(float delta) {
		if(moving) {
			float dx = speed * delta * direction.x;
			float dy = speed * delta * direction.y;
			if(hasDestination) {
				totalTravel += Math.sqrt((dx * dx) + (dy * dy));
				if(totalTravel <= distance) {
					moveBy(dx, dy);
				} else {
					setPosition(destination.x, destination.y);
				}
				stopAt(destination);
			}
		}
	}
	
	/** Sets the actor to move to a given point
	 * @param point Point to move to
	 * @param speed How fast the actor will move */
	public void moveTo(Float point, float speed) {
		hasDestination = true;
		destination = point;
		/** creates a unit vector in the direction of the point */
		direction.set(point.x - getX(), point.y - getY());
		distance = direction.len();
		direction.setLength(1);
		
		totalTravel = 0;
		this.speed = speed;
		
		moving = true;
	}
	
	/** Sets the actor to move in the direction of a point
	 * @param point Point to move towards
	 * @param speed How fast the actor will move */
	public void moveTowards(Float point, float speed) {
		hasDestination = false;
		/** creates a unit vector in the direction of the point */
		direction.set(point.x - getX(), point.y - getY());
		direction.setLength(1);
		
		this.speed = speed;
		
		moving = true;
	}
	
	/** Stops the player from moving once it gets to a point
	 * @param point Point to stop moving at */
	public void stopAt(Float point) {
		if(isAt(point)) {
			moving = false;
		}
	}
	
	/** Stops all movement of the actor */
	public void stop() {
		hasDestination = false;
		moving = false;
	}
	
	/** Draws the actor on the stage */
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		/** apply a color tint */
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		
		/** draws the animation */
		if(animation != null && isVisible()) {
			batch.draw(animation.getKeyFrame(elapsedTime), getX(),
					getY(), getOriginX(), getOriginY(), getWidth(),
					getHeight(), getScaleX(), getScaleY(), getRotation());
		}
	}
	
	/** Sets the animation of the actor
	 * @param animation Animation to set to the actor */
	public void setAnimation(Animation<TextureRegion> animation) {
		this.animation = animation;

		/** sets the size of the actor based on the animation first frame */
		TextureRegion textureRegion = animation.getKeyFrame(0);
		float width = textureRegion.getRegionWidth();
		float height = textureRegion.getRegionHeight();
		setSize(width, height);
		setOrigin(width/2, height/2);
	}
	
	/** Pause or unpause the animation
	 * @param pause If the animation should be paused or unpaused */
	public void setAnimationPaused(boolean pause) {
		animationPaused = pause;
	}
	
	public boolean isAnimationFinished() {
		return animation.isAnimationFinished(elapsedTime);
	}
	
	/** Loads an animation from multiple images
	 * @param fileNames Name of the images
	 * @param frameDuration How long each frame should last
	 * @param loop If the animation should loop
	 * @return Animation from the images */
	public static Animation<TextureRegion> loadAnimation(String[] fileNames,
			float frameDuration, boolean loop){
		int fileCount = fileNames.length;
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		/** adds images to the array */
		for(int i = 0; i < fileCount; i++) {
			Texture texture = new Texture(Gdx.files.internal(fileNames[i]));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			textureArray.add(new TextureRegion(texture));
		}
		
		/** creates and returns the finalized animation */
		return renderAnimation(textureArray, frameDuration, loop);
	}
	
	/** Loads an animation from a sprite sheet
	 * <p>
	 * The sprite sheet is split up and then turned into individual frames
	 * @param fileName Name of the sprite sheet
	 * @param rows How many rows are in the sheet
	 * @param columns How many columns are in the sheet
	 * @param frameDuration How long each frame should last
	 * @param loop If the animation should loop
	 * @return Animation from the sprite sheet */
	public static Animation<TextureRegion> loadAnimation(String fileName,
			int rows, int columns, float frameDuration, boolean loop){
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / columns;
		int frameHeight = texture.getHeight() / rows;
		
		/** splits up the sprite sheet and adds it to an array */
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth,
				frameHeight);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		/** adds images to an array */
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				textureArray.add(temp[r][c]);
			}
		}
		
		/** creates and returns the finalized animation */
		return renderAnimation(textureArray, frameDuration, loop);
	}
	
	/** Loads a single image as a texture
	 * @param fileName	Name of the image
	 * @return	Texture from the image */
	public static Animation<TextureRegion> loadTexture(String fileName) {
		String[] fileNames = new String[1];
		fileNames[0] = fileName;
		
		/** creates and returns the finalized texture */
		return loadAnimation(fileNames, 1, true);
	}
	
	/** Loads a single sprite from a sprite sheet as a texture
	 * @param fileName Name of the sprite sheet
	 * @param rows	How many rows are in the sheet
	 * @param columns How many columns are in the sheet
	 * @param row Row of the desired sprite
	 * @param column Column of the desired sprite
	 * @return Texture from the sprite sheet */
	public static Animation<TextureRegion> loadTexture(String fileName,
			int rows, int columns, int row, int column){
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / columns;
		int frameHeight = texture.getHeight() / rows;
		
		/** splits up the sprite sheet and adds it to an array */
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth,
				frameHeight);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		textureArray.add(temp[row][column]);
		
		/** creates and returns the finalized texture */
		return renderAnimation(textureArray, 1, true);
	}
	
	/** Renders an animation/texture from an array of images
	 * @param textureArray Images to be turned into an animation/texture
	 * @param frameDuration How long each frame should last
	 * @param loop If the animation should loop
	 * @return The texture or animation */
	private static Animation<TextureRegion> renderAnimation(
			Array<TextureRegion> textureArray, float frameDuration,
			boolean loop){
		/** turns array into an animation */
		Animation<TextureRegion> animation = new
				Animation<TextureRegion>(frameDuration, textureArray);
		
		if(loop) {
			animation.setPlayMode(Animation.PlayMode.LOOP);
		} else {
			animation.setPlayMode(Animation.PlayMode.NORMAL);
		}
		
		return animation;
	}
	
	@SuppressWarnings("unchecked")
	/** Returns a list of every actor in a stage
	 * <p>
	 * It only returns actors of the class given
	 * @param <actor> Type of actors to return
	 * @param actor Type of actors to return
	 * @param stage Stage to search for actors
	 * @param className Name of the actor class
	 * @return List of the requested actors */
	public static <actor> ArrayList<actor> getList(Class<? extends Actor> actor,
			Stage stage, String className){
		ArrayList<actor> list = new ArrayList<actor>();
		Class<? extends Actor> thisClass = getActorClass(className);
		
		/** Searches through the stage and only adds actors if they are the same
		 * class */
		for(Actor a : stage.getActors()) {
			if(thisClass.isInstance(a)) {
				list.add((actor)a);
			}
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	/** Retrieves the class from the class's name
	 * @param className Name of the class
	 * @return The class itself */
	public static Class<? extends Actor> getActorClass(String className) {
		Class<? extends Actor> thisClass = null;
		try {
			thisClass = (Class<? extends Actor>) Class.forName(className);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return thisClass;
	}
	
	/** Checks if an area contains certain types of actors
	 * @param check Area to check in
	 * @param types The types of actors to check for
	 * @param stage Stage to check in
	 * @return If an instance of the actor(s) were in the area */
	public static boolean isActor(Polygon check,String[] types,Stage stage) {
		for(String type : types) {
			for(Object object : BaseActor.getList(BaseActor.class,stage,type)){
				if(check.getBoundingRectangle().overlaps(((BaseActor) object).
						getBoundary().getBoundingRectangle())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** Sets a rectangular boundary around the actor */
	protected void setBoundary() {
		float width = getWidth();
		float height = getHeight();
		float[] vertices = {0,0,width,0,width,height,0,height};
		boundary = new Polygon(vertices);
	}
	
	/** Gets the boundary of the actor
	 * @return The actor's boundary */
	public Polygon getBoundary() {
		boundary.setPosition(getX(), getY());
		boundary.setOrigin(getOriginX(), getOriginY());
		boundary.setRotation(getRotation());
		boundary.setScale(getScaleX(), getScaleY());
		
		return boundary;
	}
	
	/** Tests if this actor's boundary overlaps with another actor's boundary
	 * @param other The actor to check against
	 * @return If the two actors overlap */
	public boolean overlaps(BaseActor other) {
		Polygon polygon1 = getBoundary();
		Polygon polygon2 = other.getBoundary();
		
		/** Checks a more simple boundaries of the actors overlap */
		if(!polygon1.getBoundingRectangle().
				overlaps(polygon2.getBoundingRectangle())) {
			return false;
		}
		
		return Intersector.overlapConvexPolygons(polygon1, polygon2);
	}
	
	/** Centers the actor at a given position
	 * @param x X-coordinate
	 * @param y Y-coordinate */
	public void centerAtPosition(float x, float y) {
		setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
	}
	
	/** Centers the actor on another actor
	 * @param other The actor to center onto */
	public void centerAtActor(BaseActor other) {
		centerAtPosition(other.getX() + (other.getWidth() / 2), other.getY() +
				(other.getHeight() / 2));
	}
	
	boolean isAt(Float point) {
		return point.equals(new Float(getX(),getY()));
	}
}