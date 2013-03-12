package com.nerdrage.objects;

/**
 * Item class which will interact with persistent storage to load in items. Each item will have certain
 * characteristics.
 */
public class Item {
	
	/**
	 * Private instance variables
	 */
	private String id;
	
	/**
	 * A constructor which will create an item by looking up its properties in a database or 
	 * something of the sort.
	 * 
	 * @param id The id of the item to create
	 */
	public Item (String id) {
		this.id = id;
	}
	
	/**
	 * Method to get the id of the item
	 * 
	 * @return The id of the item
	 */
	public String getId () {
		return id;
	}
}
