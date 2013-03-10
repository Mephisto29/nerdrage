package com.nerdrage;

public class Player 
{
	int health;
	int level;
	int damage;
	int equipDamage;
	int experience;
	
	
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
	
	public void setLevel()
	{
		level = level++;
	}
	
	public int getDamage()
	{
		double attackdamage = Math.random();
		return (int)(damage*attackdamage) + equipDamage;
	}
	
	public void setEquiped( int damage)
	{
		equipDamage = damage;
	}
	
	public void setExperience(int exp)
	{
		experience = experience + exp;
		if(experience >= level*100)
		{
			level++;
			experience = 0;
		}
	}
	
	public Player()
	{
		health = 100;
		equipDamage = 0;
		level = 1;
		experience = 0;
		damage = 10;
	}
}
