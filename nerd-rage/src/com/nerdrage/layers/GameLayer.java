package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nerdrage.levels.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nerdrage.screens.MainMenuScreen;
import com.nerdrage.screens.PauseMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	public static final float WALK_ANIMATION_LENGTH = 0.3f;
	public static final float DAY_LENGTH_SECONDS = 600.0f;
	public static final int DAYS_SURVIVED_WITHOUT_WATER = 1;
	public static final int DAYS_SURVIVED_WITHOUT_FOOD = 2;
	public static final int STARTING_POSITION_X = 3;
	public static final int STARTING_POSITION_Y = 11;
	public static final float COMBAT_CHANCE = 0.0f;
	
	/**
	 * Private instance variables
	 */
	private SpriteBatch batch; 
	private Sprite character;
	private Level currentLevel;
	private Town town;
	private Game game;
	private ControlLayer controlLayer;
	private GameScreen gameScreen;
	private ItemLayer inventory;
	
	private static int WIDTH = 800;
	private static int HEIGHT = 480;
	
	private Stage stage;
	private Actor level;
	private Actor dialogBox;
	private Actor townActor;
	
	private int positionX;
	private int positionY;
	
	public enum Direction {LEFT, RIGHT, UP, DOWN};
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
	
	private boolean viewingTown;
	
	private Music musicLoop;
	
	private Animation walkUpAnimation;
	private Animation walkDownAnimation;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private TextureRegion upStationary;
	private TextureRegion downStationary;
	private TextureRegion leftStationary;
	private TextureRegion rightStationary;
	
	float stateTime = 0.0f;
	
	private boolean walking;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public GameLayer(Game game, Player player, GameScreen gameScreen, ItemLayer inventory) {
		
		this.game = game;
		this.player = player;
		this.gameScreen = gameScreen;
		this.inventory = inventory;
		
		stage = new Stage(WIDTH, HEIGHT, true);
		
		town = new Town(Gdx.files.internal("levels/town.txt"), Gdx.files.internal("levels/images/town.png"));
		townActor = town.getImage();
		townActor.setVisible(false);
		stage.addActor(townActor);
		
		loadLevel("house1");
		town.setStartingHouse(STARTING_POSITION_X, STARTING_POSITION_Y, currentLevel);
		
		dialogVisible = false;
		removingDialog = false;
		
		time = 0.0f;
		days = 0;
		
		hungerDecreaseTime = DAY_LENGTH_SECONDS * DAYS_SURVIVED_WITHOUT_FOOD / 100.0f;
		thirstDecreaseTime = DAY_LENGTH_SECONDS * DAYS_SURVIVED_WITHOUT_WATER / 100.0f;
		
		hungerTime = 0.0f;
		thirstTime = 0.0f;
		
		viewingTown = false;
		
		musicLoop = Gdx.audio.newMusic(Gdx.files.internal("audio/town_loop.mp3"));

		Texture walkSheet = new Texture (Gdx.files.internal("actors/nerd_spritesheet.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 64, 64);
		
		TextureRegion[] upFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			upFrames[i] = tmp[0][i];
		}

		TextureRegion[] rightFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			rightFrames[i] = tmp[1][i];
		}
		
		TextureRegion[] downFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			downFrames[i] = tmp[2][i];
		}
		
		TextureRegion[] leftFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			leftFrames[i] = tmp[3][i];
		}

		walkUpAnimation = new Animation(WALK_ANIMATION_LENGTH / 4.0f, upFrames);
		walkDownAnimation = new Animation(WALK_ANIMATION_LENGTH / 4.0f, downFrames);
		walkLeftAnimation = new Animation(WALK_ANIMATION_LENGTH / 4.0f, leftFrames);
		walkRightAnimation = new Animation(WALK_ANIMATION_LENGTH / 4.0f, rightFrames);

		upStationary = upFrames[0];
		downStationary = downFrames[0];
		leftStationary = leftFrames[0];
		rightStationary = rightFrames[0];
		
		character = new Sprite(walkUpAnimation.getKeyFrame(0));
		character.setPosition(WIDTH / 2 - 32.0f, HEIGHT / 2 - 32.0f);
		
		batch = new SpriteBatch ();
		
		walking = false;
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
        
        if (! musicLoop.isPlaying()) {
        	
        	musicLoop.setLooping(true);
    		musicLoop.setVolume(0.8f);
    		musicLoop.play();
        }
        
        if (walking) {
        	stateTime += delta;
        }
        else {
        	stateTime = 0;
        }
        
        switch (currentPlayerDirection) {
        	case UP: {
        		TextureRegion currentFrame = walkUpAnimation.getKeyFrame(stateTime, true);
                character.setRegion(currentFrame);
                break;
        	}
        	case DOWN: {
        		TextureRegion currentFrame = walkDownAnimation.getKeyFrame(stateTime, true);
                character.setRegion(currentFrame);
                break;
        	}
        	case LEFT: {
        		TextureRegion currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
                character.setRegion(currentFrame);
                break;
        	}
        	case RIGHT: {
        		TextureRegion currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
                character.setRegion(currentFrame);
                break;
        	}
        }
        
        batch.begin();
        character.draw(batch);
        batch.end();
        
	}
	
	
	private void loadLevel (String levelName) {
		currentLevel = new Level(Gdx.files.internal("levels/" + levelName + ".txt"), Gdx.files.internal("levels/images/" + levelName + ".png"));
		currentPlayerDirection = Direction.UP;
		currentLevel.setToHouse(town);
		level = currentLevel.getImage();
	
		positionX = currentLevel.getStartingX();
		positionY = currentLevel.getStartingY();
		
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

				SequenceAction seq = new SequenceAction();
				seq.addAction(action);
				seq.addAction(new RunnableAction () {
					public void run () {
						
						synchronized (this) {
							walking = false;
							stateTime = 0;
						}
						
						double r = Math.random();
						if (r < COMBAT_CHANCE && currentLevel instanceof Town) {
							gameScreen.enterCombat();
						}
					}
				});
				
				level.addAction(seq);
				
				synchronized (this) {
					walking = true;
					stateTime = 0;
				}
				
			
				positionY--;
				
			}
			else if (currentLevel.characterAtGridPosition(positionX, positionY - 1) == 'X') {
				
				TransitionBlock t = currentLevel.getTransitionBlockAtPosition(positionX, positionY - 1);
				Level nextLevel = t.getLevelToTransitionTo();
				
				stage.clear();
				
				if (currentLevel.isHouse()) {
					currentLevel.unload();
				}
				else {
					town.setStartingX(positionX);
					town.setStartingY(positionY);
					townActor.setVisible(false);
				}
				
				currentLevel = nextLevel;
				level = currentLevel.getImage();
				level.setVisible(true);
				stage.addActor(level);
				
				positionX = currentLevel.getStartingX();
				positionY = currentLevel.getStartingY();
				
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

				SequenceAction seq = new SequenceAction();
				seq.addAction(action);
				seq.addAction(new RunnableAction () {
					public void run () {
						
						synchronized (this) {
							walking = false;
							stateTime = 0;
						}
						
						double r = Math.random();
						if (r < COMBAT_CHANCE && currentLevel instanceof Town) {
							gameScreen.enterCombat();
						}
					}
				});
				
				level.addAction(seq);
				
				synchronized (this) {
					walking = true;
					stateTime = 0;
				}
				
			
				positionY++;
			}
			else if (currentLevel.characterAtGridPosition(positionX, positionY + 1) == 'X') {
				
				TransitionBlock t = currentLevel.getTransitionBlockAtPosition(positionX, positionY + 1);
				Level nextLevel = t.getLevelToTransitionTo();
				
				stage.clear();
				
				if (currentLevel.isHouse()) {
					currentLevel.unload();
				}
				else {
					town.setStartingX(positionX);
					town.setStartingY(positionY);
					townActor.setVisible(false);
				}
				
				currentLevel = nextLevel;
				level = currentLevel.getImage();
				level.setVisible(true);
				stage.addActor(level);
				
				positionX = currentLevel.getStartingX();
				positionY = currentLevel.getStartingY();
				
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
				
				SequenceAction seq = new SequenceAction();
				seq.addAction(action);
				seq.addAction(new RunnableAction () {
					public void run () {
						synchronized(this) {
							walking = false;
							stateTime = 0;
						}
						
						double r = Math.random();
						if (r < COMBAT_CHANCE && currentLevel instanceof Town) {
							gameScreen.enterCombat();
						}
					}
				});
				
				level.addAction(seq);
				
				synchronized(this) {
					walking = true;
					stateTime = 0;
				}
				
				positionX--;
			}
			else if (currentLevel.characterAtGridPosition(positionX - 1, positionY) == 'X') {
				
				TransitionBlock t = currentLevel.getTransitionBlockAtPosition(positionX - 1, positionY);
				Level nextLevel = t.getLevelToTransitionTo();
				
				stage.clear();
				
				if (currentLevel.isHouse()) {
					currentLevel.unload();
				}
				else {
					town.setStartingX(positionX);
					town.setStartingY(positionY);
					townActor.setVisible(false);
				}
				
				currentLevel = nextLevel;
				level = currentLevel.getImage();
				level.setVisible(true);
				stage.addActor(level);
				
				positionX = currentLevel.getStartingX();
				positionY = currentLevel.getStartingY();
				
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
				
				SequenceAction seq = new SequenceAction();
				seq.addAction(action);
				seq.addAction(new RunnableAction () {
					public void run () {
						synchronized(this) {
							walking = false;
							stateTime = 0;
						}
						
						double r = Math.random();
						if (r < COMBAT_CHANCE && currentLevel instanceof Town) {
							gameScreen.enterCombat();
						}
					}
				});
				
				level.addAction(seq);
				
				synchronized(this) {
					walking = true;
					stateTime = 0;
				}
				
				positionX++;
			}
			else if (currentLevel.characterAtGridPosition(positionX + 1, positionY) == 'X') {
				
				TransitionBlock t = currentLevel.getTransitionBlockAtPosition(positionX + 1, positionY);
				Level nextLevel = t.getLevelToTransitionTo();
				
				stage.clear();
				
				if (currentLevel.isHouse()) {
					currentLevel.unload();
				}
				else {
					town.setStartingX(positionX);
					town.setStartingY(positionY);
					townActor.setVisible(false);
				}
				
				currentLevel = nextLevel;
				level = currentLevel.getImage();
				level.setVisible(true);
				stage.addActor(level);
				
				positionX = currentLevel.getStartingX();
				positionY = currentLevel.getStartingY();
				
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
					
					if (item != null) {
						text = "You picked up item " + item.getId();
						player.addItem("food");
					}
					else {
						text = "Meh... found nothing.";
					}
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

//<<<<<<< Updated upstream
		System.out.println ("S");
		//game.setScreen(new ResumeMainMenuScreen(game));
//=======
		game.setScreen(new PauseMenuScreen(this, controlLayer, inventory, game, gameScreen));
//>>>>>>> Stashed changes
	}

}
