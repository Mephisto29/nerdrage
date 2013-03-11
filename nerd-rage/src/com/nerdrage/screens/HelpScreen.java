package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Initial screen which is displayed to the user. This will temporarily show the game's
 * logo before transitioning to the main menu.
 */
public class HelpScreen extends AbstractScreen {

	
	
	private Game game;

	
	public HelpScreen(Game game){
		this.game=game;
		
	}


	@Override
	public void show() {
		
		
	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
}
