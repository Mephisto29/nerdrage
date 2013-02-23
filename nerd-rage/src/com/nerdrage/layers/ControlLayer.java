package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

/**
 * This layer will be used to display the control overlay. It will accept user input and
 * relay these the user's key presses back to another layer. This layer can be switched to
 * redirect the control layer's commands to another layer. 
 */
public class ControlLayer extends AbstractLayer {

	/**
	 * Private instance variables
	 */
	private AbstractReceiverLayer receiver;
	private SpriteBatch batch; 
	private ShapeRenderer shapeRenderer;
	
	private Texture dPadTexture;
	private Sprite dPadSprite;
	
	
	/**
	 * Constructor which sets up a sprite batch to handle drawing
	 */
	public ControlLayer() {
		batch = new SpriteBatch ();
		shapeRenderer = new ShapeRenderer ();
		receiver = null;
		
		// create texture assets
		dPadTexture = new Texture (Gdx.files.internal("data/DPad.png"));
		dPadSprite = new Sprite (dPadTexture);
		dPadSprite.setPosition(5.0f, 5.0f);
	}
	
	/**
	 * Method to handle the drawing of the layer to the screen. The layer will be slightly 
	 * transparent and will have button controls for the user to interact with
	 */
	@Override
	public void draw() {
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		/*
		if (NerdRageGame.DEBUG) {
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1f, 0.6f, 0.6f, 0.7f);
			shapeRenderer.rect(200.0f, 200.0f, 32.0f, 32.0f);
			shapeRenderer.end();
		}
		*/
		
		batch.begin();
		// textures and sprites will be rendered here 
		dPadSprite.draw(batch, 0.7f);
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

}
