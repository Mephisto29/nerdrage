package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Main menu screen. Will allow the user to choose between starting a new game, continuing a paused game, 
 * viewing a tutorial and viewing a high scores page 
 */
public class MainMenuScreen extends AbstractScreen {

	Game game;
	SpriteBatch spritebatch;
	OrthographicCamera orthoCam;
	Rectangle start_rectangle;
	Rectangle help_rectangle;
	Rectangle exit_rectangle;
	
	float screen_width=Gdx.graphics.getWidth();
	float screen_height=Gdx.graphics.getHeight();
	float button_width=150;
	float button_height=50;
	
	Texture bg_texture,start_texture;
	TextureRegion bg_texture_region,start_texture_region;
		
	int num_menu_options=3;
	
	TextureRegion[] texture_region;
	Texture[] texture;
	float start_pos_x=(screen_width-button_width)/2;
	float start_pos_y=(screen_height-(button_height))/2;
	
	public MainMenuScreen(Game game){
		this.game=game;
		orthoCam=new OrthographicCamera(screen_width,screen_height);
		orthoCam.position.set(screen_width/2,screen_height/2,0);
		spritebatch=new SpriteBatch();
		start_rectangle=new Rectangle(start_pos_x,start_pos_y,button_width,button_height);
		help_rectangle=new Rectangle(start_pos_x,start_pos_y+button_height,button_width,button_height);
		exit_rectangle=new Rectangle(start_pos_x,start_pos_y+(2*button_height),button_width,button_height);
		bg_texture=new Texture(Gdx.files.internal("data/background.png"));
		bg_texture_region=new TextureRegion(bg_texture,0,0,512,310);
		
		texture_region=new TextureRegion[3];
		texture=new Texture[3];
		
		for(int index=1;index<=num_menu_options;index++){
		texture[index-1]=new Texture(Gdx.files.internal("data/option"+index+".png"));
		texture_region[index-1]=new TextureRegion(texture[index-1],0,0,256,85);
		}
		
	}
	
	public boolean point_in_rectangle(Rectangle rect,float p_x,float p_y){
		float left=rect.getX();
		float right=rect.getWidth()+left;
		float top=rect.getY();
		float bottom=top+rect.getHeight();
		System.out.println("left:"+left+" right:"+right+" top:"+top+" bottom"+bottom);
		return p_x>=left && p_x<=right && p_y>=top && p_y<=bottom;
	}
	
	public void update(float delta){
		if(Gdx.input.justTouched()){
			System.out.println("input x: "+Gdx.input.getX()+" input y:"+Gdx.input.getY());
			if(point_in_rectangle(start_rectangle,Gdx.input.getX(),Gdx.input.getY())){
				System.out.println("Pressed start");
				game.setScreen(new GameScreen(game));
				return;
			}
			else if(point_in_rectangle(help_rectangle,Gdx.input.getX(),Gdx.input.getY())){
				System.out.println("Pressed help");
				game.setScreen(new HelpScreen(game));
				return;
			}
			else if(point_in_rectangle(exit_rectangle,Gdx.input.getX(),Gdx.input.getY())){
				System.out.println("Pressed exit");
				Gdx.app.exit();
				return;
			}
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(delta);
		orthoCam.update();
		//spritebatch.setProjectionMatrix(orthoCam.combined);
		spritebatch.begin();
		spritebatch.draw(bg_texture_region, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spritebatch.end();
		
		spritebatch.begin();
		spritebatch.draw(texture_region[0],start_rectangle.getX(),start_rectangle.getY(),button_width,button_height);
		spritebatch.end();
		
		spritebatch.begin();
		spritebatch.draw(texture_region[1],start_pos_x,start_pos_y-button_height,button_width,button_height);
		spritebatch.end();
		
		spritebatch.begin();
		spritebatch.draw(texture_region[2],start_pos_x,start_pos_y-(2*button_height),button_width,button_height);
		spritebatch.end();
	}
	
	@Override
	public void show() {
		
	}
}