package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstract screen class to remove some unnecessary clutter from the other screen classes
 */
public abstract class AbstractScreen implements Screen {

	public enum State{RUNNING,PAUSED}
	public State game_state=State.RUNNING;
	
	public boolean point_in_rectangle(Rectangle rect,float p_x,float p_y){
		float left=rect.getX();
		float right=rect.getWidth()+left;
		float top=rect.getY();
		float bottom=top+rect.getHeight();
		return p_x>=left && p_x<=right && p_y>=top && p_y<=bottom;
	}
	
	/**
	 * This method should control the drawing of stuff to the screen
	 */
	public abstract void render(float delta);

	/**
	 * This method should be used to create resources and initially
	 * set up the screen to what must be displayed to the user.
	 */
	public abstract void show();

	/*
	 * Methods which can optionally be overwritten
	 */
	
	@Override
	public void resize(int width, int height) {
	
	}
	
	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {

	}
	
}
