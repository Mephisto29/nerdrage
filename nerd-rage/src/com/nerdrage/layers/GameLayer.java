package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.Game;
import com.nerdrage.Player;
import com.nerdrage.levels.*;
import com.nerdrage.objects.*;
import com.nerdrage.screens.*;

public class GameLayer extends AbstractReceiverLayer {

	/**
	 * Private constants for use in the game
	 */
	public static final float WALK_ANIMATION_LENGTH = 0.25f;
	public static final float DAY_LENGTH_SECONDS = 600.0f;
	public static final int DAYS_SURVIVED_WITHOUT_WATER = 1;
	public static final int DAYS_SURVIVED_WITHOUT_FOOD = 2;
	
	/**
	 * Private instance variables
	 */
	private Level currentLevel;
	private Game game;
	private ControlLayer controlLayer;
	
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
	
	private boolean dialogVisible;
	private boolean removingDialog;
	
	private float time;
	private int days;
	
	private float hungerDecreaseTime;
	private float thirstDecreaseTime;
	
	private float hungerTime;
	private float thirstTime;
	
	private Player player;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer(Game game, Player player) {
		
		this.game = game;
		this.player = player;
		
		stage = new Stage(WIDTH, HEIGHT, true);
		
		loadLevel("house1");
		
		Texture nerd = new Texture(Gdx.files.internal("actors/nerd.png"));
		character = new Image(nerd);
		character.setPosition(WIDTH / 2 - 32.0f, HEIGHT / 2 - 32.0f);
		stage.addActor(character);
		
		dialogVisible = false;
		removingDialog = false;
		
		time = 0.0f;
		days = 0;
		
		hungerDecreaseTime = DAY_LENGTH_SECONDS * DAYS_SURVIVED_WITHOUT_FOOD / 100.0f;
		thirstDecreaseTime = DAY_LENGTH_SECONDS * DAYS_SURVIVED_WITHOUT_WATER / 100.0f;
		
		hungerTime = 0.0f;
		thirstTime = 0.0f;
	}
	
	/**
	 * A method to set a reference to the control layer which is in use
	 * 
	 * @param layer The control layer sending messages to this game layer
	 */
	public void setControlLayer (ControlLayer layer) {
		this.controlLayer = layer;
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
        
        time += delta;
        thirstTime += delta;
        hungerTime += delta;
        
        if (hungerTime / hungerDecreaseTime > 1) {
        	// decrease player hunger
        	hungerTime = hungerTime % hungerDecreaseTime;
        	player.adjustHunger(-1.0f);
        }
        
        if (thirstTime / thirstDecreaseTime > 1) {
        	// decrease player thirst
        	thirstTime = thirstTime % thirstDecreaseTime;
        	player.adjustThirst(-1.0f);
        }
        
        if ((time / DAY_LENGTH_SECONDS) > 1) {
        	days++;
        	time = time % DAY_LENGTH_SECONDS;
        }
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
		
		if (! dialogVisible) {
		
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
	}

	/**
	 * Method to handle the pressing of the down key
	 */
	@Override
	public void downPressed() {
		
		if (! dialogVisible) {
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
	}
	
	/**
	 * Method to handle the pressing of the left key
	 */
	@Override
	public void leftPressed() {
		
		if (! dialogVisible) {
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
	}

	/**
	 * Method to handle the pressing of the right key
	 */
	@Override
	public void rightPressed() {

		if (! dialogVisible) {
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
	}

	@Override
	public void xPressed() {
		
		if (! dialogVisible) {
		
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
				dialogBox.setPosition(WIDTH / 2.0f - 256.0f, -128.0f);
				stage.addActor(dialogBox);
				
				String text;
				
				if (g.isItemPickup()) {
					Item item = g.getItem();
					
					text = "You picked up item " + item.getId();
					// TODO: alter state of the player's inventory
				}
				else {
					text = g.getInteractionText();
				}
				
				BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/nerd.fnt"), false);
				dialogLabel = new Label(text.subSequence(0, text.length()), new LabelStyle(font, Color.BLACK));
				dialogLabel.setWidth(390.0f);
				dialogLabel.setHeight(108.0f);
				dialogLabel.setWrap(true);
				dialogLabel.setPosition(WIDTH / 2.0f - 195.0f, -118.0f);
				dialogLabel.setAlignment(Align.top, Align.left);
				stage.addActor(dialogLabel);
				
				MoveByAction action = new MoveByAction();
				action.setAmountY(133.0f);
				action.setDuration(0.2f);
				
				MoveByAction action1 = new MoveByAction();
				action1.setAmountY(133.0f);
				action1.setDuration(0.2f);
				
				dialogLabel.addAction(action);
				dialogBox.addAction(action1);
				
				dialogVisible = true;
				if (controlLayer != null) {
					controlLayer.setStartButtonVisible(false);
				}
			}
		}
		else {
			
			synchronized(this) {
				
				if (! removingDialog) {
			
					MoveByAction action = new MoveByAction();
					action.setAmountY(-133.0f);
					action.setDuration(0.2f);
					
					MoveByAction action1 = new MoveByAction();
					action1.setAmountY(-133.0f);
					action1.setDuration(0.2f);
					
					SequenceAction seq = new SequenceAction();
					seq.addAction(action);
					seq.addAction(new RunnableAction () {
						public void run () {
							synchronized (this) {
								controlLayer.setStartButtonVisible(true);
								dialogVisible = false;
								removingDialog = false;
							}
						}
					});
					
					dialogLabel.addAction(action1);
					dialogBox.addAction(seq);
					
					removingDialog = true;
				}
				
			}
		}
	}

	@Override
	public void yPressed() {
		
		if (dialogVisible) {

			synchronized(this) {
				
				if (! removingDialog) {
			
					MoveByAction action = new MoveByAction();
					action.setAmountY(-133.0f);
					action.setDuration(0.2f);
					
					MoveByAction action1 = new MoveByAction();
					action1.setAmountY(-133.0f);
					action1.setDuration(0.2f);
					
					SequenceAction seq = new SequenceAction();
					seq.addAction(action);
					seq.addAction(new RunnableAction () {
						public void run () {
							synchronized (this) {
								controlLayer.setStartButtonVisible(true);
								dialogVisible = false;
								removingDialog = false;
							}
						}
					});
					
					dialogLabel.addAction(action1);
					dialogBox.addAction(seq);
					
					removingDialog = true;
				}
			}
		}
	}

	@Override
	public void startPressed() {
		//game.setScreen(new ResumeMainMenuScreen(game));
	}

}
