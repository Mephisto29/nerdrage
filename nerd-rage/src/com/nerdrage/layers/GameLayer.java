package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.nerdrage.levels.*;
import com.nerdrage.objects.Item;
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
	private Actor dialogBox;
	
	private int positionX;
	private int positionY;
	
	private enum Direction {LEFT, RIGHT, UP, DOWN};
	private Direction currentPlayerDirection;
	
	private Label dialogLabel;
	
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
		
		currentPlayerDirection = Direction.UP;
		
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

		currentPlayerDirection = Direction.UP;
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
		
		currentPlayerDirection = Direction.DOWN;
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
		
		currentPlayerDirection = Direction.LEFT;
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

		currentPlayerDirection = Direction.RIGHT;
	}

	@Override
	public void xPressed() {
		
		int x = positionX;
		int y = positionY;
		
		switch (currentPlayerDirection) {
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			case UP:
				y--;
				break;
			case DOWN:
				y++;
				break;
		}

		char c = currentLevel.characterAtGridPosition(x, y);
		GridBlock g = currentLevel.getGridBlockForCharacter(c);
		
		if (g != null) {
			
			Texture dialogTexture = new Texture(Gdx.files.internal("ui/DialogBox.png"));
			dialogBox = new Image(dialogTexture);
			dialogBox.setPosition(WIDTH / 2.0f - 256.0f, 5.0f);
			stage.addActor(dialogBox);
			
			String text;
			
			if (g.isItemPickup()) {
				Item item = g.getItem();
				
				text = "You picked up item " + item.getId();
				// TODO: show a dialog box and alter state of the player's inventory
				System.out.println ("You picked up item " + item);
			}
			else {
				text = g.getInteractionText();
				// TODO: show a dialog box
				System.out.println (g.getInteractionText());
			}
			
			BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/nerd.fnt"), false);
			dialogLabel = new Label(text.subSequence(0, text.length()), new LabelStyle(font, Color.BLACK));
			dialogLabel.setWidth(380.0f);
			dialogLabel.setHeight(108.0f);
			dialogLabel.setWrap(true);
			dialogLabel.setPosition(WIDTH / 2.0f - 190.0f, 15.0f);
			dialogLabel.setAlignment(Align.left, Align.top);
			stage.addActor(dialogLabel);
		}
	}

	@Override
	public void yPressed() {
	}

	@Override
	public void startPressed() {
		game.setScreen(new ResumeMainMenuScreen(game));
	}

}
