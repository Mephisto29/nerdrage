package com.nerdrage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.nerdrage.layers.*;

/**
 * This will be the main screen in which the game takes place. The game screen will display a
 * control overlay and then multiple different game layers behind it. These layers will be switched in
 * and out as necessary.
 */
public class GameScreen extends AbstractScreen {
	
	/**
	 * Private instance variables. Will include the different layers to display as well as 
	 * information about the player and the current game state.
	 */
	private GameLayer gameLayer;
	private ControlLayer controlLayer;
	
	public GameScreen () {
		gameLayer = new GameLayer();
		controlLayer = new ControlLayer();
		controlLayer.setReceiver (gameLayer);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gameLayer.draw();
		controlLayer.draw();
	}
	
	@Override
	public void show() {
		
	}
}