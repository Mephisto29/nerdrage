package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nerdrage.levels.*;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private instance variables
	 */
	private SpriteBatch batch; 
	private Level currentLevel;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer() {
		batch = new SpriteBatch ();
		currentLevel = new Level(Gdx.files.internal("levels/house1.txt"));
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
		System.out.println ("U");
	}

	@Override
	public void downPressed() {
		System.out.println ("D");
	}

	@Override
	public void leftPressed() {
		System.out.println ("L");
	}

	@Override
	public void rightPressed() {
		System.out.println ("R");
	}

	@Override
	public void xPressed() {
		System.out.println ("X");
	}

	@Override
	public void yPressed() {
		System.out.println ("Y");	
	}

	@Override
	public void startPressed() {
		System.out.println ("S");
	}

}
