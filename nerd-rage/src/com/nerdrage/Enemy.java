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
		if(jockLevel > 0.7)
			level = playerlevel++;
		else if (jockLevel > 0.5)
			level = playerlevel;
		else
			level = playerlevel--;
	}
	
	public int getExperience()
	{
		int extraexp = (int)(Math.random()*100);
		return ((level*100) + extraexp);
	}
	
	public int getDamage()
	{
		double damagedone = Math.random();
		return ((int)(damage*damagedone)+ level + attackDamage);
	}
	
	public void setDamage()
	{
		damage = 5 + level;
		attackDamage = 0;
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
		damage = 5 + level;
		attackDamage = 0;
		attack = "Pummel Nerd";
		attack1 = "WEDGIE";
		attack2 = "FLEX!!!!!";
	}
}
