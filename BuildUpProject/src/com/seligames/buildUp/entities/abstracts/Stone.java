package com.seligames.buildUp.entities.abstracts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.seligames.buildUp.Constants;

public abstract class Stone extends GameEntity{
	protected boolean active;
	
	public Stone(Vector2 pos) {
		this(pos, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
	}
	
	public Stone(Vector2 pos, float width, float height) {
		super(pos, width, height);
		this.active = false;
	}

	@Override
	public abstract void setWorldAndCreateBody(World world);

	public boolean isActive() {
		return active;
	}

	public void toggleActive() {
		this.active = !active;
		
		if(active)
			currentTexRegion = animation.get(0);
		else
			currentTexRegion = animation.get(1);
	}
}
