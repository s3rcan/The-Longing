package com.seligames.buildUp.entities.black;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.Direction;
import com.seligames.buildUp.entities.abstracts.Player;

public class BlackPlayer extends Player {

	public BlackPlayer(Vector2 pos, float radius) {
		super(pos, radius);

		this.jumpSpeed = 6.6f;
		Texture t = BuildUpGame.assetManager.get("res/images/blackBall.png");
		TextureRegion[] tr = new TextureRegion[1];
		tr[0] = new TextureRegion(t);

		animation.setFrames(tr);
	}

	@Override
	public void setWorldAndCreateBody(World world) {
		this.world = world;

		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(position);
		body = world.createBody(bDef);

		CircleShape shape = new CircleShape();
		shape.setRadius((float) (radius - 0.5) / Constants.PPM);

		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0.7f;
		fDef.friction = 0.9f;
		fDef.filter.categoryBits = Constants.C_BLACKPLAYER;
		fDef.filter.maskBits = Constants.C_BLACKSTONE | Constants.C_SPLITGROUND
				| Constants.C_SPIKE | Constants.C_FINISHING_OBJECT
				| Constants.C_WORLDWRAP;

		body.createFixture(fDef).setUserData(this);
		body.setAngularDamping(5.f);

		this.gravity = new Vector2(0, -Constants.W_GRAVITY * body.getMass());
		shape.dispose();
	}

	public void move(Direction dir) {
		switch (dir) {
		case LEFT:
			body.applyForceToCenter(-4, 0, true);
			break;
		case RIGHT:
			body.applyForceToCenter(4, 0, true);
			break;
		default:
			break;
		}
	}
}
