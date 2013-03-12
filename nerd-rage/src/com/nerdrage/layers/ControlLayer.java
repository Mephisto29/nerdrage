package com.nerdrage.layers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.nerdrage.Player;

/**
 * This layer will be used to display the control overlay. It will accept user input and
 * relay these the user's key presses back to another layer. This layer can be switched to
 * redirect the control layer's commands to another layer. 
 */
public class ControlLayer extends AbstractLayer {
	
	/**
	 * Static constants for use in the control screen
	 */
	public static final int THIRST_METER_BARS = 10;
	public static final int HUNGER_METER_BARS = 10;
	public static final float THIRST_START_POS_X = 50.0f;
	public static final float THIRST_START_POS_Y = 443.0f;
	public static final float HUNGER_START_POS_X = 734.0f;
	public static final float HUNGER_START_POS_Y = 443.0f;

	/**
	 * A private inner class which will handle the resetting of the input
	 * availability array. This will be used to ensure that the player can
	 * only press each input once every 0.25 seconds or so. Basically, we are
	 * just filtering out unneeded input
	 */
	private class InputDelayResetter implements Runnable {
		
		public final int toReset;
		
		public InputDelayResetter (int toReset) {
			this.toReset = toReset;
		}
		
		@Override
		public void run() {
			synchronized (this) {
				available[toReset] = true;
			}
		}
	}
	
	
	/**
	 * Private instance variables
	 */
	private AbstractReceiverLayer receiver;
	private SpriteBatch batch; 
	
	private Texture dPadTexture;
	private Sprite dPadSprite;
	
	private Texture xyButtonTexture;
	private Sprite xyButtonSprite;
	
	private Texture startButtonTexture;
	private Sprite startButtonSprite;
	
	private Texture topBarTexture;
	private Sprite topBarSprite;

	private boolean[] available;
	
	private boolean startButtonVisible;

	private Texture meterTexture;
	private Sprite[] thirstMeter;
	private Sprite[] hungerMeter; 
	
	private Texture hungerIconTexture;
	private Sprite hungerIcon;

	private Texture thirstIconTexture;
	private Sprite thirstIcon;
	
	private Player player;
	/**
	 * An enum to simplify the understanding of the code to check the state of the buttons
	 */
	public enum Button {
		
		LEFT(0), UP(1), DOWN(2), RIGHT(3), START(4), Y(5), X(6), NONE(7);	
	
		public final int id;
		
		Button (int id) {
			this.id = id;
		}
	}
	
	/**
	 * Public variables to control rate at which input is received
	 */
	public static final long RECEIVER_DELAY = 300;
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public ControlLayer(Player player) {
		
		this.player = player;
		
		batch = new SpriteBatch ();
		receiver = null;
		
		// create texture assets
		dPadTexture = new Texture (Gdx.files.internal("ui/DPadLarge.png"));
		dPadSprite = new Sprite (dPadTexture);
		dPadSprite.setPosition(5.0f, 5.0f);
		
		xyButtonTexture = new Texture (Gdx.files.internal("ui/XYButtonsLarge.png"));
		xyButtonSprite = new Sprite (xyButtonTexture);
		xyButtonSprite.setPosition(Gdx.graphics.getWidth() - 261.0f, 5.0f);
		
		startButtonTexture = new Texture (Gdx.files.internal("ui/StartButtonLarge.png"));
		startButtonSprite = new Sprite (startButtonTexture);
		startButtonSprite.setPosition(Gdx.graphics.getWidth() / 2.0f - 64.0f , 5.0f);
		
		topBarTexture = new Texture (Gdx.files.internal("ui/TopBarRepeat1.png"));
		topBarTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		topBarSprite = new Sprite (topBarTexture);
		topBarSprite.setPosition(0.0f, Gdx.graphics.getHeight() - 64.0f);
		topBarSprite.setSize(Gdx.graphics.getWidth(), 64.0f);
		
		meterTexture = new Texture(Gdx.files.internal("ui/MeterBar.png"));
		
		thirstMeter = new Sprite[THIRST_METER_BARS];
		hungerMeter = new Sprite[HUNGER_METER_BARS];
		
		for (int i =  0; i < THIRST_METER_BARS; i++) {
			Sprite s = new Sprite(meterTexture);
			s.setSize(16.0f, 32.0f);
			s.setPosition(THIRST_START_POS_X + (i * 16.0f), THIRST_START_POS_Y);
			thirstMeter[i] = s;
		}
		
		for (int i =  0; i < HUNGER_METER_BARS; i++) {
			Sprite s = new Sprite(meterTexture);
			s.setSize(16.0f, 32.0f);
			s.setPosition(HUNGER_START_POS_X - (i * 16.0f), HUNGER_START_POS_Y);
			hungerMeter[i] = s;
		}
		
		hungerIconTexture = new Texture(Gdx.files.internal("ui/Hunger.png"));
		hungerIcon = new Sprite(hungerIconTexture);
		hungerIcon.setPosition(HUNGER_START_POS_X + 21.0f, HUNGER_START_POS_Y);
		
		thirstIconTexture = new Texture(Gdx.files.internal("ui/Thirst.png"));
		thirstIcon = new Sprite(thirstIconTexture);
		thirstIcon.setPosition(THIRST_START_POS_X - 37.0f, THIRST_START_POS_Y);
		
		
		// initialise availability array
		available = new boolean[4];
		for (int i = 0; i < 4; i++) {
			available[i] = true;
		}
		
		startButtonVisible = true;
	}
	
	/**
	 * Method to handle the drawing of the layer to the screen. The layer will be slightly 
	 * transparent and will have button controls for the user to interact with
	 */
	@Override
	public void draw(float delta) {
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	
		batch.begin();
		
		dPadSprite.draw(batch, 0.6f);
		xyButtonSprite.draw(batch, 0.6f);
		topBarSprite.draw(batch, 0.8f);
		
		if (startButtonVisible) {
			startButtonSprite.draw(batch, 0.6f);
		}
		
		for (int i =  0; i < (player.getThirst() / THIRST_METER_BARS); i++) {
			thirstMeter[i].draw(batch, 0.8f);
		}
		
		for (int i =  0; i < (player.getHunger() / HUNGER_METER_BARS); i++) {
			hungerMeter[i].draw(batch, 0.8f);
		}
		
		hungerIcon.draw(batch, 0.8f);
		thirstIcon.draw(batch, 0.8f);
		batch.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	/**
	 * Method to update the receiver of control input
	 * 
	 * @param receiver The new layer to which the controls should be redirected
	 */
	public void setReceiver (AbstractReceiverLayer receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Method to choose whether or not to display the start button
	 * 
	 * @param visible
	 */
	public void setStartButtonVisible (boolean visible) {
		startButtonVisible = visible;
	}
	
	/**
	 * Method to handle touch input from the user. It will go through the possible input areas of the 
	 * screen and if any are being pressed the relevant message will be sent to the receiver
	 * 
	 * @param x The x coordinate of the touch on the screen
	 * @param y The y coordinate of the touch on the screen
	 */
	public void handleTouch (int x, int y) {
	
		Button button = Button.NONE;
		
		// only process touches in the bottom strip of the screen
		if (y <= 197 && y > 5) {
			
			// we then segment according to x values and test the possible buttons in the current x range
			if (x < 69 && x >= 5) {
				if (y >= 69 && y <= 133) {
					button = Button.LEFT;
				}
			}
			else if (x < 133) {
				if (y >= 133) {
					button = Button.UP;
				}
				else if (y <= 69) {
					button = Button.DOWN;
				}
			}
			else if (x <= 197) {
				if (y >= 69 && y <= 133) {
					button = Button.RIGHT;
				}	
			}
			else if (x >= 336 && x < 464) {
				if (y < 55) {
					button = Button.START;
				}
			}
			else if (x > 603 && x < 699) {
				if (y < 101) {
					button = Button.Y;
				}	
			}
			else if (x > 699 && x < 795) {
				if (y > 101) {
					button = Button.X;
				}	
			}
		}
		
		// check that we registered a button press
		if (button != Button.NONE) {
			
			synchronized (this) {
				
				int id = button.id;
				if (id >= 0 && id <= 3) {
					id = 0;
				}
				else {
					id -= 3;
				}
				
				// check that the button can be pressed again
				if (available [id]) {
					
					// send a message to the receiver
					switch (button) {
						case LEFT: {
							receiver.leftPressed();
							break;
						}
						case UP: {
							receiver.upPressed();
							break;
						}
						case DOWN: {
							receiver.downPressed();
							break;
						}
						case RIGHT: {
							receiver.rightPressed();
							break;
						}
						case START: {
							if (startButtonVisible) {
								receiver.startPressed();
							}
							break;
						}
						case Y: {
							receiver.yPressed();
							break;
						}
						case X: {
							receiver.xPressed();
							break;
						}
					}
					
					available[id] = false;
					
					// reset the availability of the button after the specified time
					InputDelayResetter resetter = new InputDelayResetter(id);
					ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
					executor.schedule(resetter, RECEIVER_DELAY, TimeUnit.MILLISECONDS);	
				}
			}
		}
	}
}
