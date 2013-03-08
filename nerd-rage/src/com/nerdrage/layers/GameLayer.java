package com.nerdrage.layers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nerdrage.levels.*;
import com.badlogic.gdx.Game;
import com.nerdrage.screens.*;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private instance variables
	 */
	private Level currentLevel;
	private Game game;
	
	private static int WIDTH = 800;
	private static int HEIGHT = 480;
	
	private Stage stage;
	private Actor character;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer(Game game) {

		currentLevel = new Level(Gdx.files.internal("levels/house1.txt"));
		this.game = game;
		
		stage = new Stage(WIDTH, HEIGHT, true);
		
		Texture nerd = new Texture(Gdx.files.internal("data/Healthblock3.png"));
		character = new Image(nerd);
		character.setPosition(0.0f, 0.0f);
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
	
	/*
	 * Methods to handle different controls which are passed down from the control layer
	 */

	@Override
	public void upPressed() {
		MoveByAction action = new MoveByAction();
		action.setAmountY(64.0f);
		action.setDuration(0.5f);
		character.addAction(action);
	}

	@Override
	public void downPressed() {
		MoveByAction action = new MoveByAction();
		action.setAmountY(-64.0f);
		action.setDuration(0.5f);
		character.addAction(action);
	}

	@Override
	public void leftPressed() {
		MoveByAction action = new MoveByAction();
		action.setAmountX(-64.0f);
		action.setDuration(0.5f);
		character.addAction(action);
	}

	@Override
	public void rightPressed() {
		MoveByAction action = new MoveByAction();
		action.setAmountX(64.0f);
		action.setDuration(0.5f);
		character.addAction(action);
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
