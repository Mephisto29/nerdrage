package com.nerdrage.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nerdrage.Enemy;
import com.nerdrage.Player;

public class CombatLayer extends AbstractReceiverLayer {
	
	private SpriteBatch batch; 
	
	public Player player;
	public Enemy enemy;
	
	private Texture battleScreenTexture;
	private Sprite battleScreenSprite;
	
	private Texture battleSelectionBoxTexture;
	private Sprite battleSelectionBoxSprite;
	
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
		
		player = player1;
		enemy = new Enemy();
		enemy.setLevel(player.getLevel());
		experience = enemy.getExperience();
		
		
		battleScreenTexture = new Texture (Gdx.files.internal("data/Battlescreen.png"));
		battleScreenSprite = new Sprite (battleScreenTexture);
		battleScreenSprite.setPosition(5.0f, 5.0f);
		
		battleSelectionBoxTexture = new Texture (Gdx.files.internal("data/Battleselectionbox.png"));
		battleSelectionBoxSprite = new Sprite (battleSelectionBoxTexture);
		battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 130.0f , 105f);
		
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
		
		healthBoxSprite1.setPosition(Gdx.graphics.getWidth() / 2.0f + 100.0f , 200f);
		healthBoxSprite2.setPosition(Gdx.graphics.getWidth() / 2.0f + 116.0f , 200f);
		healthBoxSprite3.setPosition(Gdx.graphics.getWidth() / 2.0f + 132.0f , 200f);
		healthBoxSprite4.setPosition(Gdx.graphics.getWidth() / 2.0f + 148.0f , 200f);
		healthBoxSprite5.setPosition(Gdx.graphics.getWidth() / 2.0f + 164.0f , 200f);
		healthBoxSprite6.setPosition(Gdx.graphics.getWidth() / 2.0f + 180.0f , 200f);
		healthBoxSprite7.setPosition(Gdx.graphics.getWidth() / 2.0f + 196.0f , 200f);
		healthBoxSprite8.setPosition(Gdx.graphics.getWidth() / 2.0f + 212.0f , 200f);
		healthBoxSprite9.setPosition(Gdx.graphics.getWidth() / 2.0f + 228.0f , 200f);
		healthBoxSprite10.setPosition(Gdx.graphics.getWidth() / 2.0f + 244.0f , 200f);
		
		enemyHealthSprite1.setPosition(100.0f , 200f + 200);
		enemyHealthSprite2.setPosition(116.0f , 200f + 200);
		enemyHealthSprite3.setPosition(132.0f , 200f + 200);
		enemyHealthSprite4.setPosition(148.0f , 200f + 200);
		enemyHealthSprite5.setPosition(164.0f , 200f + 200);
		enemyHealthSprite6.setPosition(180.0f , 200f + 200);
		enemyHealthSprite7.setPosition(196.0f , 200f + 200);
		enemyHealthSprite8.setPosition(212.0f , 200f + 200);
		enemyHealthSprite9.setPosition(228.0f , 200f + 200);
		enemyHealthSprite10.setPosition(244.0f , 200f + 200);
		
		
		font = new BitmapFont();

	}
	
	@Override
	public void draw() {
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	
		batch.begin();
		battleScreenSprite.draw(batch,1f);
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
				font.draw(batch, "Normal", 650, 130);
				font.draw(batch, "Special", 715, 130);
				font.draw(batch, "Back", 780, 130);
			}
			else if(itemMenu)
			{
				font.draw(batch, "Water", 650, 130);
				font.draw(batch, "Food", 715, 130);
				font.draw(batch, "Back", 780, 130);
			}
			else
			{
				font.draw(batch, "Attack", 650, 130);
				font.draw(batch, "Item", 715, 130);
				font.draw(batch, "Run", 780, 130);
			}
		}
		else if(playerstep)
		{
			if(specialAttack)
			{
				font.draw(batch, "You try to educate the Jock", 100, 130);
				if(confused)
					font.draw(batch, "The Jock is super confused", 100, 110);
				else
					font.draw(batch, "The Jock ignores you", 100, 110);
			}
			else if(run)
				font.draw(batch, "You try to run from the Jock", 100, 130);
			else if(usewater)
			{
				font.draw(batch, "You Throw water at the Jock", 100, 130);
				font.draw(batch, "The Jock is more sober, he does less damage now", 100, 110);
			}
			else if(usedFood)
			{}
			else
				font.draw(batch, "You attack and do " + playerdamage + " damage", 100, 130);
		}
		else
		{
			if(step1)
			{
				if(ranAway)
				{
					font.draw(batch, "You have successfully evaded the Jock", 100, 130);
				}
				else if (usedFood)
				{
					font.draw(batch, "You Trow food at the Jock", 100, 130);
				}
				else if(confused)
				{
					font.draw(batch, "The Jock is super confused", 100, 130);
					font.draw(batch, "Jock uses " + enemyAttack, 100, 110);
				}
				else
				{
					font.draw(batch, "Jock uses " + enemyAttack, 100, 130);
				}
			}
			else if (step2)
			{
				if(usedFood)
				{
					font.draw(batch, "The Jock Munches the food", 100, 130);
				}
				else if(confused)
				{
					if (confuse < confusedAttack)
					{
						font.draw(batch, "Jock hurts itself in its confusion", 100, 130);
					}
					else
					{
						if(enemyAttack.equals("FLEX!!!!!"))
						{
							font.draw(batch, "The Jock flexes his muscles! His damage is now doubled!!!!", 100, 130);
						}
						else	
						{
							font.draw(batch, "You recieve " + enemydamage + " points of damage", 100, 130);
						}
					}
				}
				else
				{
					if(enemyAttack.equals("FLEX!!!!!"))
					{
						font.draw(batch, "The Jock flexes his muscles! His damage is now doubled!!!!", 100, 130);
					}
					else	
					{
						font.draw(batch, "You recieve " + enemydamage + " points of damage", 100, 130);
					}
				}
			}
			
			if(step3)
			{
				if(enemy.getHealth() <= 0)
				{
					font.draw(batch, "Congradulations you defeated a Jock", 100, 130);
					font.draw(batch, "You recieve "+ experience +" points of experience", 100, 110);
				}
				else
					font.draw(batch, "GG!!  You have been pummeled to death", 100, 130);
			}
		}
		
		batch.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
	}
	
	@Override
	public void upPressed() 
	{
		if(step3)
		{
			if(player.getHealth() > 0)
				player.setExperience(enemy.getExperience());
			playerTurn = false;
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
					
					if(enemy.getHealth() <= 0)
					{
						System.out.println("VICTORY");
						draw();
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
					
				}
				else if (position == 1)
				{
					System.out.println("USE FOOD");
					runawaychance = runawaychance + 33;
					playerTurn = false;
					usedFood = true;
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
				run = false;
				specialAttack = false;
				usewater = false;
				step1 = true;
				if(confused)
				{
					confuse = Math.random()*100;
				}
			}
			else if(step1)
			{
				if(ranAway)
				{
					step1 = false;
					// CHANGE SATE
				}
				else
				{
					step1 = false;
					step2 = true;
				}
			}
			else if (step2)
			{

				step2 = false;
				playerTurn = true;
				
				if(confused)
				{
					if (confuse < confusedAttack)
					{
						enemy.setHealth(enemy.getDamage());
					}
					else
					{
						if(enemyAttack.equals("FLEX!!!!!"))
						{}
						else
							player.setHealth(enemy.getDamage());
					}
				}
				else if(!usedFood )	
				{
					if(enemyAttack.equals("FLEX!!!!!"))
					{}
					else
						player.setHealth(enemy.getDamage());
					
					usedFood = false;
				}
					
				else
					usedFood = false;
				
				if(player.getHealth() <= 0 || enemy.getHealth() <=0)
				{
					step3 = true;
				}
			}
		}
		
		
	}

	@Override
	public void downPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftPressed() 
	{
		if(position == 1)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 130.0f , 105f);
			position = 0;
		}
		else if(position == 2)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 194.0f , 105f);
			position = 1;			
		}
		else;
		
	}

	@Override
	public void rightPressed() 
	{
		if(position == 0)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 194.0f , 105f);
			position = 1;
		}
		else if(position == 1)
		{
			battleSelectionBoxSprite.setPosition(Gdx.graphics.getWidth() / 2.0f + 258.0f , 105f);
			position = 2;			
		}
		else;
		
	}

	@Override
	public void xPressed() 
	{
		System.out.println("ATTACK");
		if(position == 0)
		{
			enemy.setHealth(10);
			
		}
		else if (position == 1)
		{
			
		}
		else if (position == 2)
		{
			
		}
	}

	@Override
	public void yPressed() {
		
	}

	@Override
	public void startPressed() {
		
	}

	

}
