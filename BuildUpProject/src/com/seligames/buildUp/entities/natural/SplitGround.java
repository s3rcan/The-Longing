package com.seligames.buildUp.entities.natural;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.abstracts.GameEntity;

public class SplitGround extends GameEntity {

	public SplitGround(Vector2 pos) {
		super(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);

		 currentTexRegion = TextureRegion.split((Texture)BuildUpGame.assetManager.get("res/images/ground.png"), 64, 64)[0][0];
		
	}

	@Override
	public void setWorldAndCreateBody(World world) {
		this.world = world;

		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.StaticBody;
		bDef.position.set(position);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / Constants.PPM / 2, height / Constants.PPM / 2);

		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_SPLITGROUND;
		fDef.filter.maskBits = Constants.C_BLACKPLAYER | Constants.C_WHITEPLAYER;

		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);

		shape.dispose();
	}
	@Override
	public void update(float delta) {
		
	}
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		
		sb.draw(currentTexRegion, (position.x * Constants.PPM) - width /2, position.y * Constants.PPM);

		sb.end();
	}

}
