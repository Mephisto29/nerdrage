package com.nerdrage.screens;

import sun.awt.windows.ThemeReader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.nerdrage.Player;
import com.nerdrage.layers.*;
import com.nerdrage.*;
import com.nerdrage.screens.*;

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
	private ItemLayer inventory;
	private Game game;
	public Player player;
	private boolean inCombat = false;
	private PauseMenuScreen pauseMenu; 

	public GameScreen (Game game) {
		
		player = new Player();
		
		inventory = new ItemLayer(player);
		
		
		gameLayer = new GameLayer(game, player, this, inventory);
		//combatLayer = new CombatLayer(player, this);
		inCombat = false;
		controlLayer = new ControlLayer(player);

		if (inCombat) {
			controlLayer.setReceiver (combatLayer);
			controlLayer.setStartButtonVisible(false);
		}
		else {
			controlLayer.setReceiver (gameLayer);
			controlLayer.setStartButtonVisible(true);
		}
		gameLayer.setControlLayer(controlLayer);
		pauseMenu = new PauseMenuScreen(gameLayer, controlLayer, inventory, game, this);
		
	}
	
	@Override
	public void render(float delta) {
		
		switch(game_state){
		case RUNNING:
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
			break;
		case PAUSED:
			game.setScreen(new PauseMenuScreen(gameLayer, controlLayer, inventory, game, this));
			//System.out.println("Game paused");
			break;
		}
		
		//override back button of phone to go back to pause menu
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			game_state=State.PAUSED;
			game.setScreen(new PauseMenuScreen(gameLayer, controlLayer, inventory, game, this));
		}
	}
		
	
	@Override
	public void show() {
		
	}
	
	public void enterCombat () {
		inCombat = true;
		combatLayer = new CombatLayer(player, this);
		controlLayer.setReceiver (combatLayer);
		controlLayer.setStartButtonVisible(false);
	}
	
	public void exitCombat () {
		inCombat = false;
		controlLayer.setReceiver (gameLayer);
		controlLayer.setStartButtonVisible(true);
	}
}