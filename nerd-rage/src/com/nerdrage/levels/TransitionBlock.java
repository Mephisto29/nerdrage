package com.nerdrage.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class which will store the transitions at each "X" block
 */
public class TransitionBlock {
	
	/**
	 * Private instance variables
	 */
	private Level transitionTo;
	private String levelToLoad;
	private String imageToLoad;
	private boolean toHouse;
	private Town town;
	
	private int x;
	private int y;
	
	/**
	 * Constructor which accepts the level to transition to
	 * 
	 * @param transitionTo The level to transition to
	 */
	public TransitionBlock (Level transitionTo, int x, int y) {
		this.transitionTo = transitionTo;
		levelToLoad = null;
		imageToLoad = null;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor which accepts the name of the level to transition to
	 * 
	 * @param level The name of the level to transition to
	 */
	public TransitionBlock (String level, int x, int y) {
		levelToLoad = "levels/" + level + ".txt";
		imageToLoad = "levels/images/" + level + ".png";
		transitionTo = null;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor which accepts no arguments
	 */
	public TransitionBlock (int x, int y) {
		levelToLoad = null;
		imageToLoad = null;
		transitionTo = null;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method to get the level to transition to
	 * 
	 * @return The level which should be transitioned to
	 */
	public Level getLevelToTransitionTo () {
		
		if (transitionTo != null) {
			return transitionTo;
		}
		
		transitionTo = new Level(Gdx.files.internal(levelToLoad), Gdx.files.internal(imageToLoad));
		
		if (toHouse) {
			transitionTo.setToHouse(town);
		}
		
		return transitionTo;
	}
	
	/**
	 * Method to set the level to transition to
	 * 
	 * @param transition The level which should be transitioned to
	 */
	public void setTransitionTo (Level transition) {
		transitionTo = transition;
	}
	
	/**
	 * Method to set the level to transition to by name
	 * 
	 * @param transition The name of the level which should be transitioned to
	 */
	public void setTransitionTo (String level, boolean toHouse, Town town) {
		levelToLoad = "levels/" + level + ".txt";
		imageToLoad = "levels/images/" + level + ".png";
		transitionTo = null;
		this.toHouse = toHouse;
		this.town = town;
	}
	
	/**
	 * Method to get the y position of the transition block
	 * 
	 * @return the x position
	 */
	public int getX () {
		return x;
	}
	
	/**
	 * Method to get the y position of the transition block
	 * 
	 * @return the y position
	 */
	public int getY () {
		return y;
	}

}
