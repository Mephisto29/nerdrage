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
public class SplashScreen extends AbstractScreen {

	
	private SpriteBatch spritebatch;
	private Texture splash_texture;
	private TextureRegion splash_texture_region;
	private Game game;
	private int image_width=512;
	private int image_height=300;
	
	public SplashScreen(Game game){
		this.game=game;
		
	}
	float timer=0;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spritebatch.begin();
		spritebatch.draw(splash_texture_region,0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spritebatch.end();
		
		timer+=delta;
		if(Gdx.input.isTouched()||(timer>=4)){
			game.setScreen (new MainMenuScreen (game));
		}
	}

	@Override
	public void show() {
		spritebatch=new SpriteBatch();
		
		//Load splash image
		splash_texture=new Texture(Gdx.files.internal("data/splash_image.png"));
		
		//set linear texture filter to help with the image stretching
		splash_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//create the texture region
		//image is 512x300 beginning at (0,0)
		splash_texture_region=new TextureRegion(splash_texture,0,0,image_width,image_height);
		
	}
}
