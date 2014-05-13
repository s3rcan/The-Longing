package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.BackgroundLayer;
import com.seligames.buildUp.handlers.GameButton;
import com.seligames.buildUp.handlers.MyInput;

public class LevelSelectScreen implements Screen {

	private BuildUpGame game;
	private GameButton[][] buttons;
	private OrthographicCamera cam;
	private SpriteBatch sb;
	
	private BackgroundLayer backgroundLayer;
	
	private float timer = 0;
	
	public LevelSelectScreen(BuildUpGame game) {

		TextureRegion[][] buttonReg = TextureRegion.split(
				(Texture) BuildUpGame.assetManager
						.get("res/images/button1.png"),
				(int) Constants.TILE_WIDTH, (int) Constants.TILE_HEIGHT);

		this.game = game;

		buttons = new GameButton[4][3];
		this.cam = game.camera;
		cam.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);

		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new GameButton(	buttonReg[row][col],
													Constants.V_WIDTH / 2 + col * 128 - 128,
													cam.viewportWidth - cam.viewportWidth / 5 * (row + 1),
													game.camera
													);
			}
		}
		
		backgroundLayer = game.staticBackground;
	}

	public void handleInput() {
		if(MyInput.isPressed(MyInput.ESC)) {
			game.setScreen(BuildUpGame.mainMenuScene);
		}
	}

	public void update(float delta) {
		handleInput();
		timer += delta;
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].update(delta);
				if (buttons[row][col].isClicked() && timer > 1) {

					BuildUpGame.playScene.loadMap(row * 3 + col);
					game.setScreen(BuildUpGame.playScene);
				}
			}
		}
		backgroundLayer.update(delta);

	}

	@Override
	public void show() {
		cam = game.camera;
		sb = game.batch;
		timer = 0;
		cam.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(game.camera.combined);
		game.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
		
		backgroundLayer.render(game.batch);

		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].render(sb);
			}
		}

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