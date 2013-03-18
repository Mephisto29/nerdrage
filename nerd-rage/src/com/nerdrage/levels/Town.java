package com.nerdrage.levels;

import com.badlogic.gdx.files.FileHandle;

/**
 * A town class. This is much like the level class except it has some differences with remembering the state which the player was in.
 */
public class Town extends Level {
	
	/**
	 * Private instance variables used for storing the player's previous location
	 */
	private int currentX;
	private int currentY;
	
	public static final int STARTING_POSITION_X = 3;
	public static final int STARTING_POSITION_Y = 11;
	
	public static final int MAX_HOUSES = 3;
	
	/**
	 * Constructor which sets some additional fields
	 * 
	 * @param dataFile The file from which to construct the town
	 */
	public Town(FileHandle dataFile, FileHandle imageHandle) {
		super(dataFile, imageHandle);
		startingX = 1;
		startingY = 1;
		currentX = 1;
		currentY = 1;
		isTown = true;
		
		for (TransitionBlock t : transitionBlocks.values()) {
			
			int r = (int) (Math.floor(Math.random()*MAX_HOUSES) + 1);
			//System.out.println ("random: " + r);
			
			t.setTransitionTo("house" + r, true, this);
		}
	}
	
	/**
	 * Method to get the current X position
	 * 
	 * @return The X position the user is at
	 */
	public int getCurrentX () {
		return currentX;
	}
	
	/**
	 * Method to get the current Y position
	 * 
	 * @return The Y position the user is at
	 */
	public int getCurrentY () {
		return currentY;
	}
	
	/**
	 * Method to set the starting position of the player
	 * 
	 * @param posX The X position of the house which the player initially starts in
	 * @param posY The Y position of the house which the player initially starts in
	 * @param startingLevel The level/house which the player initially starts in
	 */
	public void setStartingHouse (int posX, int posY, Level startingLevel) {
		TransitionBlock t = getTransitionBlockAtPosition(posX, posY);
		t.setTransitionTo(startingLevel);
		startingX = posX;
		startingY = posY + 1;
		levelImage.setPosition(WIDTH / 2 - 32.0f - (startingX * 64.0f), HEIGHT / 2 - 32.0f - ((height - startingY - 1) * 64.0f));
	}
	
	/**
	 * Method to set the starting x position
	 * 
	 * @param x The starting x position
	 */
	public void setStartingX (int x) {
		startingX = x;
	}
	
	/**
	 * Method to set the starting Y position
	 * 
	 * @param y The starting y position
	 */
	public void setStartingY (int y) {
		startingY = y;
	}
	
	/**
	 * A method to get the probability of entering combat in the level
	 * 
	 * @return The probability of encountering a jock
	 */
	public float getCombatChance () {
		return 0.5f;
	}
}
