package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.nerdrage.layers.*;
import com.nerdrage.*;

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
	private Game game;
	private boolean inCombat;

	public GameScreen (Game game) {
		gameLayer = new GameLayer(game);
		combatLayer = new CombatLayer();
		controlLayer = new ControlLayer();
		
		controlLayer.setReceiver (gameLayer);
		controlLayer.setStartButtonVisible(true);
	}
	
	@Override
	public void render(float delta) {
		
		// log frame rate
		if (NerdRageGame.DEBUG) {
			//NerdRageGame.fpsLogger.log();
		}
		
		if (Gdx.input.isTouched()) {
			// pass the input on to the control layer
			controlLayer.handleTouch(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (inCombat) {
			combatLayer.draw(delta);
		}
		else {
			gameLayer.draw(delta);
		}
		
		controlLayer.draw(delta);
	}
	
	@Override
	public void show() {
		
	}
}