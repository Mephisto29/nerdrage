package com.nerdrage.layers;

/**
 * Abstract class to ensure that a layer has methods to handle key presses
 */
public abstract class AbstractReceiverLayer extends AbstractLayer {
	
	public abstract void upPressed ();
	
	public abstract void downPressed ();
	
	public abstract void leftPressed ();
	
	public abstract void rightPressed ();
	
	public abstract void xPressed ();
	
	public abstract void yPressed ();
	
	public abstract void startPressed ();

}
