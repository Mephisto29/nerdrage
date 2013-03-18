package com.nerdrage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.nerdrage.screens.*;

public class NerdRageGame extends Game {
	
	
	/**
	 * Static variables which the whole game needs to know about
	 */
	public static final boolean DEBUG = false;
	public static final String VERSION = "v1.0";
	public static final String GAME_NAME = "Nerd Rage";
	public static FPSLogger fpsLogger;

	@Override
	public void create() {		
		fpsLogger = new FPSLogger();
		setScreen(new SplashScreen(this));
		Gdx.input.setCatchBackKey(true);
		//setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
