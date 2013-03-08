package com.nerdrage;

public class Player 
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
	
	
	public Player()
	{
		health = 100;
	}
}
