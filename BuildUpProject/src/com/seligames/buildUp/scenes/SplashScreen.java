package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.MyInput;
import com.seligames.buildUp.handlers.SoundLib;

public class SplashScreen implements Screen{
	
	private BuildUpGame game;
	private float timer;
	private float timerLimit;
	private Texture splashImage;
	
	private Vector2 originOfImage;
	
	public SplashScreen(BuildUpGame game){
		this.game = game;
		this.timerLimit = 5;
		this.timer = 0;
		splashImage = BuildUpGame.assetManager.get("res/images/splash.png");
		originOfImage = new Vector2();
		originOfImage.x = (float) (Constants.V_WIDTH - splashImage.getWidth()) / 2;
		originOfImage.y = (float) (Constants.V_HEIGHT - splashImage.getHeight()) / 2;
	}
	
	@Override
	public void show() {
		SoundLib.splashScreen.play();
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		//clearscreen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(splashImage,originOfImage.x, originOfImage.y);
		
		game.batch.end();
	}
	
	public void update(float delta){
		handleInput();
		
		timer += delta;
		
//		alpha += delta * alphaSpeed;
		
		if(timer > timerLimit) {
			game.setScreen(BuildUpGame.storyScreen);
		}
	}

	private void handleInput() {
		if(MyInput.isPressed(MyInput.SPACE)){
			game.setScreen(BuildUpGame.storyScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		
		
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
