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
	private static final float UNIT_VECTOR_MAG = 1;
	
	//animation
	protected Animation<TextureRegion> animation;
	protected float elapsedTime;
	protected boolean animationPaused;
	
	//movement
	protected boolean moving;
	protected float speed;
	protected Vector2 direction;
	protected Float destination;
	protected boolean hasDestination;
	protected float totalTravel;
	protected float distance;
	
	//collision
	protected Polygon boundary;
	
	/**
	 * Constructor for an actor
	 * @param x	Starting x-coordinate
	 * @param y	Starting y-coordinate
	 * @param stage	Stage to place the actor into
	 */
	public BaseActor(float x, float y, Stage stage) {
		//Place the actor at the coordinates, in the stage
		setPosition(x, y);
		stage.addActor(this);
		
		//animation or textures
		animation = null;
		elapsedTime = 0;
		animationPaused = false;
		
		//movement
		speed = 0;
		moving = false;
		direction = new Vector2();
		destination = new Float();
		hasDestination = false;
	}
	
	/**
	 * Returns a list of all of a certain type of Actor from a given stage.
	 * @param <actor>
	 * 
	 * @param stage	Stage to pull BaseActors from
	 * @param className	Type of BaseActor to look for
	 * @return	A list of the BaseActors specified from the given Stage
	 */
	@SuppressWarnings("unchecked")
	public static <actor> ArrayList<actor> getList(Class<? extends Actor>actor,
			Stage stage, String className){
		ArrayList<actor> list = new ArrayList<actor>();
		
		//checks if className is a valid class
		Class<? extends Actor> thisClass = getActorClass(className);
		
		//searches through the Stage for all instances of the class
		for(Actor a : stage.getActors()) {
			if(thisClass.isInstance(a)) {
				list.add((actor)a);
			}
		}
		
		return list;
	}
	
	/**
	 * Returns the class of a given class name
	 * 
	 * @param className	Name of the class
	 * @return	The class itself
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Actor> getActorClass(String className) {
		//checks if className is a valid class
		Class<? extends Actor> thisClass = null;
		try {
			thisClass = (Class<? extends Actor>) Class.forName(className);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return thisClass;
	}
	
	/**
	 * Checks if the given area has an actor of given types
	 * 
	 * @param check Area to check for actors
	 * @param stage	Stage to look for walls
	 * @return	If there was a desired actor in the check area
	 */
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
	
	/**
	 * Draws the actor on the stage
	 */
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		//apply color tint effect
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		
		//draws the actor if it has an animation and is set to visible
		if(animation != null && isVisible()) {
			batch.draw(animation.getKeyFrame(elapsedTime), getX(),
					getY(), getOriginX(), getOriginY(), getWidth(),
					getHeight(), getScaleX(), getScaleY(), getRotation());
		}
	}
	
	/**
	 * Sets the animation for the actor to an array of texture regions
	 * 
	 * @param animation	Array of texture regions that make up an animation
	 */
	public void setAnimation(Animation<TextureRegion> animation) {
		//sets the animation to the given array
		this.animation = animation;
		//measurements of the animation are based off the first frame
		TextureRegion textureRegion = animation.getKeyFrame(0);
		float width = textureRegion.getRegionWidth();
		float height = textureRegion.getRegionHeight();
		setSize(width, height);
		setOrigin(width/2, height/2);
	}
	
	/**
	 * Set the animation to play or pause
	 * 
	 * @param pause	What to set the animation to
	 */
	public void setAnimationPaused(boolean pause) {
		animationPaused = pause;
	}
	
	/**
	 * Tests if the animation is finished
	 * 
	 * @return	Whether the animation is finished or not
	 */
	public boolean isAnimationFinished() {
		return animation.isAnimationFinished(elapsedTime);
	}
	
	/**
	 * Creates an array of texture regions from multiple images
	 * 
	 * @param fileNames	Array of image addresses
	 * @param frameDuration	Duration for each frame
	 * @param loop	If the animation loops
	 * @return	Array of texture regions from the given images
	 */
	public static Animation<TextureRegion> loadAnimation(String[] fileNames,
			float frameDuration, boolean loop){
		int fileCount = fileNames.length;
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		//adds each image into an array of texture regions
		for(int i = 0; i < fileCount; i++) {
			Texture texture = new Texture(Gdx.files.internal(fileNames[i]));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			textureArray.add(new TextureRegion(texture));
		}
		
		//returns the finished texture region array
		return renderAnimation(textureArray, frameDuration, loop);
	}
	
	/**
	 * Creates an array of texture regions from a single sprite sheet
	 * 
	 * @param fileName	Address of the sprite sheet
	 * @param rows	Rows in the sprite sheet
	 * @param columns	Columns in the sprite sheet
	 * @param frameDuration	Duration for each frame
	 * @param loop	If the animation loops
	 * @return	Array of texture regions from the given sprite sheet
	 */
	public static Animation<TextureRegion> loadAnimation(String fileName,
			int rows, int columns, float frameDuration, boolean loop){
		//receives sprite sheet
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//measurements for each sprite
		int frameWidth = texture.getWidth() / columns;
		int frameHeight = texture.getHeight() / rows;
		
		//splits the sprite in the sheet up into an array of texture regions
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth,
				frameHeight);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		//places the sprites into a proper texture region array
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				textureArray.add(temp[r][c]);
			}
		}
		
		//returns the finished animation
		return renderAnimation(textureArray, frameDuration, loop);
	}
	
	/**
	 * Creates an array of texture regions from a single sprite sheet
	 * 
	 * @param fileName	Address of the sprite sheet
	 * @param rows	Rows in the sprite sheet
	 * @param columns	Columns in the sprite sheet
	 * @param frameDuration	Duration for each frame
	 * @param loop	If the animation loops
	 * @return	Array of texture regions from the given sprite sheet
	 */
	public static Animation<TextureRegion> loadAnimation(String fileName,
			int rows, int columns, float rotation, float frameDuration,
			boolean loop){
		//receives sprite sheet
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//measurements for each sprite
		int frameWidth = texture.getWidth() / columns;
		int frameHeight = texture.getHeight() / rows;
		
		//splits the sprite in the sheet up into an array of texture regions
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth,
				frameHeight);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		
		//places the sprites into a proper texture region array
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				textureArray.add(temp[r][c]);
			}
		}
		
		//returns the finished animation
		return renderAnimation(textureArray, frameDuration, loop);
	}
	
	/**
	 * Creates an array with a single texture region of the image at the
	 * address of an image
	 * 
	 * @param fileName	Address of the image
	 * @return	An array with a texture region
	 */
	public static Animation<TextureRegion> loadTexture(String fileName) {
		//places a single texture into an array and treats it like an animation
		String[] fileNames = new String[1];
		fileNames[0] = fileName;
		return loadAnimation(fileNames, 1, true);
	}
	
	/**
	 * Creates an array with a single texture region of an image that came from
	 * a sprite sheet
	 * 
	 * @param fileName Address of the sprite sheet
	 * @param rows	Amount of rows in the sheet
	 * @param columns Amount of columns in the sheet
	 * @param row Row to pull the sprite from
	 * @param column Column to pull the sprite from
	 * @return An array with a texture region
	 */
	public static Animation<TextureRegion> loadTexture(String fileName,
			int rows, int columns, int row, int column){
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / columns;
		int frameHeight = texture.getHeight() / rows;
		
		//splits the sprite in the sheet up into an array of texture regions
		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth,
				frameHeight);
		
		Array<TextureRegion> textureArray = new Array<TextureRegion>();
		textureArray.add(temp[row][column]);
		
		return renderAnimation(textureArray, 1, true);
	}
	
	/**
	 * Mainly for simplification. Final steps for loading animations
	 * 
	 * @param textureArray	Array of texture regions to create the animation
	 * @param frameDuration	Duration of each frame
	 * @param loop	If the animation loops
	 * @return	Array of texture regions of each frame of the animation
	 */
	private static Animation<TextureRegion> renderAnimation(
			Array<TextureRegion> textureArray, float frameDuration,
			boolean loop){
		//turns the array of texture regions into an animation
		Animation<TextureRegion> animation = new
				Animation<TextureRegion>(frameDuration, textureArray);
		
		//sets animation to loop if it loops otherwise is set to end
		if(loop) {
			animation.setPlayMode(Animation.PlayMode.LOOP);
		} else {
			animation.setPlayMode(Animation.PlayMode.NORMAL);
		}
		
		return animation;
	}
	
	/**
	 * Sets a basic rectangular boundary around the BaseActor
	 */
	protected void setBoundary() {
		float width = getWidth();
		float height = getHeight();
		float[] vertices = {0,0,width,0,width,height,0,height};
		boundary = new Polygon(vertices);
	}
	
	/**
	 * Gets the boundary of the BaseActor
	 * 
	 * @return	The boundary of the BaseActor
	 */
	public Polygon getBoundary() {
		boundary.setPosition(getX(), getY());
		boundary.setOrigin(getOriginX(), getOriginY());
		boundary.setRotation(getRotation());
		boundary.setScale(getScaleX(), getScaleY());
		
		return boundary;
	}
	
	/**
	 * Tests if the boundary of BaseActor overlaps with the given BaseActor's
	 * boundary.
	 * 
	 * @param other	The other BaseActor to check with
	 * @return	If the two BaseActors have overlapping boundaries
	 */
	public boolean overlaps(BaseActor other) {
		Polygon polygon1 = getBoundary();
		Polygon polygon2 = other.getBoundary();
		
		//initial test for performance
		if(!polygon1.getBoundingRectangle().
				overlaps(polygon2.getBoundingRectangle())) {
			return false;
		}
		
		return Intersector.overlapConvexPolygons(polygon1, polygon2);
	}
	
	/**
	 * Center the actor at a given point
	 * 
	 * @param x	X-coordinate to center the actor onto
	 * @param y	Y-coordinate to center the actor onto
	 */
	public void centerAtPosition(float x, float y) {
		//sets the position
		setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
	}
	
	/**
	 * Center the actor onto another actor's coordinates
	 * 
	 * @param other	The actor to center onto
	 */
	public void centerAtActor(BaseActor other) {
		//centers to the coordinate in the center of the given actor
		centerAtPosition(other.getX() + (other.getWidth() / 2), other.getY() +
				(other.getHeight() / 2));
	}
	
	/**
	 * Actions to take each tick
	 */
	public void act(float delta) {
		super.act(delta);
		
		if(!animationPaused) {
			elapsedTime += delta;
		}
		
		if(moving) {
			updatePosition(delta);
		}
	}
	
	/**
	 * Moves the actor in the direction and speed that is previously set
	 * 
	 * @param delta	Change in time since last tick
	 */
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
	
	/**
	 * Sets the actor to move to a point at a certain speed
	 * 
	 * @param point	The point to go towards
	 * @param speed	The speed the actor moves at
	 */
	public void moveTo(Float point, float speed) {
		hasDestination = true;
		destination = point;
		//sets direction to a unit vector in the direction of the point
		direction.set(point.x - getX(), point.y - getY());
		distance = direction.len();	//gets the distance to travel
		direction.setLength(UNIT_VECTOR_MAG);
		
		totalTravel = 0;
		this.speed = speed;
		//sets the actor to move
		moving = true;
	}
	
	/**
	 * Sets the actor to move towards a point at a certain speed
	 * 
	 * @param point	The point to go towards
	 * @param speed	The speed the actor moves at
	 */
	public void moveTowards(Float point, float speed) {
		hasDestination = false;
		//sets direction to a unit vector in the direction of the point
		direction.set(point.x - getX(), point.y - getY());
		direction.setLength(UNIT_VECTOR_MAG);
		
		this.speed = speed;
		//sets the actor to move
		moving = true;
	}
	
	/**
	 * Stops moving once it is at a point
	 * 
	 * @param point	Point to stop at
	 */
	public void stopAt(Float point) {
		if(isAt(point)) {
			moving = false;
		}
	}
	
	/**
	 * Stops moving at its current position
	 */
	public void stop() {
		moving = false;
	}
	
	/**
	 * Tests if the actor is at the point
	 * 
	 * @param point	The point the actor is being tested to be at
	 * @return	Whether the actor is at the point
	 */
	public boolean isAt(Float point) {
		return point.equals(new Float(getX(),getY()));
	}
}