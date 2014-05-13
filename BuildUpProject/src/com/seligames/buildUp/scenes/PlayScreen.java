package com.seligames.buildUp.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.BlackPlayer;
import com.seligames.buildUp.entities.BlackStone;
import com.seligames.buildUp.entities.Color;
import com.seligames.buildUp.entities.Crystal;
import com.seligames.buildUp.entities.Direction;
import com.seligames.buildUp.entities.Spike;
import com.seligames.buildUp.entities.SplitGround;
import com.seligames.buildUp.entities.WhitePlayer;
import com.seligames.buildUp.entities.WhiteStone;
import com.seligames.buildUp.handlers.BackgroundLayer;
import com.seligames.buildUp.handlers.MyContactListener;
import com.seligames.buildUp.handlers.MyInput;
import com.seligames.buildUp.handlers.SoundLib;

public class PlayScreen implements Screen {

	private boolean debugMode = false;
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera box2dcam;
	// private OrthographicCamera cam;
	private TiledMap map;
	// private OrthogonalTiledMapRenderer tmr;

	private WhitePlayer whitePlayer;
	private BlackPlayer blackPlayer;
	private Array<SplitGround> grounds;

	private Array<WhiteStone> whiteStones;
	private Array<BlackStone> blackStones;

	private Array<Spike> whiteSpikes;
	private Array<Spike> blackSpikes;
	private Array<Crystal> crystals;

	private BitmapFont initialFont;

	private BuildUpGame game;

	private boolean paused;

	private MyContactListener contactListener;

	private int currentLevel;

	private boolean whiteActive;

	private OrthographicCamera cam;
	private SpriteBatch sb;

	private BackgroundLayer backgroundLayer;

	private float mapWidth;
	private float mapHeight;
	
	private float tileCountY;
	private float shift;
	private boolean loadMap;
	private boolean gameFinished;

	public PlayScreen(BuildUpGame game) {
		this.game = game;
		this.paused = false;
		initialFont = new BitmapFont();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Constants.V_WIDTH * Constants.ZOOM,
				Constants.V_HEIGHT * Constants.ZOOM);

		currentLevel = 0;

		sb = new SpriteBatch();

		backgroundLayer = new BackgroundLayer(
				(Texture) BuildUpGame.assetManager
						.get("res/images/background1.png"),
				1.f, Direction.LEFT);
		backgroundLayer.setAutoUpdate(false);

//		loadMap(currentLevel);

		if (debugMode) {
			box2dcam = new OrthographicCamera();
			box2dcam.setToOrtho(false, Constants.V_WIDTH / Constants.PPM
					* Constants.ZOOM, Constants.V_HEIGHT / Constants.PPM
					* Constants.ZOOM);
		}
	}

	@Override
	public void show() {
		
		SoundLib.intro.stop();
		gameFinished = false;
		loadMap = false;
	}

	public void loadMap(int level) {
		currentLevel = level;

		whiteActive = false;
		world = new World(new Vector2(0, 0), true);

		contactListener = new MyContactListener();
		world.setContactListener(contactListener);

		b2dr = new Box2DDebugRenderer();
		game.camera.setToOrtho(false, Constants.V_WIDTH, Constants.V_HEIGHT);

		
		if (currentLevel % 2 == 0) {
			SoundLib.bgv3.stop();
			SoundLib.bgv2.play();
			
		}
		if (currentLevel % 2 == 1) {
			SoundLib.bgv2.stop();
			SoundLib.bgv3.play();
		}
		
		
		// create map
		createMap(level);
		Array<Body> bodies = new Array<Body>();

		world.getBodies(bodies);
		shift = (float) ((11 - tileCountY) / Constants.PPM);

		if (shift < 0)
			return;
		for (int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			Vector2 v = b.getPosition();
			float angle = b.getAngle();

			b.setTransform(v.x, v.y + shift, angle);
		}
	}

	@Override
	public void render(float delta) {

		if (paused && contactListener.isPlayerDead()) {
			game.batch.begin();

			initialFont.draw(game.batch, "YOU DIED!",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2 + 64);
			initialFont.draw(game.batch, "Press ESC to go to Main Menu.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2);
			initialFont.draw(game.batch, "Press R to retry.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2 - 64);

			game.batch.end();

			if (MyInput.isPressed(MyInput.ESC)) {
				paused = false;
				loadMap(0);
				game.setScreen(BuildUpGame.mainMenuScene);
			}
			if (MyInput.isPressed(MyInput.R)) {
				paused = false;
				loadMap(currentLevel);
			}

			return;
		}
		if (paused && gameFinished) {
			game.batch.begin();

			initialFont.draw(game.batch, "YOU HAVE FINISHED THE GAME!",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2 + 64);
			initialFont.draw(game.batch, "Press ESC to continue.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2);

			game.batch.end();

			if (MyInput.isPressed(MyInput.ESC)) {
				paused = false;
				loadMap(0);
				game.setScreen(BuildUpGame.creditsScreen);
			}
			return;
		}
		// pause
		if (paused) {
			game.batch.begin();

			initialFont.draw(game.batch, "PAUSED!", Constants.V_WIDTH / 2 - 64,
					Constants.V_HEIGHT / 2 + 64);
			initialFont.draw(game.batch, "Press ESC to go to Main Menu.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2);
			initialFont.draw(game.batch, "Press P to resume.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2 - 64);
			initialFont.draw(game.batch, "Press R to retry.",
					Constants.V_WIDTH / 2 - 64, Constants.V_HEIGHT / 2 - 128);

			game.batch.end();
			if (MyInput.isPressed(MyInput.ESC)) {
				paused = false;
				loadMap(0);
				game.setScreen(BuildUpGame.mainMenuScene);
			}
			if (MyInput.isPressed(MyInput.P)) {
				paused = false;
			}
			if (MyInput.isPressed(MyInput.R)) {
				paused = false;
				loadMap(currentLevel);
			}

			return;
		}

		if (contactListener.loadNextLevel()) {
			if (currentLevel + 1 == Constants.LEVEL_COUNT) {
				gameFinished = true;
				paused = true;
				return;
			}
			loadNextLevel();
			return;
		}
		if (contactListener.isPlayerDead()) {
			
			playerHit();
			
			return;
		}
		// update
		update(delta);

		// clear screen
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (debugMode) {
			box2dcam.position.set(whitePlayer.getBodyPosition().x,
					whitePlayer.getBodyPosition().y, 0);
			box2dcam.update();
		}

		game.camera.setToOrtho(false, Constants.V_WIDTH * Constants.ZOOM,
				Constants.V_HEIGHT * Constants.ZOOM);
		sb.setProjectionMatrix(game.camera.combined);
		backgroundLayer.render(sb);

		centerCamera();

		sb.setProjectionMatrix(cam.combined);

		for (int i = 0; i < blackStones.size; i++)
			blackStones.get(i).render(sb);

		for (int i = 0; i < whiteStones.size; i++)
			whiteStones.get(i).render(sb);

		for (int i = 0; i < grounds.size; i++)
			grounds.get(i).render(sb);

		for (int i = 0; i < blackSpikes.size; i++)
			blackSpikes.get(i).render(sb);

		for (int i = 0; i < whiteSpikes.size; i++)
			whiteSpikes.get(i).render(sb);

		for (int i = 0; i < crystals.size; i++)
			crystals.get(i).render(sb);

		whitePlayer.render(sb);
		blackPlayer.render(sb);

		// tmr.setView(cam);
		// tmr.render();
		if (debugMode)
			b2dr.render(world, box2dcam.combined);
	}

	private void centerCamera() {
		float posX = blackPlayer.getBodyPosition().x * Constants.PPM;
		float scale = (blackPlayer.getBodyPosition().y - whitePlayer
				.getBodyPosition().y) * Constants.PPM / 64f;

		scale = (float) Math.pow(scale, 1 / 2f);

		scale /= 2;
		if (scale < 1) {
			scale = 1;
		} else if (scale > 3) {
			scale = 3;
		}

		cam.setToOrtho(false, Constants.V_WIDTH * scale, Constants.V_HEIGHT
				* scale);

		if (posX - cam.viewportHeight / 2 < 0) {
			posX = cam.viewportWidth / 2f;
		} else if (posX + cam.viewportWidth / 2 + 64 > mapWidth) {
			posX = mapWidth - cam.viewportHeight / 2f - 64;
		} else {
			float vel = blackPlayer.getBody().getLinearVelocity().x;
			if (vel > 0)
				backgroundLayer.move(Direction.LEFT, vel * Constants.PPM / 4);
			else if (vel == 0) {

			} else
				backgroundLayer
						.move(Direction.RIGHT, -vel * Constants.PPM / 4);
		}

		cam.position.set(posX, mapHeight / 2, 0);
		cam.update();
	}

	private void update(float delta) {

		handleInput();

		whitePlayer.update(delta);
		blackPlayer.update(delta);

		if (whiteActive && contactListener.isWPGrounded()
				&& !SoundLib.splashv6.isPlaying()) {
			SoundLib.splashv6.play();
		}
		if (whiteActive && !contactListener.isWPGrounded()) {
			SoundLib.splashv6.stop();
		}

		if (!whiteActive && contactListener.isBPGrounded()
				&& !SoundLib.RockMove.isPlaying()) {
			SoundLib.RockMove.play();
		}
		if (!whiteActive && !contactListener.isBPGrounded()) {
			SoundLib.RockMove.stop();
		}

		world.step(delta, 6, 2);

		Vector2 pos;
		if (whiteActive) {
			pos = whitePlayer.getBodyPosition();
			pos.y = (float) (mapHeight - Constants.TILE_HEIGHT) / Constants.PPM
					- pos.y;
			blackPlayer.setBodyPositionAndAngle(pos, whitePlayer.getBody()
					.getAngle());
			blackPlayer.getBody().setLinearVelocity(
					whitePlayer.getBody().getLinearVelocity().x,
					-whitePlayer.getBody().getLinearVelocity().y);
		} else {
			pos = blackPlayer.getBodyPosition();
			pos.y = (float) (mapHeight - Constants.TILE_HEIGHT) / Constants.PPM
					- pos.y;
			whitePlayer.setBodyPositionAndAngle(pos, blackPlayer.getBody()
					.getAngle());
			whitePlayer.getBody().setLinearVelocity(
					blackPlayer.getBody().getLinearVelocity().x,
					-blackPlayer.getBody().getLinearVelocity().y);
		}

		// for (int i = 0; i < blackStones.size; i++) {
		// blackStones.get(i).update(delta);
		// }
		//
		// for (int i = 0; i < whiteStones.size; i++)
		// whiteStones.get(i).update(delta);

		for (int i = 0; i < blackSpikes.size; i++)
			blackSpikes.get(i).update(delta);

		for (int i = 0; i < whiteSpikes.size; i++)
			whiteSpikes.get(i).update(delta);

		for (int i = 0; i < crystals.size; i++)
			crystals.get(i).update(delta);

		backgroundLayer.update(delta);
	}

	private void handleInput() {
		if (MyInput.isPressed(MyInput.P) || MyInput.isPressed(MyInput.ESC)) {
			paused = true;
		}
		if (MyInput.isPressed(MyInput.Z)) {
			for (int i = 0; i < blackStones.size; i++) {
				blackStones.get(i).toggleActive();
			}
			for (int i = 0; i < whiteStones.size; i++) {
				whiteStones.get(i).toggleActive();
			}
			whiteActive = !whiteActive;
		}

		if (MyInput.isDown(MyInput.RIGHT)) {
			blackPlayer.move(Direction.RIGHT);
			whitePlayer.move(Direction.RIGHT);
		}
		if (MyInput.isDown(MyInput.LEFT)) {
			blackPlayer.move(Direction.LEFT);
			whitePlayer.move(Direction.LEFT);
		}
		if (MyInput.isPressed(MyInput.SPACE)) {
			if (contactListener.isBPGrounded() && !whiteActive) {
				blackPlayer.jump();
				whitePlayer.jump();
			}
			if (contactListener.isWPGrounded() && whiteActive) {
				blackPlayer.jump();
				whitePlayer.jump();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		world.dispose();

	}

	private void createPlayers() {
		MapLayer layer;
		layer = (MapLayer) map.getLayers().get("player");

		float row = (Float) layer.getObjects().get(0).getProperties().get("x")
				/ Constants.PPM;
		float col = (Float) layer.getObjects().get(0).getProperties().get("y")
				/ Constants.PPM;

		blackPlayer = new BlackPlayer(new Vector2(col, mapHeight - row),
				Constants.TILE_HEIGHT - 3);
		whitePlayer = new WhitePlayer(new Vector2(col, row),
				Constants.TILE_HEIGHT - 3);

		blackPlayer.setWorldAndCreateBody(world);
		whitePlayer.setWorldAndCreateBody(world);
	}

	private void createMap(int level) {
		map = new TmxMapLoader().load("res/maps/level" + level + ".tmx");
		// tmr = new OrthogonalTiledMapRenderer(map);

		TiledMapTileLayer layer;
		layer = (TiledMapTileLayer) map.getLayers().get("split");

		float row1 = (tileCountY = layer.getHeight()) * Constants.TILE_HEIGHT;
		float col1 = layer.getWidth() * Constants.TILE_HEIGHT;
		mapWidth = col1;
		mapHeight = row1;
		// wrap the world

		wrapWorld(row1, col1);

		createPlayers();

		layer = (TiledMapTileLayer) map.getLayers().get("split");
		grounds = new Array<SplitGround>();
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {

				// get cell
				Cell cell = layer.getCell(col, row);

				// check that there is a cell
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;
				SplitGround split = new SplitGround(new Vector2((col - 0.5f)
						* Constants.TILE_HEIGHT, (row) * Constants.TILE_HEIGHT));
				split.setWorldAndCreateBody(world);
				grounds.add(split);
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get("blackStones");
		blackStones = new Array<BlackStone>();
		if (layer != null) {
			if (layer != null) {
				for (int row = 0; row < layer.getHeight(); row++) {
					for (int col = 0; col < layer.getWidth(); col++) {

						// get cell
						Cell cell = layer.getCell(col, row);

						// check that there is a cell
						if (cell == null)
							continue;
						if (cell.getTile() == null)
							continue;
						BlackStone blackStone = new BlackStone(new Vector2(
								(col - 0.5f) * Constants.TILE_HEIGHT,
								(row - 0.5f) * Constants.TILE_HEIGHT));
						blackStone.setWorldAndCreateBody(world);
						blackStones.add(blackStone);
					}
				}
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get("whiteStones");
		whiteStones = new Array<WhiteStone>();
		if (layer != null) {
			for (int row = 0; row < layer.getHeight(); row++) {
				for (int col = 0; col < layer.getWidth(); col++) {

					// get cell
					Cell cell = layer.getCell(col, row);

					// check that there is a cell
					if (cell == null)
						continue;
					if (cell.getTile() == null)
						continue;

					WhiteStone whiteStone = new WhiteStone(new Vector2(
							(col - .5f) * Constants.TILE_HEIGHT, (row - .5f)
									* Constants.TILE_HEIGHT));
					whiteStone.setWorldAndCreateBody(world);
					whiteStones.add(whiteStone);
				}
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get("whiteSpikes");
		whiteSpikes = new Array<Spike>();
		if (layer != null) {
			for (int row = 0; row < layer.getHeight(); row++) {
				for (int col = 0; col < layer.getWidth(); col++) {

					// get cell
					Cell cell = layer.getCell(col, row);

					// check that there is a cell
					if (cell == null)
						continue;
					if (cell.getTile() == null)
						continue;

					Spike spike = new Spike(new Vector2((col)
							* Constants.TILE_HEIGHT, row
							* Constants.TILE_HEIGHT), Color.WHITE);
					spike.setWorldAndCreateBody(world);
					whiteSpikes.add(spike);
				}
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get("blackSpikes");
		blackSpikes = new Array<Spike>();
		if (layer != null) {
			for (int row = 0; row < layer.getHeight(); row++) {
				for (int col = 0; col < layer.getWidth(); col++) {

					// get cell
					Cell cell = layer.getCell(col, row);

					// check that there is a cell
					if (cell == null)
						continue;
					if (cell.getTile() == null)
						continue;

					Spike spike = new Spike(new Vector2((col)
							* Constants.TILE_HEIGHT, (row)
							* Constants.TILE_HEIGHT), Color.BLACK);
					spike.setWorldAndCreateBody(world);
					blackSpikes.add(spike);
				}
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get("crystal");
		crystals = new Array<Crystal>();
		if (layer != null) {

			for (int row = 0; row < layer.getHeight(); row++) {
				for (int col = 0; col < layer.getWidth(); col++) {

					// get cell
					Cell cell = layer.getCell(col, row);

					// check that there is a cell
					if (cell == null)
						continue;
					if (cell.getTile() == null)
						continue;

					Crystal fo = new Crystal(new Vector2((col)
							* Constants.TILE_HEIGHT, row
							* Constants.TILE_HEIGHT));
					fo.setWorldAndCreateBody(world);
					crystals.add(fo);
				}
			}
		}

		// layer = (TiledMapTileLayer) map.getLayers().get("elevator");
		//
		// for ( int row = 0; row < layer.getHeight(); row++) {
		// for (int col = 0; col < layer.getWidth(); col++) {
		//
		// // get cell
		// Cell cell = layer.getCell(col, row);
		//
		// // check that there is a cell
		// if (cell == null)
		// continue;
		// if (cell.getTile() == null)
		// continue;
		//
		// Elevator elevator = new Elevator(new Vector2((col + 0.5f)
		// * Constants.TILE_HEIGHT, (row + 0.5f)
		// * Constants.TILE_HEIGHT));
		// elevator.setWorldAndCreateBody(world);
		// }
		// }

	}

	private void wrapWorld(float row1, float col1) {
		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.StaticBody;
		bDef.position.set(new Vector2(0, 0));

		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0, 0);
		v[1] = new Vector2(0, mapHeight / Constants.PPM);
		shape.createChain(v);

		ChainShape shape2 = new ChainShape();
		v[0] = new Vector2((mapWidth - Constants.TILE_HEIGHT) / Constants.PPM,
				0);
		v[1] = new Vector2((mapWidth - Constants.TILE_HEIGHT) / Constants.PPM,
				mapHeight / Constants.PPM);
		shape2.createChain(v);

		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_WORLDWRAP;
		fDef.filter.maskBits = Constants.C_BLACKPLAYER
				| Constants.C_WHITEPLAYER;

		world.createBody(bDef).createFixture(fDef);

		fDef = new FixtureDef();
		fDef.shape = shape2;
		fDef.density = 5.f;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_WORLDWRAP;
		fDef.filter.maskBits = Constants.C_BLACKPLAYER
				| Constants.C_WHITEPLAYER;

		world.createBody(bDef).createFixture(fDef);

		shape.dispose();
		shape2.dispose();
	}

	public int getMapWidth() {
		return (Integer) map.getProperties().get("width");
	}

	public int getMapHeight() {
		return (Integer) map.getProperties().get("height");
	}

	public void playerHit() {
		paused = true;
		if (loadMap) {
			loadMap(currentLevel);
		}
	}

	public void loadNextLevel() {
		paused = true;
		loadMap(++currentLevel);
		SoundLib.PickUpv2.play();
		paused = false;
	}

}
