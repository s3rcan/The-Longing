package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.Animation;
import com.seligames.buildUp.handlers.BackgroundLayer;
import com.seligames.buildUp.handlers.GameButton;
import com.seligames.buildUp.handlers.SoundLib;

public class MainMenuScreen implements Screen {

	private BuildUpGame game;
	// private int selectedButton, menuButtonNumber;
	// private BitmapFont bitmapFont;
	// private String[] menuButtons;

	private Animation bPlayer;
	private Animation wPlayer;

	private float angle;

	private BackgroundLayer backgroundLayer;
	private float angleSpeed = 100.f;

	private GameButton gameButtons[];
	
	private Texture tutorial;

	public MainMenuScreen(BuildUpGame game) {
		this.game = game;
		// bitmapFont = new BitmapFont();

		// selectedButton = 0;
		// this.menuButtonNumber = 3;

		// menuButtons = new String[3];
		// menuButtons[0] = ">Start Game<";
		// menuButtons[1] = "Enter Level";
		// menuButtons[2] = "Quit";
		tutorial = BuildUpGame.assetManager.get("res/images/tutorial.png");
		
		TextureRegion[][] tr = TextureRegion.split(
				(Texture) BuildUpGame.assetManager.get("res/images/menu.png"),
				128, 64);

		game.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);

		gameButtons = new GameButton[3];

		gameButtons[0] = new GameButton(tr[0][0], Constants.V_WIDTH / 2,
				Constants.V_HEIGHT / 6 * 4, game.camera);
		gameButtons[1] = new GameButton(tr[1][0], Constants.V_WIDTH / 2,
				Constants.V_HEIGHT / 6 * 3, game.camera);
		gameButtons[2] = new GameButton(tr[2][0], Constants.V_WIDTH / 2,
				Constants.V_HEIGHT / 6 * 2, game.camera);

		backgroundLayer = game.staticBackground;

		bPlayer = new Animation(TextureRegion.split(
				(Texture) BuildUpGame.assetManager
						.get("res/images/blackBall.png"), 64, 64)[0]);

		wPlayer = new Animation(TextureRegion.split(
				(Texture) BuildUpGame.assetManager
						.get("res/images/whiteBallAnim.png"), 64, 64)[0]);
	}

	@Override
	public void show() {
		SoundLib.bgv2.stop();
		SoundLib.bgv3.stop();
		SoundLib.splashScreen.stop();
		SoundLib.intro.play();

		this.angle = 0;
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
		
		game.batch.draw(tutorial, 0, 0);
		
		game.batch.end();

		gameButtons[0].update(delta);

		if (gameButtons[0].isClicked()) {
			game.setScreen(BuildUpGame.levelScene);
		}

		gameButtons[0].render(game.batch);

		gameButtons[1].update(delta);
		gameButtons[1].render(game.batch);
		if (gameButtons[1].isClicked()) {
			game.setScreen(BuildUpGame.creditsScreen);// credits SCENE
		}
		gameButtons[2].update(delta);
		gameButtons[2].render(game.batch);

		if (gameButtons[2].isClicked()) {
			Gdx.app.exit();
		}

		game.batch.begin();

		game.batch.draw(bPlayer.getFrame(), Constants.V_WIDTH / 2 - 32,
				Constants.V_HEIGHT - 164, (float) 64 / 2, (float) 64 / 2, 64,
				64, 1, 1, angle);

		game.batch.draw(wPlayer.getFrame(), Constants.V_WIDTH / 2 - 32, 100);

		// for(int i = 0, j = 500; i < menuButtonNumber; i++, j-=100){
		// bitmapFont.draw(game.batch, menuButtons[i], 320, j);
		// }

		game.batch.end();

	}

	public void update(float delta) {
		handleInput();
		angle -= delta * angleSpeed;
		if (angle < -360) {
			angle = 0;
		}
		backgroundLayer.update(delta);

		bPlayer.update(delta);

		wPlayer.update(delta);
	}

	private void handleInput() {
		// if(MyInput.isPressed(MyInput.DOWN)){
		// menuButtons[selectedButton] =
		// menuButtons[selectedButton].replace(">", "");
		// menuButtons[selectedButton] =
		// menuButtons[selectedButton].replace("<", "");
		// selectedButton++;
		//
		// if(selectedButton > 2){selectedButton = 0;}
		// else if(selectedButton < 1){selectedButton = 2;}
		//
		// menuButtons[selectedButton] = ">" + menuButtons[selectedButton] +
		// "<";
		// }
		//
		// if(MyInput.isPressed(MyInput.UP)){
		// menuButtons[selectedButton] =
		// menuButtons[selectedButton].replace(">", "");
		// menuButtons[selectedButton] =
		// menuButtons[selectedButton].replace("<", "");
		// selectedButton--;
		//
		// if(selectedButton > 2){selectedButton = 0;}
		// else if(selectedButton < 0){selectedButton = 2;}
		//
		// menuButtons[selectedButton] = ">" + menuButtons[selectedButton] +
		// "<";
		// }
		//
		// if(MyInput.isPressed(MyInput.ENTER)){
		// if(selectedButton == 2){System.exit(0);}
		// if(selectedButton == 1){game.setScreen(BuildUpGame.levelScene);}
		// if(selectedButton == 0){game.setScreen(BuildUpGame.playScene);}
		// }
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
