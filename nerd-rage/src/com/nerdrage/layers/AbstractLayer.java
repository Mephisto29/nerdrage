package com.nerdrage.layers;

/**
 * Abstract layer class to ensure that each layer is able to receive input from the control layer
 * and to ensure that they have methods for drawing to the screen
 */
public abstract class AbstractLayer {
	
	/**
	 * Method which should handle the drawing of the layer onto the screen
	 * This should involve positioning components and drawing them as well as controlling
	 * the transparency of the layer.
	 */
	public abstract void draw ();

}
