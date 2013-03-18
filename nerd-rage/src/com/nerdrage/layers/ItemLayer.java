package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.nerdrage.Enemy;
import com.nerdrage.Player;
import com.nerdrage.layers.GameLayer.Direction;

import com.nerdrage.screens.GameScreen;
import com.nerdrage.screens.PauseMenuScreen;

public class ItemLayer extends AbstractReceiverLayer {

	private SpriteBatch batch; 

	public PauseMenuScreen pauseMenuScreen;

	public Player player;
	public Enemy enemy;

	private int positionx;
	private int positiony;
	public int itemx;
	public int itemy;
	public int position;

	private boolean useable;

	private Stage stage;
	private Actor dialogBox;
	private Label dialogLabel;
	private boolean dialogVisible;
	private String text = "";

	private Texture inventoryScreenTexture;
	private TextureRegion inventory_screen_texture_region;
	private Texture key1;
	private Texture key2;
	private Texture food;
	private Texture water;
	private Texture battery;

	private int image_width=512;
	private int image_height=310;

	private Sprite inventoryScreenSprite;
	private Sprite key1sprite;
	private Sprite key2sprite;
	private Sprite foodSprite;
	private Sprite waterSprite;
	private Sprite batterySprite;

	private Texture battleSelectionBoxTexture;
	private Texture selectionBoxTexture;

	private Sprite selectionBoxSprite;
	private Sprite battleSelectionBoxSprite;

	private static int WIDTH = 800;
	private static int HEIGHT = 480;

	private BitmapFont font;

	private Object controlLayer;

	public ItemLayer (Player player1) {

		batch = new SpriteBatch ();

		player = player1;
		itemx = 0;
		itemy = 0;
		position = 0;
		useable = false;

		stage = new Stage(WIDTH, HEIGHT, true);


		inventoryScreenTexture = new Texture (Gdx.files.internal("inventory/inventory.png"));
		key1 = new Texture(Gdx.files.internal("inventory/Key1.png"));
		key2 = new Texture(Gdx.files.internal("inventory/Key1.png"));
		food = new Texture(Gdx.files.internal("inventory/food.png"));
		water = new Texture(Gdx.files.internal("inventory/water.png"));
		battery = new Texture(Gdx.files.internal("inventory/battery.png"));
		//rageScreenTexture = new Texture (Gdx.files.internal("inventory/Battlescreen2.png"));

		inventoryScreenSprite = new Sprite (inventoryScreenTexture);
		key1sprite = new Sprite (key1);
		key2sprite = new Sprite (key2);
		foodSprite = new Sprite (food);
		waterSprite = new Sprite (water);
		batterySprite = new Sprite (battery);
		//rageScreenSprite = new Sprite (rageScreenTexture);
		inventoryScreenSprite.setPosition(5.0f, 5.0f);

		battleSelectionBoxTexture = new Texture (Gdx.files.internal("inventory/Battleselectionbox.png"));
		battleSelectionBoxSprite = new Sprite (battleSelectionBoxTexture);
		battleSelectionBoxSprite.setPosition(30, 256);

		selectionBoxTexture = new Texture(Gdx.files.internal("inventory/selectionBox.png"));
		selectionBoxSprite = new Sprite (selectionBoxTexture);
		selectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f - 128, 30);

		inventory_screen_texture_region = new TextureRegion(inventoryScreenTexture,0,0,image_width,image_height);


		font = new BitmapFont();

	}

	@Override
	public void draw(float delta) 
	{
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batch.begin();

		batch.draw(inventory_screen_texture_region,0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		key1sprite.setPosition(Gdx.graphics.getWidth() / 2.0f -22, 102f);
		itemx = 0;
		itemy = 0;
		for(int i = 0; i < player.items.size();i++)
		{

			//System.out.println("TESTING FOOD");
			if(player.items.elementAt(i).equals("food"))
			{
				//System.out.println("PRINTING FOOD");
				foodSprite.setPosition(125+(120*(i-(5*itemy))), 351 - (120 * itemy ));
				itemx++;
				if(itemx ==5)
				{
					itemx = 0;
					itemy ++;
				}
				foodSprite.draw(batch,1f);
			}
			else if(player.items.elementAt(i).equals("water"))
			{
				waterSprite.setPosition(30+(120*(i-(5*itemy))), 256 - (120 * itemy));
				itemx++;
				if(itemx ==4)
				{
					itemx = 0;
					itemy ++;
				}
				waterSprite.draw(batch,1f);
			}
			else if(player.items.elementAt(i).equals("battery"))
			{
				batterySprite.setPosition(30+(120*(i-(5*itemy))), 256 - (120 * itemy));
				itemx++;
				if(itemx ==4)
				{
					itemx = 0;
					itemy ++;
				}
				batterySprite.draw(batch,1f);
			}
			else if(player.items.elementAt(i).equals("key1"))
			{
				key1sprite.setPosition(30+(120*(i-(5*itemy))), 256 - (120 * itemy));
				itemx++;
				if(itemx ==4)
				{
					itemx = 0;
					itemy ++;
				}
				key1sprite.draw(batch,1f);
			}
			else if(player.items.elementAt(i).equals("key2"))
			{
				key2sprite.setPosition(30+(120*(i-(5*itemy))), 256 - (120 * itemy));
				itemx++;
				if(itemx ==4)
				{
					itemx = 0;
					itemy ++;
				}
				key2sprite.draw(batch,1f);
			}
		}




		//key1sprite.draw(batch,1f);
		batch.end();
		stage.act( delta );
		// draw the actors
		stage.draw();

		batch.begin();
		if(dialogVisible && useable)
		{
			if(position == 0)
				selectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f - 128, 30);
			else
				selectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 64, 30);

			selectionBoxSprite.draw(batch,1f);
		}

		battleSelectionBoxSprite.draw(batch,1f);

		//key1sprite.draw(batch,1f);
		batch.end();

		Gdx.gl.glDisable(GL10.GL_BLEND);

	}


	@Override
	public void upPressed() {

		if(positiony ==0)
		{
			positiony = 2;
			battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

		}
		else
		{
			positiony--;
			battleSelectionBoxSprite.setPosition(30+(120*positionx), 256- (120 * positiony));

		}
		// TODO Auto-generated method stub

	}

	@Override
	public void downPressed() {

		if(positiony ==2)
		{
			positiony = 0;
			battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

		}
		else
		{
			positiony++;
			battleSelectionBoxSprite.setPosition(30+(120*positionx), 256- (120 * positiony));

		}
		// TODO Auto-generated method stub

	}

	@Override
	public void leftPressed() {
		if(dialogVisible)
		{
			if(position == 0)
				position = 1;
			else
				position = 0;
		}
		else
		{
			if(positionx ==0)
			{
				positionx = 4;
				battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

			}
			else
			{
				positionx--;
				battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void rightPressed() {

		if(dialogVisible)
		{
			if(position == 0)
				position = 1;
			else
				position = 0;
		}
		else
		{
			if(positionx ==4)
			{
				positionx = 0;
				battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

			}
			else
			{
				positionx++;
				battleSelectionBoxSprite.setPosition(30+(120*positionx), 256 - (120 * positiony));

			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void xPressed() {
		if(!dialogVisible)
		{
			if(player.items.size() > 0)
			{
				if(player.items.size() > positionx + (positiony*4))
				{
					if(player.items.elementAt(positionx + (positiony*4)).equals("food"))
					{
						text = "Do you wish to eath this food?\n"
								+ "...  YES              ...  NO";
						useable = true;
					}
				}
				else
					text = "There is nothing here";
			}
			else
				text = "There is nothing here";
			//upPressed();

			Texture dialogTexture = new Texture(Gdx.files.internal("ui/DialogBox.png"));
			dialogBox = new Image(dialogTexture);
			dialogBox.setPosition(WIDTH / 2.0f - 256.0f, -128.0f);
			stage.addActor(dialogBox);

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

		}
		else
		{
			if(useable)
			{
				if(position == 0)
				{
					if(player.items.elementAt(positionx + positiony*4).equals("food"))
					{
						player.items.remove(player.items.elementAt(positionx + positiony*4));
						player.resetHealth();
						player.adjustHunger(33f);
						player.nrOfItems--;
						player.foods--;
					}
					else
					{
						player.items.remove(player.items.elementAt(positionx + positiony*4));
						player.adjustHunger(50f);
						player.nrOfItems--;
						player.waters--;
					}
				}
				
				useable = false;
			}
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
						dialogVisible = false;
					}
				}
			});

			dialogLabel.addAction(action1);
			dialogBox.addAction(seq);
		}

	}

	@Override
	public void yPressed() {
		System.out.println("PRESSED Y");
		pauseMenuScreen.exitInventory();
		// TODO Auto-generated method stub

	}

	@Override
	public void startPressed() {
		// TODO Auto-generated method stub

	}

	public void setPauseMenu(PauseMenuScreen pauseMenuScreen)
	{
		this.pauseMenuScreen = pauseMenuScreen;
	}

	public void setControlLayer(ControlLayer controlLayer) {
		this.controlLayer = controlLayer;
		// TODO Auto-generated method stub

	}
}