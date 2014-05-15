package com.seligames.buildUp.entities.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.Constants;

public abstract class Spike extends GameEntity {

	protected float radius;
	protected float radiusTolarance = 8.f;

	public Spike(Vector2 pos) {
		super(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		this.radius = (float) (Constants.TILE_HEIGHT / 2 - radiusTolarance)
				/ Constants.PPM;
	}

	@Override
	public void setWorldAndCreateBody(World world) {

		this.world = world;

		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.StaticBody;
		
		bDef.position.set(position);

		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.isSensor = true;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_SPIKE;
		fDef.filter.maskBits = Constants.C_BLACKPLAYER
				| Constants.C_WHITEPLAYER;

		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);

		shape.dispose();

	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.begin();

		sb.draw(currentTexRegion, body.getPosition().x * Constants.PPM - radius
				* Constants.PPM - radiusTolarance, body.getPosition().y
				* Constants.PPM + radiusTolarance / 2f);

		sb.end();
	}
}
