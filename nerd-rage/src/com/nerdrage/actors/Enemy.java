package com.nerdrage.actors;

public class Enemy 
{
	int health;
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int damage)
	{
		health = health - damage;
	}
	
	
	public Enemy()
	{
		health = 100;
	}
}
