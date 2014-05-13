package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.BackgroundLayer;
import com.seligames.buildUp.handlers.MyInput;

public class CreditsScreen implements Screen {

	private BuildUpGame game;
	private OrthographicCamera cam;

	private BitmapFont bitmapFont;
	private String[] names;
	
	private float shiftY = 0;

	private BackgroundLayer backgroundLayer;


	public CreditsScreen(BuildUpGame game) {

		this.game = game;
		this.bitmapFont = new BitmapFont();
		
		this.cam = game.camera;
		cam.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);

		backgroundLayer = game.staticBackground;
		
		names = new String[9];
		names[0] = "SELI GAMES";
		names[1] = "Programming";
		names[2] = "Sercan Turkmen";
		names[3] = "Ender Tunc";
		names[4] = "Erman Yafay";
		
		names[5] = "Graphics";
		names[6] = "Ozer Ozkahraman";
		
		names[7] = "Sounds";
		names[8] = "Hilmi Yalin Mungan";
		
	}

	public void handleInput() {
		if (MyInput.isPressed(MyInput.ESC)) {
			game.setScreen(BuildUpGame.mainMenuScene);
		}
	}
	
	public void update(float delta) {
		handleInput();

		backgroundLayer.update(delta);

	}

	@Override
	public void show() {
		cam = game.camera;
		cam.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
		shiftY = 0;
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(game.camera.combined);
		game.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
		
		backgroundLayer.render(game.batch);
		
		game.batch.begin();
		float inShift = 0;
		for(int i = 0;  i < names.length; i++) {
			float posY = Constants.V_HEIGHT / 2 + shiftY - inShift - i * 64;
			bitmapFont.draw(game.batch, names[i], Constants.V_WIDTH / 2 - 64, posY);
			if(i == 0 || i == 4 || i == 6) {
				inShift += 64;
			}
			if( posY > 2 * Constants.V_HEIGHT + 20) {
				game.setScreen(BuildUpGame.mainMenuScene);
			}
		}
		game.batch.end();
		shiftY += delta * 25.f;
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}