package com.seligames.buildUp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seligames.buildUp.entities.Direction;
import com.seligames.buildUp.handlers.BackgroundLayer;
import com.seligames.buildUp.handlers.MyInput;
import com.seligames.buildUp.handlers.MyInputProcessor;
import com.seligames.buildUp.handlers.SoundLib;
import com.seligames.buildUp.scenes.CreditsScreen;
import com.seligames.buildUp.scenes.LevelSelectScreen;
import com.seligames.buildUp.scenes.MainMenuScreen;
import com.seligames.buildUp.scenes.PlayScreen;
import com.seligames.buildUp.scenes.SplashScreen;
import com.seligames.buildUp.scenes.StoryScreen;

public class BuildUpGame extends Game{
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public static AssetManager assetManager;
	
	public static PlayScreen playScene;
	public static MainMenuScreen mainMenuScene;
	public static SplashScreen splashScene;
	public static LevelSelectScreen levelScene;
	public static CreditsScreen creditsScreen;
	public static StoryScreen storyScreen;
	
	public BackgroundLayer staticBackground;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);
		
		batch = new SpriteBatch();
		
		assetManager = new AssetManager();
		
		//Asset loading goes here
		assetManager.load("res/images/splash.png", Texture.class);
		//whiteanim
		assetManager.load("res/images/whiteBallAnim.png", Texture.class);
		//blackBall
		assetManager.load("res/images/blackBall.png", Texture.class);
		
		assetManager.load("res/images/ground.png", Texture.class);
		
		assetManager.load("res/whiteSpike.png", Texture.class);
		assetManager.load("res/blackSpike.png", Texture.class);
		
		assetManager.load("res/blackStone.png", Texture.class);
		assetManager.load("res/whiteStone.png", Texture.class);
		//button
		assetManager.load("res/images/button1.png", Texture.class);
		
		assetManager.load("res/images/tutorial.png", Texture.class);
		
		assetManager.load("res/images/background1.png", Texture.class);
		
		assetManager.load("res/blackStoneFaded.png", Texture.class);
		assetManager.load("res/whiteStoneFaded.png", Texture.class);
		
		assetManager.load("res/images/crystal.png", Texture.class);
		
		assetManager.load("res/images/menu.png", Texture.class);
		assetManager.load("res/images/story.png", Texture.class);
		
		//Musics
		
		assetManager.load("res/music/SplashScreen.wav", Music.class);
		assetManager.load("res/music/bgv3.wav", Music.class);
		assetManager.load("res/music/bgv2.wav", Music.class);
		assetManager.load("res/music/CloudDeathv2.wav", Music.class);
		assetManager.load("res/music/CloudMoveWaterSplashv2.wav", Music.class);
//		assetManager.load("res/music/GameOver.wav", Music.class);
		assetManager.load("res/music/intro.wav", Music.class);
		assetManager.load("res/music/PickUpv2.wav", Music.class);
		assetManager.load("res/music/RockDeath.wav", Music.class);
		assetManager.load("res/music/RockMove.wav", Music.class);
//		assetManager.load("res/music/Rubble_FallDown.wav", Music.class);
//		assetManager.load("res/music/Windball_move_rainycloud.wav", Music.class);
		assetManager.load("res/music/splashv6.wav", Music.class);

		
		//Block until assets loaded
		assetManager.finishLoading();
		
		
		SoundLib.splashScreen = assetManager.get("res/music/SplashScreen.wav");
		SoundLib.bgv3 = assetManager.get("res/music/bgv3.wav");
		SoundLib.bgv2 = assetManager.get("res/music/bgv2.wav");
		SoundLib.CloudDeathv2 = assetManager.get("res/music/CloudDeathv2.wav");
		SoundLib.CloudMoveWaterSplashv2 = assetManager.get("res/music/CloudMoveWaterSplashv2.wav");
//		SoundLib.GameOver = assetManager.get("res/music/GameOver.wav");
		SoundLib.intro = assetManager.get("res/music/intro.wav");
		SoundLib.PickUpv2 = assetManager.get("res/music/PickUpv2.wav");
		SoundLib.RockDeath = assetManager.get("res/music/RockDeath.wav");
		SoundLib.RockMove = assetManager.get("res/music/RockMove.wav");
//		SoundLib.Rubble_FallDown = assetManager.get("res/music/Rubble_FallDown.wav");
//		SoundLib.Windball_move_rainycloud = assetManager.get("res/music/Windball_move_rainycloud.wav");
		SoundLib.splashv6 = assetManager.get("res/music/splashv6.wav");

		SoundLib.init();
		
		staticBackground = new BackgroundLayer(
				(Texture) BuildUpGame.assetManager
						.get("res/images/background1.png"),
				30.f, Direction.LEFT);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		splashScene = new SplashScreen(this);
		playScene = new PlayScreen(this);
		mainMenuScene = new MainMenuScreen(this);
		levelScene = new LevelSelectScreen(this);
		creditsScreen = new CreditsScreen(this);
		storyScreen = new StoryScreen(this);
		
		setScreen(splashScene);
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		assetManager.dispose();
	}

	@Override
	public void render() {
		Gdx.graphics.setTitle(Constants.gameName + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
		super.render();
		MyInput.update();//TODO TRYING THIS
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
