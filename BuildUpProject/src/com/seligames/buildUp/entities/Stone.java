package com.seligames.buildUp.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;

public class Stone extends GameEntity{

	private Color color;
	private boolean active;
	
	public Stone(Vector2 pos, Color color) {
		this(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT, color);
	}
	
	public Stone(Vector2 pos, float width, float height, Color color) {
		super(pos, width, height);
		
		this.color = color;
		
//		if(color == Color.WHITE)
//			currentTexRegion = BuildUpGame.assetManager.get("res/whiteStone.png");
//		if(color == Color.BLACK)
//			currentTexRegion = BuildUpGame.assetManager.get("res/blackStone.png");
	}

	@Override
	public void setWorldAndCreateBody(World world) {
		this.world = world;
		
		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.StaticBody;
		bDef.position.set(position);
		
		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[5];
		v[0] = new Vector2(0, 0);
		v[1] = new Vector2(width / Constants.PPM, 0);
		v[2] = new Vector2(width / Constants.PPM, height / Constants.PPM);
		v[3] = new Vector2(0, height / Constants.PPM);
		v[4] = new Vector2(0, 0);
		shape.createChain(v);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.friction = .5f;
//		fDef.filter.categoryBits = Constants.C_STONE;
//		fDef.filter.maskBits = Constants.C_PLAYER;
		
		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);
		
		shape.dispose();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		
		if(color == Color.WHITE)
			currentTexRegion = BuildUpGame.assetManager.get("res/whiteStone.png");
		if(color == Color.BLACK)
			currentTexRegion = BuildUpGame.assetManager.get("res/blackStone.png");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
