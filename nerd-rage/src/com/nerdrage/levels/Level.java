package com.nerdrage.levels;

import java.util.HashMap;
import java.io.*;

import com.badlogic.gdx.files.FileHandle;
import com.nerdrage.NerdRageGame;

/**
 * Level class which will contain information about the area in which the user is. As well as
 * information about possible items which the user can pick up and interactions with world objects.
 */
public class Level {
	
	/**
	 * Private instance variables
	 */
	private char[][] levelGrid;
	private HashMap<Character, GridBlock> interactiveBlocks;
	
	private int width;
	private int height;
	
	public Level (FileHandle dataFile) {
		
		BufferedReader input = null;
		interactiveBlocks = new HashMap<Character, GridBlock>();
		
		input = new BufferedReader(dataFile.reader());
		
		try {
			String line = input.readLine();
			String[] arr = line.split(",");
			
			width = Integer.valueOf(arr[0]);
			height = Integer.valueOf(arr[0]);
		
			levelGrid = new char[height][width];
			
			// read in the characters for each grid block of the map
			for (int y = 0; y < height; y++) {
				line = input.readLine();
				
				for (int x = 0; x < width; x++)  {
					levelGrid[y][x] = line.charAt(x);
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
		
		// for testing purposes
		if (NerdRageGame.DEBUG) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++)  {
					System.out.print (levelGrid[y][x]);
				}
				System.out.println ();
			}
			
			System.out.println (interactiveBlocks);
		}
	}
}
