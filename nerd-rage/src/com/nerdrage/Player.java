package com.nerdrage;

import java.util.Vector;

public class Player 
{
	int health;
	int level;
	int damage;
	public int equipDamage;
	int experience;
	public int defence;
	float thirst;
	float hunger;
	float nerdRage;
	boolean inRage;
    public int foods = 0;
	public int waters = 0;
	public int batteries = 0;
	
	public Vector<String> items;
	public int nrOfItems;
	public double confuseChance;
	
	
	public int getHealth()
	{
		return health;
	}
	
	public float getRage()
	{
		return nerdRage;
	}
	
	public void setInRage()
	{
		nerdRage = 0;
		inRage = true;
	}
	
	public void setRage()
	{
		nerdRage = 0;
		inRage = false;
	}
	
	public void resetHealth()
	{
		health = 100;
	}
	
	public void setHealth(int damage)
	{
		if(damage > 0)
			damage = damage - defence;
		
		if(damage < 0)
			damage = 0;
		
		health = health - (damage);
		
		nerdRage += damage *4;//*(Math.random()* 3);
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
		int tempdamage = (int)Math.floor(damage*attackdamage);
		if(inRage)
			tempdamage *=2;
		if(tempdamage == 0)
			return (1+equipDamage + level);
		else
			return tempdamage + equipDamage + level;
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
	
	public void addItem(String item)
	{
		
		if(nrOfItems < 15)
		{
			System.out.println("PICKED UP : " + item);
			items.add(item);
			if(item.equals("food"))
				foods++;
			else if(item.equals("water"))
				waters++;
			else if (item.equals("battery"))
				batteries++;
		}
		else
			System.out.println("YOU DO NOT HAVE SPACE NERD");
	}

	public Player()
	{
		health = 100;
		equipDamage = 0;
		confuseChance = 33;
		level = 1;
		experience = 0;
		damage = 10;
		thirst = 100.0f;
		hunger = 100.0f;
		nerdRage = 0;
		inRage = false;
		items = new Vector<String>();
		nrOfItems = 0;
	}
}
