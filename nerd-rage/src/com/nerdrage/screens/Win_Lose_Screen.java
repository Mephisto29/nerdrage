package com.nerdrage.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.nerdrage.Player;
import com.sun.org.apache.bcel.internal.generic.FADD;

/**
 * Initial screen which is displayed to the user. This will temporarily show the game's
 * logo before transitioning to the main menu.
 */
public class Win_Lose_Screen extends AbstractScreen {

	private SpriteBatch spritebatch;
	private Texture splash_texture;
	private TextureRegion splash_texture_region;
	private Game game;
	private int image_width=800;
	private int image_height=480;
	
	private Label firstOptionLabel;
	private Label secondOptionLabel;
	private Label thirdOptionLabel;
	
	Player player;
	int days;
	private BitmapFont font;
	
	private Stage stage;
	private Image splashImage;
	public int win;
	
	public Win_Lose_Screen(Game game, int win, Player player){
		this.game=game;
		this.win = win;
		days = 0;
		
		this.player = player;

		spritebatch = new SpriteBatch ();
		
		font = new BitmapFont(Gdx.files.internal("fonts/nerd.fnt"), false);

		firstOptionLabel = new Label("".subSequence(0, 0), new LabelStyle(font, Color.WHITE));
		firstOptionLabel.setPosition(Gdx.graphics.getWidth() / 2.0f - 80.0f, 125f);
		firstOptionLabel.setAlignment(Align.center);
		firstOptionLabel.setWidth(160.0f);
		secondOptionLabel = new Label("".subSequence(0, 0), new LabelStyle(font, Color.WHITE));
		secondOptionLabel.setPosition(Gdx.graphics.getWidth() / 2.0f - 80.0f, 85f);
		secondOptionLabel.setAlignment(Align.center);
		secondOptionLabel.setWidth(160.0f);
		thirdOptionLabel = new Label("".subSequence(0, 0), new LabelStyle(font, Color.WHITE));
		thirdOptionLabel.setPosition(Gdx.graphics.getWidth() / 2.0f - 80.0f, 45f);
		thirdOptionLabel.setAlignment(Align.center);
		thirdOptionLabel.setWidth(160.0f);
		
		firstOptionLabel.setText("TimeBonus:    " +  days*1000);
		secondOptionLabel.setText("Level Score:  " + (player.getLevel()*300));
		thirdOptionLabel.setText("Total Score : " + (int)((player.getLevel()*300) + days*1000));
		
		

		font = new BitmapFont();
		stage = new Stage();
	}
	
	float timer=0;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spritebatch.begin();
		spritebatch.draw(splash_texture_region,0,0,image_width,image_height);
		
		stage.addActor(firstOptionLabel);
		stage.addActor(secondOptionLabel);
		stage.addActor(thirdOptionLabel);
		//font.draw(spritebatch, "Total Score : " + (int)((player.getLevel()*300) + days*1000), Gdx.graphics.getWidth() / 2.0f - 12, 125f);
		//font.draw(spritebatch, "Level Score:  " + (player.getLevel()*300), Gdx.graphics.getWidth() / 2.0f - 12, 175f);
		//font.draw(spritebatch, "TimeBonus:    " +  days*1000, Gdx.graphics.getWidth() / 2.0f - 12, 150f);
		spritebatch.end();

		stage.act( delta );

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
        
        //Gdx.gl.glEnable(GL10.GL_BLEND);
        //Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		//batch.begin();
        
		
        
       // batch.end();
		
	}

	@Override
	public void show() {
		spritebatch=new SpriteBatch();
		
		//Load splash image
		if(win == 0)
			splash_texture=new Texture(Gdx.files.internal("data/WinScreen.png"));
		else
			splash_texture=new Texture(Gdx.files.internal("data/LoseScreen.png"));
		
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
	
	public void setDays(int days)
	{
		this.days = days;
	}
}
