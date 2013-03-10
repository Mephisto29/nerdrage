package com.nerdrage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.nerdrage.Player;
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
	private CombatLayer combatLayer;
	private ControlLayer controlLayer;
	private Player player;
	
	public GameScreen () {
		
		player = new Player();
		
		gameLayer = new GameLayer();
		combatLayer = new CombatLayer(player);
		controlLayer = new ControlLayer();
		
		controlLayer.setReceiver (combatLayer);
	}
	
	@Override
	public void render(float delta) {
		
		if (Gdx.input.isTouched()) {
			// pass the input on to the control layer
			controlLayer.handleTouch(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		combatLayer.draw();
		controlLayer.draw();
	}
	
	@Override
	public void show() {
		
	}
}