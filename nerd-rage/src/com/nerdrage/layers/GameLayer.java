package com.nerdrage.layers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.nerdrage.NerdRageGame;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private instance variables
	 */
	private SpriteBatch batch; 
	private ShapeRenderer shapeRenderer;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer() {
		batch = new SpriteBatch ();
		shapeRenderer = new ShapeRenderer ();
	}
	
	/**
	 * Method to handle the drawing of the layer to the screen
	 */
	@Override
	public void draw() {
		
		if (NerdRageGame.DEBUG) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0, 1, 1, 1);
			shapeRenderer.circle(200.0f, 200.0f, 50.0f);
			shapeRenderer.end();
		}
		
		batch.begin();
		// textures and sprites will be rendered here 
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
