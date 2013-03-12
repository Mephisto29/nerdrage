package com.nerdrage;

public class Player 
{
	int health;
	int level;
	int damage;
	int equipDamage;
	int experience;
	float thirst;
	float hunger;
	
	
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
		int tempdamage = (int)(damage*attackdamage);
		if(tempdamage == 0)
			return (1+equipDamage);
		else
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
	
	public void adjustHunger (float toAdd) {
		hunger = Math.min(Math.max((hunger + toAdd), 0.0f), 100.0f);  
	}
	
	public void adjustThirst (float toAdd) {
		thirst = Math.min(Math.max((thirst + toAdd), 0.0f), 100.0f);  
	}
	
	public float getThirst () {
		return thirst;
	}
	
	public float getHunger () {
		return hunger;
	}
	
	
	public Player()
	{
		health = 100;
		equipDamage = 0;
		level = 1;
		experience = 0;
		damage = 10;
		thirst = 100.0f;
		hunger = 100.0f;
	}
}
