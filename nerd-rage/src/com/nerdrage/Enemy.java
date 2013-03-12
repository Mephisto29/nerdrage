package com.nerdrage;

public class Enemy 
{
	int health;
	int level;
	int damage;
	String attack;
	String attack1;
	String attack2;
	int attackDamage;
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int damage)
	{
		health = health - damage;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel(int playerlevel)
	{
		double jockLevel = Math.random();
		if(jockLevel > 66)
			level = playerlevel++;
		else if (jockLevel > 33)
			level = playerlevel;
		else
			level = playerlevel--;
	}
	
	public int getExperience()
	{
		return (level*100 + ((int)Math.random()*100));
	}
	
	public int getDamage()
	{
		double damagedone = Math.random();
		return ((int)(( damage + attackDamage )*damagedone));
	}
	
	public void setDamage()
	{
		damage = damage/2;
	}
	
	public String getAttack()
	{
		double attackselect = Math.random()*100;
		if(attackselect > 90)
		{
			damage = (int)(damage + 2f);
			return attack2;
		}
		else if (attackselect > 60)
		{
			attackDamage += 1;
			return attack1;
		}
		else
			return attack;
	}
	
	public Enemy()
	{
		health = 100;
		damage = 5;
		attackDamage = 0;
		attack = "Pummel Nerd";
		attack1 = "WEDGIE";
		attack2 = "FLEX!!!!!";
	}
}
