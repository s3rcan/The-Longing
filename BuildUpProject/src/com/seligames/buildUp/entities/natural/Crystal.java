package com.seligames.buildUp.entities.natural;

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
import com.seligames.buildUp.entities.abstracts.GameEntity;

public class Crystal extends GameEntity{

	private float rotationSpeed;
	private float angle;
	private float radius;
	
	public Crystal(Vector2 pos) {
		super(pos,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		
		Texture t = (Texture) BuildUpGame.assetManager.get("res/images/crystal.png");
		TextureRegion tr = new TextureRegion(t);
		this.angle = 0;
		this.rotationSpeed = 0.5f;
		this.radius = Constants.TILE_HEIGHT;
		currentTexRegion = tr;
	}

	@Override
	public void setWorldAndCreateBody(World world) {
		
		this.world = world;
		
		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyType.StaticBody;
		bDef.position.set(position);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / Constants.PPM / 2);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 5.f;
		fDef.friction = .5f;
		fDef.filter.categoryBits = Constants.C_FINISHING_OBJECT;
		fDef.filter.maskBits = Constants.C_BLACKPLAYER | Constants.C_WHITEPLAYER;
		
		body = world.createBody(bDef);
		body.createFixture(fDef).setUserData(this);
		
		shape.dispose();
		
	}
	
	@Override
	public void update(float delta) {
		angle += rotationSpeed * delta;
		if(angle > 360) {
			angle = 0;
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		
		sb.draw(currentTexRegion,
				body.getPosition().x * Constants.PPM - radius / 2,
				body.getPosition().y * Constants.PPM ,
				radius / 2,
				radius / 2,
				radius ,
				radius ,
				1,
				1,
				this.angle * Constants.PPM);
		
		sb.end();
	}

}
