package com.nerdrage.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Main menu screen. Will allow the user to choose between starting a new game,
 * continuing a paused game, viewing a tutorial and viewing a high scores page
 */
public class HelpScreen extends AbstractScreen {

	Game game;
	SpriteBatch spritebatch;
	Texture back_texture, flames_texture;
	TextureRegion flames_texture_region;
	Sprite back_sprite;
	float screen_width = Gdx.graphics.getWidth();
	float screen_height = Gdx.graphics.getHeight();
	float button_width = 256;
	float button_height = 64;
	float center = /*(screen_width - button_width) / 2*/ 500;
	float center_y = /*(screen_height-button_height) / 6*/30;
	OrthographicCamera camera;

	public HelpScreen(Game game) {
		this.game = game;
		spritebatch = new SpriteBatch();
		camera = new OrthographicCamera(screen_width, screen_height);
		camera.setToOrtho(false); // This points the y-axis upwards, instead of
									// downwards
		print("creating new camera with width: " + screen_width
				+ " and height: " + screen_height);

		back_texture = new Texture(Gdx.files.internal("buttons/backtomenu.png"));
		back_sprite = new Sprite(back_texture);
		back_sprite.setPosition(center, center_y);

		flames_texture = new Texture(Gdx.files.internal("menu/helpscreen.png"));
		flames_texture_region = new TextureRegion(flames_texture, 0, 0, 512,
				310);

	}

	public void print(String text) {
		//System.out.println(text);
	}

	public void update(float delta) {

		camera.update();
		float inputx = Gdx.input.getX();
		float inputy = Gdx.input.getY();

		Vector3 touched = new Vector3(inputx, inputy, 0);
		camera.unproject(touched);

		Rectangle back_rectangle = new Rectangle(center, center_y,button_width, button_height);
		
		if (Gdx.input.justTouched()) {
			if (point_in_rectangle(back_rectangle, touched.x, touched.y)) {
				//System.out.println("Pressed back");
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update(delta);

		spritebatch.begin();
		spritebatch.draw(flames_texture_region, 0, 0, screen_width,
				screen_height);
		back_sprite.draw(spritebatch);
		spritebatch.end();
	}

	@Override
	public void show() {

	}
}