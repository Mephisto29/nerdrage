package com.nerdrage.layers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private instance variables
	 */
	private SpriteBatch batch; 
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer() {
		batch = new SpriteBatch ();
	}
	
	/**
	 * Method to handle the drawing of the layer to the screen
	 */
	@Override
	public void draw() {
		
		batch.begin();
		batch.end();
	}
	
	/*
	 * Methods to handle different controls which are passed down from the control layer
	 */

	@Override
	public void upPressed() {
		
	}

	@Override
	public void downPressed() {
		
	}

	@Override
	public void leftPressed() {
		
	}

	@Override
	public void rightPressed() {
		
	}

	@Override
	public void xPressed() {
		
	}

	@Override
	public void yPressed() {
			
	}

	@Override
	public void startPressed() {
		
	}

}
