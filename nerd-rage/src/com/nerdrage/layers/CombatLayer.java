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

public class CombatLayer extends AbstractReceiverLayer {

	private SpriteBatch batch; 

	public Player player;
	public Enemy enemy;

	private Texture battleScreenTexture;
	private TextureRegion battle_screen_texture_region;

	private int image_width=512;
	private int image_height=310;

	private Sprite battleScreenSprite;

	private Texture battleSelectionBoxTexture;
	private Sprite battleSelectionBoxSprite;

	private static int WIDTH = 800;
	private static int HEIGHT = 480;

	private Stage stage;
	private Actor dialogBox;
	private Label dialogLabel;
	private boolean dialogVisible;
	private String text = "";


	private Texture healthBoxTexture1;
	private Texture healthBoxTexture2;
	private Texture healthBoxTexture3;

	private Sprite healthBoxSprite1;
	private Sprite healthBoxSprite2;
	private Sprite healthBoxSprite3;
	private Sprite healthBoxSprite4;
	private Sprite healthBoxSprite5;
	private Sprite healthBoxSprite6;
	private Sprite healthBoxSprite7;
	private Sprite healthBoxSprite8;
	private Sprite healthBoxSprite9;
	private Sprite healthBoxSprite10;

	private Sprite enemyHealthSprite1;
	private Sprite enemyHealthSprite2;
	private Sprite enemyHealthSprite3;
	private Sprite enemyHealthSprite4;
	private Sprite enemyHealthSprite5;
	private Sprite enemyHealthSprite6;
	private Sprite enemyHealthSprite7;
	private Sprite enemyHealthSprite8;
	private Sprite enemyHealthSprite9;
	private Sprite enemyHealthSprite10;

	private BitmapFont font;

	private boolean attackMenu = false;
	private boolean itemMenu = false;

	private boolean playerTurn = true;
	private boolean specialAttack = false;
	private boolean usedFood = false;
	private boolean ranAway = false;
	private boolean run = false;
	private boolean usewater = false;

	private boolean confused = false;

	private boolean playerstep = false;
	private boolean step1 = false;
	private boolean step2 = false;
	private boolean step3 = false;

	private int position = 0;
	private int confusedAttack = 33;
	private int runawaychance = 33;
	private double confuse;
	private double runable;

	String enemyAttack = "";
	int playerdamage = 0;
	int enemydamage = 0;
	int experience = 0;



	public CombatLayer (Player player1) {

		batch = new SpriteBatch ();

		stage = new Stage(WIDTH, HEIGHT, true);
		dialogVisible = false;

		player = player1;
		enemy = new Enemy();
		enemy.setLevel(player.getLevel());
		experience = enemy.getExperience();


		battleScreenTexture = new Texture (Gdx.files.internal("data/Battlescreen.png"));
		battleScreenSprite = new Sprite (battleScreenTexture);
		battleScreenSprite.setPosition(5.0f, 5.0f);

		battleSelectionBoxTexture = new Texture (Gdx.files.internal("data/Battleselectionbox.png"));
		battleSelectionBoxSprite = new Sprite (battleSelectionBoxTexture);
		battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 80.0f , 102f);

		healthBoxTexture1 = new Texture (Gdx.files.internal("data/Healthblock1.png"));
		healthBoxTexture2 = new Texture (Gdx.files.internal("data/Healthblock2.png"));
		healthBoxTexture3 = new Texture (Gdx.files.internal("data/Healthblock3.png"));


		healthBoxSprite1 = new Sprite (healthBoxTexture1);
		healthBoxSprite2 = new Sprite (healthBoxTexture1);
		healthBoxSprite3 = new Sprite (healthBoxTexture2);
		healthBoxSprite4 = new Sprite (healthBoxTexture2);
		healthBoxSprite5 = new Sprite (healthBoxTexture2);
		healthBoxSprite6 = new Sprite (healthBoxTexture3);
		healthBoxSprite7 = new Sprite (healthBoxTexture3);
		healthBoxSprite8 = new Sprite (healthBoxTexture3);
		healthBoxSprite9 = new Sprite (healthBoxTexture3);
		healthBoxSprite10 = new Sprite (healthBoxTexture3);

		enemyHealthSprite1 = new Sprite (healthBoxTexture1);
		enemyHealthSprite2 = new Sprite (healthBoxTexture1);
		enemyHealthSprite3 = new Sprite (healthBoxTexture2);
		enemyHealthSprite4 = new Sprite (healthBoxTexture2);
		enemyHealthSprite5 = new Sprite (healthBoxTexture2);
		enemyHealthSprite6 = new Sprite (healthBoxTexture3);
		enemyHealthSprite7 = new Sprite (healthBoxTexture3);
		enemyHealthSprite8 = new Sprite (healthBoxTexture3);
		enemyHealthSprite9 = new Sprite (healthBoxTexture3);
		enemyHealthSprite10 = new Sprite (healthBoxTexture3);

		healthBoxSprite1.setPosition(Gdx.graphics.getWidth() / 2.0f + 80.0f , 185f);
		healthBoxSprite2.setPosition(Gdx.graphics.getWidth() / 2.0f + 96.0f , 185f);
		healthBoxSprite3.setPosition(Gdx.graphics.getWidth() / 2.0f + 112.0f , 185f);
		healthBoxSprite4.setPosition(Gdx.graphics.getWidth() / 2.0f + 128.0f , 185f);
		healthBoxSprite5.setPosition(Gdx.graphics.getWidth() / 2.0f + 144.0f , 185f);
		healthBoxSprite6.setPosition(Gdx.graphics.getWidth() / 2.0f + 160.0f , 185f);
		healthBoxSprite7.setPosition(Gdx.graphics.getWidth() / 2.0f + 176.0f , 185f);
		healthBoxSprite8.setPosition(Gdx.graphics.getWidth() / 2.0f + 192.0f , 185f);
		healthBoxSprite9.setPosition(Gdx.graphics.getWidth() / 2.0f + 208.0f , 185f);
		healthBoxSprite10.setPosition(Gdx.graphics.getWidth() / 2.0f + 224.0f , 185f);

		enemyHealthSprite1.setPosition(70.0f , 200f + 175);
		enemyHealthSprite2.setPosition(86.0f , 200f + 175);
		enemyHealthSprite3.setPosition(102.0f , 200f + 175);
		enemyHealthSprite4.setPosition(118.0f , 200f + 175);
		enemyHealthSprite5.setPosition(134.0f , 200f + 175);
		enemyHealthSprite6.setPosition(150.0f , 200f + 175);
		enemyHealthSprite7.setPosition(166.0f , 200f + 175);
		enemyHealthSprite8.setPosition(182.0f , 200f + 175);
		enemyHealthSprite9.setPosition(198.0f , 200f + 175);
		enemyHealthSprite10.setPosition(214.0f , 200f + 175);

		battle_screen_texture_region = new TextureRegion(battleScreenTexture,0,0,image_width,image_height);


		font = new BitmapFont();

	}

	@Override
	public void draw(float delta) {

		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batch.begin();
		batch.draw(battle_screen_texture_region,0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(playerTurn)
			battleSelectionBoxSprite.draw(batch,1f);

		if(player.getHealth() > 0)
		{
			healthBoxSprite1.draw(batch,1f);
			if(player.getHealth() >= 10)
			{
				healthBoxSprite2.draw(batch,1f);
			}
			if(player.getHealth() >= 20)
			{
				healthBoxSprite3.draw(batch,1f);
			}
			if(player.getHealth() >= 30)
			{
				healthBoxSprite4.draw(batch,1f);
			}
			if(player.getHealth() >= 40)
			{
				healthBoxSprite5.draw(batch,1f);
			}
			if(player.getHealth() >= 50)
			{
				healthBoxSprite6.draw(batch,1f);
			}
			if(player.getHealth() >=60)
			{
				healthBoxSprite7.draw(batch,1f);
			}
			if(player.getHealth() >= 70)
			{
				healthBoxSprite8.draw(batch,1f);
			}
			if(player.getHealth() >= 80)
			{
				healthBoxSprite9.draw(batch,1f);
			}
			if(player.getHealth() >= 90)
			{
				healthBoxSprite10.draw(batch,1f);
			}
		}

		if(enemy.getHealth() > 0)
		{
			enemyHealthSprite1.draw(batch,1f);

			if(enemy.getHealth() > 10)
			{
				enemyHealthSprite2.draw(batch,1f);
			}
			if(enemy.getHealth() > 20)
			{
				enemyHealthSprite3.draw(batch,1f);
			}
			if(enemy.getHealth() > 30)
			{
				enemyHealthSprite4.draw(batch,1f);
			}
			if(enemy.getHealth() > 40)
			{
				enemyHealthSprite5.draw(batch,1f);
			}
			if(enemy.getHealth() > 50)
			{
				enemyHealthSprite6.draw(batch,1f);
			}
			if(enemy.getHealth() > 60)
			{
				enemyHealthSprite7.draw(batch,1f);
			}
			if(enemy.getHealth() > 70)
			{
				enemyHealthSprite8.draw(batch,1f);
			}
			if(enemy.getHealth() > 80)
			{
				enemyHealthSprite9.draw(batch,1f);
			}
			if(enemy.getHealth() > 90)
			{
				enemyHealthSprite10.draw(batch,1f);
			}	
		}

		if(playerTurn)
		{
			if(attackMenu)
			{
				font.draw(batch, "Normal", Gdx.graphics.getWidth() / 2.0f + 85.0f , 125f);
				font.draw(batch, "Special", Gdx.graphics.getWidth() / 2.0f + 150f, 125f);
				font.draw(batch, "Back", Gdx.graphics.getWidth() / 2.0f + 210f, 125f);
			}
			else if(itemMenu)
			{
				font.draw(batch, "Water", Gdx.graphics.getWidth() / 2.0f + 90.0f , 125f);
				font.draw(batch, "Food", Gdx.graphics.getWidth() / 2.0f + 155f, 125f);
				font.draw(batch, "Back", Gdx.graphics.getWidth() / 2.0f + 210f, 125f);
			}
			else
			{
				font.draw(batch, "Attack", Gdx.graphics.getWidth() / 2.0f + 90.0f , 125f);
				font.draw(batch, "Item", Gdx.graphics.getWidth() / 2.0f + 155f, 125f);
				font.draw(batch, "Run", Gdx.graphics.getWidth() / 2.0f + 210f, 125f);
			}
		}



		batch.end();
		stage.act( delta );
		// draw the actors
		stage.draw();
		Gdx.gl.glDisable(GL10.GL_BLEND);

	}

	@Override
	public void upPressed() 
	{	}

	@Override
	public void downPressed() 
	{	}

	@Override
	public void leftPressed() 
	{
		if(position == 1)
		{
			battleSelectionBoxSprite.setPosition( Gdx.graphics.getWidth() / 2.0f + 80.0f , 102f);
			position = 0;
		}
		else if(position == 2)
		{
			battleSelectionBoxSprite.setPosition( Gdx.graphics.getWidth() / 2.0f + 140.0f , 102f);
			position = 1;			
		}
		else;

	}

	@Override
	public void rightPressed() 
	{
		if(position == 0)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 140.0f , 102f);
			position = 1;
		}
		else if(position == 1)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 200.0f , 102f);
			position = 2;			
		}
		else;

	}

	@Override
	public void xPressed() 
	{
		if(!dialogVisible)
		{
			if(step3)
			{
				if(player.getHealth() > 0)
					player.setExperience(experience);
				playerTurn = false;
				
				if(enemy.getHealth() <= 0)
				{
					text = "Congradulations you defeated a Jock\n";
					text = text + "You recieve "+ experience +" points of experience";
				}
				else
					text = "GG!!  You have been pummeled to death";
				
				// change state
			}

			if(playerTurn)
			{
				playerdamage = player.getDamage();
				enemydamage = enemy.getDamage();
				enemyAttack = enemy.getAttack();
				if(attackMenu == true)
				{
					if(position == 0)
					{
						System.out.println("ATTACK");
						enemy.setHealth(player.getDamage());
						playerTurn = false;
						playerstep = true;

						text = "You attack and do " + playerdamage + " damage";

						if(enemy.getHealth() <= 0)
						{
							System.out.println("VICTORY");
							System.out.println("YOU HAVE WON");
						}
					}
					else if (position == 1)
					{
						System.out.println("USE SPECIAL ATTACK");
						confuse = (Math.random() * 100);
						if (confuse < 50)
						{
							confused = true;
						}

						text = "You try to educate the Jock\n";
						if(confused)
						{}
						else
							text = text + "The Jock ignores you";

						playerTurn = false;
						specialAttack = true;
						playerstep = true;
					}
					else if (position == 2)
					{
						System.out.println("BACK TO PREVIOUS MENU");
					}
					attackMenu = false;
				}
				else if(itemMenu == true)
				{
					if(position == 0)
					{
						System.out.println("USE WATER");
						enemy.setDamage();
						playerTurn = false;
						playerstep = true;
						usewater = true;



						text = "You Throw water at the Jock\n";
						text = text + "The Jock is more sober, he does less damage now";

					}
					else if (position == 1)
					{
						System.out.println("USE FOOD");
						runawaychance = runawaychance + 33;
						playerTurn = false;
						usedFood = true;

						text = "You Trow food at the Jock";
					}
					else if (position == 2)
					{

					}
					itemMenu = false;
				}
				else
				{
					if(position == 0)
					{
						System.out.println("ATTACK MENU");
						attackMenu = true;
					}
					else if (position == 1)
					{
						System.out.println("ITEM MENU");
						itemMenu = true;
					}
					else if (position == 2)
					{
						System.out.println("RUNNNNNNN!!!!!!");
						run = true;
						runable = (Math.random() * 100);
						if (runable < runawaychance)
						{
							ranAway = true;
						}
						playerTurn = false;
						playerstep = true;

						text = "You try to run from the Jock";
						
					}
				}
				if(playerTurn== false)
					step1 = true;
			}

			else
			{
				if(playerstep)
				{
					playerstep = false;
					step1 = true;
					
					if(ranAway)
					{
						text = "You have successfully evaded the Jock";
					}
					else if(confused)
					{
						text =  "The Jock is super confused\n";
						confuse = Math.random()*100;
						if (confuse < confusedAttack)
						{						}
						else
						{
							text = text + "Jock uses " + enemyAttack;
						}
					}
					else
					{
						text = "Jock uses " + enemyAttack;
					}

				}
				else if(step1)
				{
					step1 = false;
					step2 = true;
					
					if(ranAway)
					{
						text = "YOU GAIN NOTHING WIMP!";
						// CHANGE STATE
					}
					else if(usedFood)
					{
						text = "The Jock Munches the food";
					}
					else if(confused && !usedFood)
					{
						if (confuse < confusedAttack)
						{
							enemy.setHealth(enemy.getDamage());
							text = "Jock hurts itself in its confusion";
						}
						else
						{
							if(enemyAttack.equals("FLEX!!!!!"))
							{
								text = "The Jock flexes his muscles! His damage is now doubled!!!!";
							}
							else
							{
								player.setHealth(enemy.getDamage());
								text = "You recieve " + enemydamage + " points of damage";
							}
						}
					}
					else if(!usedFood )	
					{
						if(enemyAttack.equals("FLEX!!!!!"))
						{
							text = "The Jock flexes his muscles! His damage is increased!!!!";
						}
						else
						{
							player.setHealth(enemy.getDamage());
							text = "You recieve " + enemydamage + " points of damage";
						}
					}
					
					if(player.getHealth() <= 0 || enemy.getHealth() <=0)
					{
						step3 = true;
					}
				}
				else if (step2)
				{
					playerstep = false;
					step1 = false;
					step2 = false;
					playerTurn = true;
					run = false;
					specialAttack = false;
					usewater = false;
					usedFood = false;
					position = 0;
					if(ranAway)
						System.out.println("ESCAPED");
				}
			}
			if(!dialogVisible)
			{
				if(!playerTurn)
				{
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
			}
		}
		else
		{
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
	public void yPressed() 
	{	
		attackMenu = false;
		itemMenu = false;
	}
	
	@Override
	public void startPressed() 
	{	}

}
