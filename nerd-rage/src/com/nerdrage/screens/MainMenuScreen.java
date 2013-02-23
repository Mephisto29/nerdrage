package com.nerdrage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**
 * Main menu screen. Will allow the user to choose between starting a new game, continuing a paused game, 
 * viewing a tutorial and viewing a high scores page 
 */
public class MainMenuScreen extends AbstractScreen {

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void show() {
		
	}
}