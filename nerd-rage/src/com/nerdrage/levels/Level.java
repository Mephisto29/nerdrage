package com.nerdrage.levels;

import java.util.HashMap;
import java.io.*;

import com.badlogic.gdx.files.FileHandle;

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
				
				GridBlock block = new GridBlock();
				
				if (indicator == '{') {
					// process the list of items
				}
				else if (indicator == '"') {
					// process the text
				}
				
				interactiveBlocks.put(key, block);
			}
		}
		catch (IOException e) {
			System.out.println ("Error reading in file");
		}
		
		// for testing purposes
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)  {
				System.out.print (levelGrid[y][x]);
			}
			System.out.println ();
		}
		
		System.out.println (interactiveBlocks);
	}
}
