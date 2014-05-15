package com.seligames.buildUp.entities.white;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class WhitePlayer extends Player {

	public WhitePlayer(Vector2 pos, float radius) {
		super(pos, radius);
		
		this.jumpSpeed = -7.0f;

		Texture t = BuildUpGame.assetManager.get("res/images/whiteBallAnim.png");
		TextureRegion[] tr = TextureRegion.split(t, 64, 64)[0];
		animation.setFrames(tr, 1 / 12f);
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
		fDef.density = 0.2f;
		fDef.friction = 0.2f;
		fDef.filter.categoryBits = Constants.C_WHITEPLAYER;
		fDef.filter.maskBits = Constants.C_WHITESTONE | Constants.C_SPLITGROUND
				| Constants.C_SPIKE | Constants.C_FINISHING_OBJECT | Constants.C_WORLDWRAP;

		body.createFixture(fDef).setUserData(this);
		
		body.setAngularDamping(1.f);
		body.setFixedRotation(true);
		this.gravity = new Vector2(0, Constants.W_GRAVITY * body.getMass());
		shape.dispose();
	}

	public void move(Direction dir) {
		switch (dir) {
		case LEFT:
			body.applyForceToCenter(-1, 0, true);
			break;
		case RIGHT:
			body.applyForceToCenter(1, 0, true);
			break;
		default:
			break;
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		
		sb.draw(currentTexRegion, body.getPosition().x * Constants.PPM - radius, body.getPosition().y * Constants.PPM);
		
		sb.end();
	}

	public void setBodyPositionAndAngle(Vector2 pos, float angle) {
		body.setTransform(pos, angle);
	}

}
