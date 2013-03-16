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

/**
 * Main menu screen. Will allow the user to choose between starting a new game, continuing a paused game, 
 * viewing a tutorial and viewing a high scores page 
 */
public class PauseMenuScreen extends AbstractScreen {

	Game game;
	SpriteBatch spritebatch;
	Texture inventory_texture,equipment_texture,back_to_menu_texture,flames_texture,resume_texture;
	TextureRegion flames_texture_region;
	Sprite inventory_sprite,equipment_sprite,back_to_menu_sprite,resume_sprite;
	float screen_width=Gdx.graphics.getWidth();
	float screen_height=Gdx.graphics.getHeight();
	float button_width=256;
	float button_height=64;
	float center=(screen_width-button_width)/2;
	float center_y=(screen_height-4*button_height)/2;
	OrthographicCamera camera;
	public enum GameState{PLAYED,UNPLAYED}
	public PauseMenuScreen(Game game){
		this.game=game;
		spritebatch=new SpriteBatch();
		camera=new OrthographicCamera(screen_width, screen_height);
		camera.setToOrtho(false); //This points the y-axis upwards, instead of downwards
		print("creating new camera with width: "+screen_width+" and height: "+screen_height);
		
		resume_texture=new Texture(Gdx.files.internal("buttons/continue.png"));
		resume_sprite=new Sprite(resume_texture);
		resume_sprite.setPosition(center, center_y+(3*64));
		
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
	public boolean point_in_rectangle(Rectangle rect,float p_x,float p_y){
		float left=rect.getX();
		float right=rect.getWidth()+left;
		float top=rect.getY();
		float bottom=top+rect.getHeight();
		print("left:"+left+" right:"+right+" top:"+top+" bottom"+bottom);
		print("touched: "+p_x+","+p_y);
		return p_x>=left && p_x<=right && p_y>=top && p_y<=bottom;
	}
	
	public void update(float delta){
		
		camera.update();
		float inputx=Gdx.input.getX();
		float inputy=Gdx.input.getY();
		
		Vector3 touched=new Vector3(inputx,inputy,0);
//		print("projected: "+touched.x+","+touched.y);
		camera.unproject(touched);
//		print("UNprojected: "+touched.x+","+touched.y);
		Rectangle continue_rectangle=new Rectangle(center,center_y+(button_height*3),button_width,button_height);
		Rectangle inventory_rectangle=new Rectangle(center,center_y+(button_height*2),button_width,button_height);
		Rectangle equipment_rectangle=new Rectangle(center,center_y+button_height,button_width,button_height);
		Rectangle back_to_menu_rectangle=new Rectangle(center,center_y,button_width,button_height);
		if(Gdx.input.justTouched()){
			if(point_in_rectangle(continue_rectangle,touched.x, touched.y)){
				System.out.println("Pressed continue");
				game.setScreen(new GameScreen(game));
			}
			else if(point_in_rectangle(inventory_rectangle,touched.x, touched.y)){
				System.out.println("Pressed inventory");
			}
			else if(point_in_rectangle(equipment_rectangle,touched.x, touched.y)){
				System.out.println("Pressed equipment");
			}
			else{
				System.out.println("Pressed back to menu");
				game.setScreen(new MainMenuScreen(game));
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
		inventory_sprite.draw(spritebatch);
		equipment_sprite.draw(spritebatch);
		back_to_menu_sprite.draw(spritebatch);
		spritebatch.end();
	}
	
	@Override
	public void show() {
		
	}
}