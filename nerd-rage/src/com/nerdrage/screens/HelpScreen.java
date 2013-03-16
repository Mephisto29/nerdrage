package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Initial screen which is displayed to the user. This will temporarily show the game's
 * logo before transitioning to the main menu.
 */
public class HelpScreen extends AbstractScreen {

	Rectangle back_rectangle;
	Texture bg_texture,back_texture;
	TextureRegion bg_texture_region,back_texture_region;
	Game game;
	SpriteBatch spritebatch;
	OrthographicCamera orthoCam;
	float button_width=150;
	float button_height=50;
	float screen_width=Gdx.graphics.getWidth();
	float screen_height=Gdx.graphics.getHeight();
	
	public HelpScreen(Game game){
		this.game=game;
		
		orthoCam=new OrthographicCamera(screen_width,screen_height);
		orthoCam.position.set(screen_width/2,screen_height/2,0);
		
		bg_texture=new Texture(Gdx.files.internal("data/background.png"));
		bg_texture_region=new TextureRegion(bg_texture,0,0,512,310);
		
		back_rectangle=new Rectangle(0,0,button_width,button_height);
		back_texture=new Texture(Gdx.files.internal("data/back_option.png"));
		back_texture_region=new TextureRegion(back_texture,0,0,256,85);
	}
	@Override
	public void show() {
		
		
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
		float inputx=Gdx.input.getX();
		float inputy=Gdx.input.getY();
		
		if(Gdx.input.justTouched()){
			System.out.println("input x: "+inputx+" input y:"+inputy);
			if(point_in_rectangle(back_rectangle,inputx,inputy)){
				System.out.println("Pressed back");
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(delta);
		orthoCam.update();
		
		spritebatch=new SpriteBatch();
		spritebatch.getProjectionMatrix().set(orthoCam.combined);
		spritebatch.begin();
		spritebatch.draw(bg_texture_region, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spritebatch.end();
		spritebatch.begin();
		spritebatch.draw(back_texture_region,0,screen_height-button_height,button_width,button_height);
		spritebatch.end();
	}
}
