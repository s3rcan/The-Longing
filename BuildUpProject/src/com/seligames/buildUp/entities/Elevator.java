package com.seligames.buildUp.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.Constants;

public class Elevator extends GameEntity{

//	private int size;
	private float distance;
	private float startPoint;
	private float endPoint;
	private boolean movingDown;
	private boolean movingUp;
	
	public Elevator(Vector2 pos) {
		super(pos, Constants.TILE_HEIGHT, Constants.TILE_WIDTH);
//		this.size = 5;
		this.distance = 10.f / Constants.PPM;
		this.startPoint = body.getPosition().y; 
		this.endPoint = body.getPosition().y + distance;
		this.movingUp = true;
		this.movingDown = false;
	}
	
	@Override
	public void setWorldAndCreateBody(World world) {
		
		this.world = world;
		
		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.KinematicBody;
		bDef.position.set(position);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Constants.TILE_WIDTH / Constants.PPM / 2, Constants.TILE_HEIGHT / Constants.PPM / 2);		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_CLOUD;
		fDef.filter.maskBits = Constants.C_WHITEPLAYER | Constants.C_BLACKPLAYER;
		
		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);
		
		shape.dispose();

	}
	
	public void update(){

		if(movingUp){
			if(body.getPosition().y < endPoint){
				body.setLinearVelocity(0,0.2f);
			}
			else{
				movingUp = false;
				movingDown = true;
			}
		}
		
		if(movingDown){
			if(body.getPosition().y > startPoint){
				body.setLinearVelocity(0,-0.2f);
			}
			else{
				movingUp = true;
				movingDown = false;
			}
		}
		
	}

}
