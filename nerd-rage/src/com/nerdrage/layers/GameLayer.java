package com.nerdrage.layers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nerdrage.levels.*;
import com.badlogic.gdx.Game;
import com.nerdrage.screens.*;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private constants for use in the game
	 */
	public static final float WALK_ANIMATION_LENGTH = 0.3f;
	
	/**
	 * Private instance variables
	 */
	private Level currentLevel;
	private Game game;
	
	private static int WIDTH = 800;
	private static int HEIGHT = 480;
	
	private Stage stage;
	private Actor character;
	private Actor level;
	
	private int positionX;
	private int positionY;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer(Game game) {

		
		this.game = game;
		
		stage = new Stage(WIDTH, HEIGHT, true);
		
		loadLevel("house1");
		
		Texture nerd = new Texture(Gdx.files.internal("actors/nerd.png"));
		character = new Image(nerd);
		character.setPosition(WIDTH / 2 - 32.0f, HEIGHT / 2 - 32.0f);
		stage.addActor(character);
	}
	
	/**
	 * Method to handle the drawing of the layer to the screen
	 */
	@Override
	public void draw (float delta) {
        // update the actors
        stage.act( delta );

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
	}
	
	
	private void loadLevel (String levelName) {
		
		currentLevel = new Level(Gdx.files.internal("levels/" + levelName + ".txt"));
		Texture levelTexture = new Texture(Gdx.files.internal("levels/images/" + levelName + ".png"));
		
		TextureRegion region = new TextureRegion(levelTexture, 0, 0, currentLevel.getWidth() * 64, currentLevel.getHeight() * 64);
		level = new Image(region);
		
		positionX = currentLevel.getStartingX();
		positionY = currentLevel.getStartingY();
		
		level.setPosition(WIDTH / 2 - 32.0f - (positionX * 64.0f), HEIGHT / 2 - 32.0f - ((currentLevel.getHeight() - positionY - 1) * 64.0f));
		
		stage.addActor(level);
	}
	
	
	
	/**
	 * Method to handle the pressing of the up key
	 */
	@Override
	public void upPressed() {
		
		// query the block above
		if (currentLevel.characterAtGridPosition(positionX, positionY - 1) == '0') {
		
			MoveByAction action = new MoveByAction();
			action.setAmountY(-64.0f);
			action.setDuration(WALK_ANIMATION_LENGTH);
			level.addAction(action);
		
			positionY--;
		}
	}

	/**
	 * Method to handle the pressing of the down key
	 */
	@Override
	public void downPressed() {
		
		// query the block below
		if (currentLevel.characterAtGridPosition(positionX, positionY + 1) == '0') {
			
			MoveByAction action = new MoveByAction();
			action.setAmountY(64.0f);
			action.setDuration(WALK_ANIMATION_LENGTH);
			level.addAction(action);
		
			positionY++;
		}
	}

	/**
	 * Method to handle the pressing of the left key
	 */
	@Override
	public void leftPressed() {
		
		// query the block to the left
		if (currentLevel.characterAtGridPosition(positionX - 1, positionY) == '0') {
		
			MoveByAction action = new MoveByAction();
			action.setAmountX(64.0f);
			action.setDuration(WALK_ANIMATION_LENGTH);
			level.addAction(action);
			
			positionX--;
		}
	}

	/**
	 * Method to handle the pressing of the right key
	 */
	@Override
	public void rightPressed() {
		// query the block to the left
		if (currentLevel.characterAtGridPosition(positionX + 1, positionY) == '0') {
			
			MoveByAction action = new MoveByAction();
			action.setAmountX(-64.0f);
			action.setDuration(WALK_ANIMATION_LENGTH);
			level.addAction(action);
			
			positionX++;
		}
	}

	@Override
	public void xPressed() {
	}

	@Override
	public void yPressed() {
	}

	@Override
	public void startPressed() {
		game.setScreen(new ResumeMainMenuScreen(game));
	}

}
