package com.seligames.buildUp.entities.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.Direction;

public abstract class Player extends GameEntity{
	
	protected float radius;
	protected float jumpSpeed;
	protected Vector2 gravity;
	
	public Player(Vector2 pos, float radius) {
		super(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		this.radius = (float)radius / 2;
	}
	
	
	@Override
	public abstract void setWorldAndCreateBody(World world);

	public void jump() {
		body.setLinearVelocity(body.getLinearVelocity().x, jumpSpeed);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		body.applyForceToCenter(gravity, true);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		
		sb.draw(currentTexRegion,
				body.getPosition().x * Constants.PPM - radius / 2,
				body.getPosition().y * Constants.PPM + radius / 2,
				radius / 2,
				radius / 2,
				radius ,
				radius ,
				2,
				2,
				body.getAngle() * Constants.PPM);
		
		sb.end();
	}
	
	public void setBodyPositionAndAngle(Vector2 pos, float angle) {
		body.setTransform(pos, angle);
	}
	
	public abstract void move(Direction dir);
}
