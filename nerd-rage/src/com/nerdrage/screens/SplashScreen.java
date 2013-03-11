package com.nerdrage.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sun.org.apache.bcel.internal.generic.FADD;

/**
 * Initial screen which is displayed to the user. This will temporarily show the game's
 * logo before transitioning to the main menu.
 */
public class SplashScreen extends AbstractScreen {

	private SpriteBatch spritebatch;
	private Texture splash_texture;
	private TextureRegion splash_texture_region;
	private Game game;
	private int image_width=800;
	private int image_height=480;
	
	private Stage stage;
	private Image splashImage;
	
	public SplashScreen(Game game){
		this.game=game;
		stage = new Stage();
	}
	
	float timer=0;
	
	@Override
	public void render(float delta) {

		stage.act( delta );

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
		
	}

	@Override
	public void show() {
		spritebatch=new SpriteBatch();
		
		//Load splash image
		splash_texture=new Texture(Gdx.files.internal("data/SplashScreen.png"));
		
		//set linear texture filter to help with the image stretching
		splash_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//create the texture region
		//image is 512x300 beginning at (0,0)
		splash_texture_region=new TextureRegion(splash_texture,0,0,image_width,image_height);
		
		splashImage = new Image(splash_texture_region);
	    splashImage.addAction(sequence( fadeIn(0.75f), delay(1.75f), fadeOut( 1.75f ),
	              new Action() {
	                  @Override
	                  public boolean act(float delta)
	                  {
	                      game.setScreen( new MainMenuScreen(game));
	                      return true;
	                  }
	              }));
		
	    stage.addActor(splashImage);
		
	}
}
