package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nerdrage.screens.AbstractScreen.State;

/**
 * Main menu screen. Will allow the user to choose between starting a new game, continuing a paused game, 
 * viewing a tutorial and viewing a high scores page 
 */
public class MainMenuScreen extends AbstractScreen {

	Game game;
	SpriteBatch spritebatch;
	Texture play_texture,help_texture,exit_texture,flames_texture;
	TextureRegion flames_texture_region;
	Sprite play_sprite,help_sprite,exit_sprite;
	float screen_width=Gdx.graphics.getWidth();
	float screen_height=Gdx.graphics.getHeight();
	float button_width=256;
	float button_height=64;
	float center=(screen_width-button_width)/2;
	float center_y=(screen_height-3*button_height)/2;
	OrthographicCamera camera;
	public enum GameState{PLAYED,UNPLAYED}
	Music theme_song;
	
	public MainMenuScreen(Game game){
		this.game=game;
		spritebatch=new SpriteBatch();
		theme_song=Gdx.audio.newMusic(Gdx.files.internal("audio/theme2.mp3"));
		theme_song.play();
		theme_song.setLooping(false);
		
		camera=new OrthographicCamera(screen_width, screen_height);
		camera.setToOrtho(false); //This points the y-axis upwards, instead of downwards
		print("creating new camera with width: "+screen_width+" and height: "+screen_height);
		play_texture=new Texture(Gdx.files.internal("buttons/play.png"));
		play_sprite=new Sprite(play_texture);
		play_sprite.setPosition(center, center_y+(2*64));
		
		help_texture=new Texture(Gdx.files.internal("buttons/help.png"));
		help_sprite=new Sprite(help_texture);
		help_sprite.setPosition(center, center_y+64);
		
		exit_texture=new Texture(Gdx.files.internal("buttons/exit.png"));
		exit_sprite=new Sprite(exit_texture);
		exit_sprite.setPosition(center, center_y);
		
		flames_texture=new Texture(Gdx.files.internal("menu/flames.png"));
		flames_texture_region=new TextureRegion(flames_texture, 0, 0, 512, 310);
		
		
	}
	
	public void print(String text){
		System.out.println(text);
	}
	
	public void update(float delta){
		
		camera.update();
		float inputx=Gdx.input.getX();
		float inputy=Gdx.input.getY();
		
		Vector3 touched=new Vector3(inputx,inputy,0);
//		print("projected: "+touched.x+","+touched.y);
		camera.unproject(touched);
//		print("UNprojected: "+touched.x+","+touched.y);
		Rectangle play_rectangle=new Rectangle(center,center_y+(button_height*2),button_width,button_height);
		Rectangle help_rectangle=new Rectangle(center,center_y+button_height,button_width,button_height);
		Rectangle exit_rectangle=new Rectangle(center,center_y,button_width,button_height);
		if(Gdx.input.justTouched()){
			if(point_in_rectangle(play_rectangle,touched.x, touched.y)){
				System.out.println("Pressed play");
				theme_song.pause();
				game.setScreen(new GameScreen(game));
			}
			else if(point_in_rectangle(help_rectangle,touched.x, touched.y)){
				System.out.println("Pressed help");
				game.setScreen(new HelpScreen(game));
			}
			else if(point_in_rectangle(exit_rectangle,touched.x, touched.y) || Gdx.input.isKeyPressed(Keys.BACK)){
				theme_song.dispose();
				System.out.println("Pressed exit");
				Gdx.app.exit();
			}
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(delta);
		
		spritebatch.begin();
		spritebatch.draw(flames_texture_region,0,0,screen_width,screen_height);
		play_sprite.draw(spritebatch);
		help_sprite.draw(spritebatch);
		exit_sprite.draw(spritebatch);
		spritebatch.end();
	}
	
	@Override
	public void show() {
		
	}
}