package com.nerdrage.levels;

import com.nerdrage.objects.*;

/**
 * A grid block in the level grid. Each grid block will contain information about the grid
 * such as items the player can find at this grid point and the interaction text which the
 * player sees when interacting with an object. 
 */
public class GridBlock {
	
	/**
	 * Private instance variables
	 */ 
	private String[] possibleItemIDs;
	private float[] probabilities;
	private String interactionText;
	private boolean isItemPickup;
	private boolean itemTaken;
	
	/**
	 * Constructor for the grid block class. Creates a grid block with an array of the number of items 
	 * that the player can pick up from this grid block as well as the interaction text seen by the player
	 * 
	 * @param itemIds The list of available item IDs from the block
	 * @param probabilities The probability of getting each of these items
	 * @param display The text to display to the user
	 * @param isItemPickup a boolean to state whether or not the grid block should be for item pickup or interaction
	 */
	public GridBlock (String[] itemIds, float[] probabilities, String interactionText, boolean isItemPickup) {

		if (itemIds != null && probabilities != null) {
			this.possibleItemIDs = new String[itemIds.length];
			this.probabilities = new float[itemIds.length];
		
			for (int i =0; i < itemIds.length; i++) {
				this.possibleItemIDs[i] = itemIds[i];
				this.probabilities[i] = probabilities[i];
			}
		}
		
		this.interactionText = interactionText;
		this.isItemPickup = isItemPickup;
		itemTaken = false;
	}
	
	/**
	 * Method to return get the interaction text for the block. i.e. what is displayed to
	 * the user when they try to interact with the block.
	 * 
	 * @return The interaction text for the grid block
	 */
	public String getInteractionText () {
		return interactionText;
	}
	
	/**
	 * Method to randomly select an item from the list of possible items based on the probability
	 * of picking up that item from that block.
	 * 
	 * @return The item which was randomly selected.
	 */
	public Item getItem () { 
		
		double random = Math.random();
		int itemNum = -1;
		double sum = 0;

		itemTaken = true;
		interactionText = "Shoot! Just my luck! There's nothing left.";
		
		while (sum <= random && itemNum < probabilities.length) {
			itemNum++;
			sum += probabilities[itemNum];
		}
		
		if (itemNum < probabilities.length) {
			return new Item (possibleItemIDs[itemNum]);
		}
		
		return null;
	}
	
	/**
	 * Method to check if the block gives out items to the player or is just interactable
	 * 
	 * @return Whether or not the player can pick up items from the block.
	 */
	public boolean isItemPickup () {
	
		if (itemTaken) {
			return false;
		}
		
		return isItemPickup;
	}

	/**
	 * For debugging purposes. Used to print a useful representation of the GridBlock to
	 * the output stream.
	 * 
	 * @return A string representation of the object
	 */
	public String toString () {
		
		if (isItemPickup) {
			String line = "GridBlock: " + possibleItemIDs.length + " items: ";
			for (int i = 0; i < possibleItemIDs.length; i++) {
				line += "[" + possibleItemIDs[i] + ", " + probabilities[i] + "]; ";
			}
			return line.trim();
		}
		
		return "GridBlock: " + interactionText;
	}
	
}
