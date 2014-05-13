package com.seligames.buildUp.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.Direction;



public class BackgroundLayer {

	private Texture texture;
	private float movingSpeed;
	private Direction dir;
	private float shift;
	private float currentLoc;
	private boolean autoUpdate;
	private boolean moved;
	
	public BackgroundLayer(Texture textureRegion, float speed, Direction dir) {
		this.texture = textureRegion;
		this.movingSpeed = speed;
		this.dir = dir;
		currentLoc = 0;
		autoUpdate = true;
		updateShift();
	}
	
	private void updateShift() {
		float temp = 0;
		
		switch (dir) {
		case LEFT:
			temp = texture.getWidth();
			break;
		case RIGHT:
			temp = -texture.getWidth();
			break;
		case UP:
			temp = -texture.getHeight();
			break;
		case DOWN:
			temp = texture.getHeight();
			break;
		default:
			break;
		}
		
		shift = temp;
	}

	public void update(float delta){
		if(autoUpdate || moved) {
			switch (dir) {
			case LEFT:
				currentLoc -= movingSpeed * delta;
				if(currentLoc < -Constants.V_WIDTH) {
					currentLoc = 0;
					updateShift();
				}
				break;
			case RIGHT:
				currentLoc += movingSpeed * delta;
				if(currentLoc > Constants.V_WIDTH) {
					currentLoc = 0;
					updateShift();
				}
				break;
			case UP:
				currentLoc += movingSpeed * delta;
				if(currentLoc > Constants.V_HEIGHT) {
					currentLoc = 0;
					updateShift();
				}
				break;
			case DOWN:
				if(currentLoc < -Constants.V_HEIGHT) {
					currentLoc = 0;
					updateShift();
				}
				currentLoc -= movingSpeed * delta;
				break;
			default:
				break;
			}
			moved = false;
		}
		updateShift();
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		float shiftY = 0; // helf of the bacgroiund 
		if(dir == Direction.LEFT || dir == Direction.RIGHT) {
			sb.draw(texture, currentLoc, shiftY);
			sb.draw(texture, currentLoc + shift, shiftY);
			sb.draw(texture, currentLoc - shift, shiftY);
		}else {
			sb.draw(texture, shiftY, currentLoc);
			sb.draw(texture, shiftY, currentLoc + shift);
			sb.draw(texture, shiftY, currentLoc - shift);
		}
		
		sb.end();
	}
	
	public void move(Direction dir, float speed) {
		this.dir = dir;
		this.movingSpeed = speed;
		this.moved = true;
	}
	
	public void move(Direction dir) {
		this.dir = dir;
		this.moved = true;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	
}
