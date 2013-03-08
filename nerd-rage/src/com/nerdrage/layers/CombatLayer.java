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
	private int position = 0;

	
	public CombatLayer () {
		
		batch = new SpriteBatch ();
		
		enemy = new Enemy();
		player = new Player();
		
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
		else
		{
			// CHANGE GAME STATE!!!
		}
		
		
		font.draw(batch, "Attack", 650, 130);
		font.draw(batch, "Item", 715, 130);
		font.draw(batch, "Run", 780, 130);
		batch.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
	}
	
	@Override
	public void upPressed() {
		// TODO Auto-generated method stub
		
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
		System.out.println("ATTACK");
		enemy.setHealth(10);
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
		System.out.println("ATTACK");
		
	}

	@Override
	public void startPressed() {
		System.out.println("ATTACK");
		
	}

	

}
