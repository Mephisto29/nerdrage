package com.nerdrage.screens;

import com.badlogic.gdx.Screen;

/**
 * Abstract screen class to remove some unnecessary clutter from the other screen classes
 */
public abstract class AbstractScreen implements Screen {

	/**
	 * This method should control the drawing of stuff to the screen
	 */
	public abstract void render(float delta);

	/**
	 * This method should be used to create resources and initially
	 * set up the screen to what must be displayed to the user.
	 */
	public abstract void show();

	/*
	 * Methods which can optionally be overwritten
	 */
	
	@Override
	public void resize(int width, int height) {
	
	}
	
	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {

	}
	
}
