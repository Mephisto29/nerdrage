package com.nerdrage.levels;

import java.util.HashMap;
import java.io.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nerdrage.NerdRageGame;
/**
 * Level class which will contain information about the area in which the user is. As well as
 * information about possible items which the user can pick up and interactions with world objects.
 */
public class Level {
	
	/**
	 * Private constants
	 */
	protected static int WIDTH = 800;
	protected static int HEIGHT = 480;
	
	/**
	 * Private instance variables
	 */
	protected char[][] levelGrid;
	protected HashMap<Character, GridBlock> interactiveBlocks;
	protected HashMap<String, TransitionBlock> transitionBlocks;

	protected int width;
	protected int height;
	
	protected int startingX;
	protected int startingY;
	
	protected FileHandle imageToLoad;
	protected Image levelImage;
	
	protected boolean isLoaded;
	protected boolean isTown;
	
	/**
	 * Method to load in a level from a file
	 * 
	 * @param dataFile The file to load in the level from
	 */
	public Level (FileHandle dataFile, FileHandle imageHandle) {
		
		BufferedReader input = null;
		interactiveBlocks = new HashMap<Character, GridBlock>();
		transitionBlocks = new HashMap<String, TransitionBlock>();
		
		input = new BufferedReader(dataFile.reader());
		
		try {
			String line = input.readLine();
			String[] arr = line.split(",");
			
			width = Integer.valueOf(arr[0]);
			height = Integer.valueOf(arr[1]);
		
			levelGrid = new char[height][width];
			
			// read in the characters for each grid block of the map
			for (int y = 0; y < height; y++) {
				line = input.readLine();
				
				System.out.println (line);
				
				for (int x = 0; x < width; x++)  {
					levelGrid[y][x] = line.charAt(x);
					if (line.charAt(x) == 'X') {
						startingX = x;
						startingY = y - 1;
						TransitionBlock t = new TransitionBlock(x, y);
						transitionBlocks.put("X:" + x + "Y:" + y, t);
					}
				}
			}
			
			int dictSize = Integer.valueOf(input.readLine());
			
			for (int i = 0; i < dictSize; i++) {
				
				line = input.readLine();
				char key = line.charAt(0);
				char indicator = line.charAt(2);
				
				GridBlock block = null;
				
				if (indicator == '{') {
					// process the list of items
					int posStart = line.indexOf('{');
					int posEnd = line.indexOf('}');
					
					int numItems = Integer.valueOf(line.substring(posStart + 1, posEnd));
					
					String[] itemIds = new String[numItems];
					float[] probs = new float[numItems];
					
					String[] raw = line.substring(posEnd + 1, line.length()).split(",");
					
					for (int j = 0; j < numItems; j++) {
						String[] split = raw[j].substring(1, raw[j].length() - 1).split(";");
						itemIds[j] = split[0];
						probs[j] = Float.valueOf(split[1]);
					}
					
					block = new GridBlock(itemIds, probs, null, true);
				}
				else if (indicator == '"') {
					// process the interaction text
					String interactionText = line.substring(3, line.length() - 1);
					block = new GridBlock(null, null, interactionText, false);
				}
				
				interactiveBlocks.put(key, block);
			}
		}
		catch (IOException e) {
			System.out.println ("Error reading in file");
		}
		
		if (NerdRageGame.DEBUG) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++)  {
					System.out.print (levelGrid[y][x]);
				}
				System.out.println ();
			}
			
			System.out.println (interactiveBlocks);
			System.out.println (transitionBlocks);
		}
		
		levelImage = null;
		imageToLoad = imageHandle;
		isLoaded = false;
	}
	
	/**
	 * A method to get the character at a specific position in the grid
	 * 
	 * @param x The x position to look up
	 * @param y The y position to look up
	 * @return The character at the position specified i.e. levelGrid[y][x]
	 */
	public char characterAtGridPosition (int x, int y) {
		return levelGrid[y][x];
	}
	
	/**
	 * Method to get the specific grid block for a character in the level grid 
	 * 
	 * @param c The character to lookup
	 * @return The GridBlock for that specific character
	 */
	public GridBlock getGridBlockForCharacter (char c) {
		return interactiveBlocks.get(Character.valueOf(c));
	}
	
	/**
	 * Method to get the starting position in the grid's x value
	 * 
	 * @return The starting position in the grid's x value
	 */
	public int getStartingX () {
		return startingX;
	}
	
	/**
	 * Method to get the starting position in the grid's y value
	 * 
	 * @return The starting position in the grid's y value
	 */
	public int getStartingY () {
		return startingY;
	}
	
	/**
	 * Method to get the width of the level
	 * 
	 * @return The width of the level
	 */
	public int getWidth () {
		System.out.println (width * 64);
		return width;
	}
	
	/**
	 * Method to return the height of the level
	 * 
	 * @return The height of the level
	 */
	public int getHeight () {
		System.out.println (height * 64);
		return height;
	}
	
	/**
	 * For cleaning up resources we only load the image when necessary
	 */
	public void load () {
		Texture levelTexture = new Texture(imageToLoad);
		TextureRegion region = new TextureRegion(levelTexture, 0, 0, width * 64, height * 64);
		levelImage = new Image(region);
		levelImage.setPosition(WIDTH / 2 - 32.0f - (startingX * 64.0f), HEIGHT / 2 - 32.0f - ((height - startingY - 1) * 64.0f));
		isLoaded = true;
	}
	
	/**
	 * For cleaning up resources we only load the image when necessary
	 */
	public void unload () {
		levelImage = null;
		isLoaded = false;
	}
	
	/**
	 * Method to get the image for the level
	 * 
	 * @return The loaded in image file as an Image actor
	 */
	public Image getImage () {
		
		if (isLoaded) {
			System.out.println ("image loaded is:" + imageToLoad.path());
			return levelImage;
		}
		
		load();
		
		System.out.println ("image to load is:" + imageToLoad.path());
		return levelImage;
	}
	
	/**
	 * If the Level should be considered as a house then we set it as such and give it the town as an argument
	 * 
	 * @param town The town in which the house resides
	 */
	public void setToHouse (Town town) {

		for (TransitionBlock t : transitionBlocks.values()) {
			t.setTransitionTo(town);
		}
		
		isTown = false;
	}
	
	/**
	 * Method to get the transition block at a specific block
	 * 
	 * @param x The x position of the block
	 * @param y The y position of the block
	 * @return The transition block for that block
	 */
	public TransitionBlock getTransitionBlockAtPosition (int x, int y) {
		return transitionBlocks.get("X:" + x + "Y:" + y);
	}
	
	/**
	 * Method to check if the level is a house
	 * 
	 * @return Whether or not the level is a house
	 */
	public boolean isHouse () {
		return ! isTown;
	}
	
	/**
	 * A method to get the probability of entering combat in the level
	 * 
	 * @return The probability of encountering a jock
	 */
	public float getCombatChance () {
		return 0.0f;
	}
}


