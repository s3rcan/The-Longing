package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.MyInput;
import com.seligames.buildUp.handlers.SoundLib;

public class StoryScreen implements Screen{
	
	private BuildUpGame game;
	private Texture storyTexture;
	
	private Vector2 originOfImage;
	
	private float delay;
	
	public StoryScreen(BuildUpGame game){
		this.game = game;
		
		storyTexture = BuildUpGame.assetManager.get("res/images/story.png");
		originOfImage = new Vector2();
		originOfImage.x = (float) (Constants.V_WIDTH - storyTexture.getWidth()) / 2;
		originOfImage.y = (float) (Constants.V_HEIGHT - storyTexture.getHeight()) / 2;
	}
	
	@Override
	public void show() {
		SoundLib.splashScreen.stop();
		delay = 3f;
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		//clearscreen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(storyTexture,0, 0);
		
		game.batch.end();
	}
	
	public void update(float delta){
		handleInput();
		delay -= delta;
		
		if(delay > 0) 
			return;
		
		if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			game.setScreen(BuildUpGame.mainMenuScene);
		}
	}

	private void handleInput() {
		if(MyInput.isPressed(MyInput.SPACE)){
			game.setScreen(BuildUpGame.mainMenuScene);
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
