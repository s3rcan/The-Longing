package com.seligames.buildUp.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.handlers.Animation;

public abstract class GameEntity {

	protected Vector2 position;
	protected float width;
	protected float height;
	protected Animation animation;
	protected TextureRegion currentTexRegion;

	protected Body body;
	protected World world;

	protected GameEntity(Vector2 pos, float width, float height) {
		this.position = new Vector2();
		this.position.x = (float) pos.x / Constants.PPM;
		this.position.y = (float) pos.y / Constants.PPM;
		this.width = width;
		this.height = height;
		this.animation = new Animation();
	}

	public abstract void setWorldAndCreateBody(World world);

	public void update(float delta) {
		if (animation != null) {
			animation.update(delta);
			currentTexRegion = animation.getFrame();
		}
	}

	public void render(SpriteBatch sb) {
		
		sb.begin();

		Vector2 pos = getRealPosition();
		sb.draw(currentTexRegion, pos.x * Constants.PPM, pos.y * Constants.PPM
				+ height / 2);

		sb.end();

	}

	public void setMaskBits(short bits) {
		Fixture f = body.getFixtureList().get(0);
		Filter ff = f.getFilterData();
		ff.maskBits = bits;
		f.setFilterData(ff);
		;
	}

	public void setCategoryBits(short bits) {
		Fixture f = body.getFixtureList().get(0);
		Filter ff = f.getFilterData();
		ff.categoryBits = bits;
		f.setFilterData(ff);
		;
	}

	/**
	 * Returns body position in Box2d world
	 * 
	 * @return
	 * 
	 */
	public Vector2 getBodyPosition() {
		return body.getPosition();
	}

	/**
	 * Returns body position multiplied with PPM
	 * 
	 * @return
	 */
	public Vector2 getRealPosition() {
		Vector2 pos = new Vector2();
		Vector2 bpos = body.getPosition();
		pos.x = bpos.x;
		pos.y = bpos.y;
		return pos;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public TextureRegion getCurrentTexRegion() {
		return currentTexRegion;
	}

	public Body getBody() {
		return body;
	}

	public void setBodyType(BodyType type) {
		body.setType(type);
	}
}