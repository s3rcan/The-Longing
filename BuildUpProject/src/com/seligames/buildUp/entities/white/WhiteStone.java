package com.seligames.buildUp.entities.white;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.abstracts.Stone;

public class WhiteStone extends Stone{
	
	public WhiteStone(Vector2 pos) {
		this(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
	}
	
	public WhiteStone(Vector2 pos, float width, float height) {
		super(pos, width, height);
		TextureRegion[] tr = new TextureRegion[2];
		
		Texture t = BuildUpGame.assetManager.get("res/whiteStone.png");
		tr[0] = new TextureRegion(t);
		
		t = BuildUpGame.assetManager.get("res/whiteStoneFaded.png");
		tr[1] = new TextureRegion(t);

		animation.setFrames(tr);
		
		this.active = false;
		currentTexRegion = animation.get(1);
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
		fDef.filter.categoryBits = Constants.C_WHITESTONE;
		fDef.filter.maskBits = Constants.C_WHITEPLAYER;
		
		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);
		
		shape.dispose();
	}
}
