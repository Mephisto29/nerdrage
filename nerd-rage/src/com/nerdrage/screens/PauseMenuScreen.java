package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.nerdrage.Player;
import com.nerdrage.layers.ControlLayer;
import com.nerdrage.layers.GameLayer;
import com.nerdrage.layers.ItemLayer;

/**
 * Main menu screen. Will allow the user to choose between starting a new game, continuing a paused game, 
 * viewing a tutorial and viewing a high scores page 
 */
public class PauseMenuScreen extends AbstractScreen {

	Game game;
	
	ItemLayer inventory;
	GameScreen screen;
	Player player;
	
	GameLayer gameLayer;
	ControlLayer controlLayer;
	
	boolean inInvent = false;
	
	SpriteBatch spritebatch;
	Texture inventory_texture,equipment_texture,back_to_menu_texture,flames_texture,resume_texture,restart_texture;
	TextureRegion flames_texture_region;
	Sprite inventory_sprite,equipment_sprite,back_to_menu_sprite,resume_sprite,restart_sprite;
	float screen_width=Gdx.graphics.getWidth();
	float screen_height=Gdx.graphics.getHeight();
	float button_width=256;
	float button_height=64;
	float center=(screen_width-button_width)/2;
	float center_y=(screen_height-4*button_height)/2;
	OrthographicCamera camera;
	public enum GameState{PLAYED,UNPLAYED}
	
	public PauseMenuScreen(GameLayer gameLayer, ControlLayer controlLayer, ItemLayer inventory, Game game, GameScreen screen){
		
		this.game=game;
		this.gameLayer = gameLayer;
		this.controlLayer = controlLayer;
		this.inventory = inventory;
		this.screen = screen;
		
		spritebatch=new SpriteBatch();
		camera=new OrthographicCamera(screen_width, screen_height);
		camera.setToOrtho(false); //This points the y-axis upwards, instead of downwards
		print("creating new camera with width: "+screen_width+" and height: "+screen_height);
		
		resume_texture=new Texture(Gdx.files.internal("buttons/continue.png"));
		resume_sprite=new Sprite(resume_texture);
		resume_sprite.setPosition(center, center_y+(4*64));
		
		restart_texture=new Texture(Gdx.files.internal("buttons/restart.png"));
		restart_sprite=new Sprite(restart_texture);
		restart_sprite.setPosition(center, center_y+(3*64));
		
		inventory_texture=new Texture(Gdx.files.internal("buttons/inventory.png"));
		inventory_sprite=new Sprite(inventory_texture);
		inventory_sprite.setPosition(center, center_y+(2*64));
		
		equipment_texture=new Texture(Gdx.files.internal("buttons/equipment.png"));
		equipment_sprite=new Sprite(equipment_texture);
		equipment_sprite.setPosition(center, center_y+64);
		
		back_to_menu_texture=new Texture(Gdx.files.internal("buttons/backtomenu.png"));
		back_to_menu_sprite=new Sprite(back_to_menu_texture);
		back_to_menu_sprite.setPosition(center, center_y);
		
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
		Rectangle continue_rectangle=new Rectangle(center,center_y+(button_height*4),button_width,button_height);
		Rectangle restart_rectangle=new Rectangle(center,center_y+(button_height*3),button_width,button_height);
		Rectangle inventory_rectangle=new Rectangle(center,center_y+(button_height*2),button_width,button_height);
		Rectangle equipment_rectangle=new Rectangle(center,center_y+button_height,button_width,button_height);
		Rectangle back_to_menu_rectangle=new Rectangle(center,center_y,button_width,button_height);
		Rectangle MainMenurectangle = new Rectangle(center,center_y,button_width,button_height);
		if(Gdx.input.justTouched()){
			if(point_in_rectangle(continue_rectangle,touched.x, touched.y)){
				System.out.println("Pressed continue");
				//Handle state variables here
				game.setScreen(screen);
				controlLayer.setReceiver(gameLayer);
			}
			else if(point_in_rectangle(restart_rectangle,touched.x, touched.y)){
				game.setScreen(new GameScreen(game));
				System.out.println("Pressed restart");
			}
			else if(point_in_rectangle(inventory_rectangle,touched.x, touched.y)){
				System.out.println("Pressed inventory");
				
				inventory.setPauseMenu(this);
				game.setScreen(this);
				
				
				inInvent = true;
				
				controlLayer.setReceiver (inventory);
				controlLayer.setStartButtonVisible(false);
				
			}
			else if(point_in_rectangle(equipment_rectangle,touched.x, touched.y)){
				System.out.println("Pressed equipment");
			}
			else if(point_in_rectangle(MainMenurectangle,touched.x, touched.y)){
				System.out.println("Pressed back to menu");
				screen.stopMusic();
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}
	
	@Override
	public void render(float delta) {
		
		
		if (Gdx.input.isTouched()) {
			// pass the input on to the control layer
			controlLayer.handleTouch(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}
		if(inInvent)
		{
			//controlLayer.setReceiver (inventory);
			//controlLayer.setStartButtonVisible(false);
			
			inventory.draw(delta);
			controlLayer.draw(delta);
		}
		else
		{
			Gdx.gl.glClearColor(0, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			update(delta);
			spritebatch.begin();
			spritebatch.draw(flames_texture_region,0,0,screen_width,screen_height);
			resume_sprite.draw(spritebatch);
			restart_sprite.draw(spritebatch);
			inventory_sprite.draw(spritebatch);
			equipment_sprite.draw(spritebatch);
			back_to_menu_sprite.draw(spritebatch);
			spritebatch.end();
		}
		
		
	}
	
	@Override
	public void show() {
		
	}

	public void exitInventory() {
		
		inInvent = false;
		//controlLayer.setReceiver (gameLayer);
		controlLayer.setStartButtonVisible(false);
		// TODO Auto-generated method stub
		
	}
}