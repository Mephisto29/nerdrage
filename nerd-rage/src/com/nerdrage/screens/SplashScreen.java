package com.nerdrage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**
 * Initial screen which is displayed to the user. This will temporarily show the game's
 * logo before transitioning to the main menu.
 */
public class SplashScreen extends AbstractScreen {

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void show() {
		
	}
}
