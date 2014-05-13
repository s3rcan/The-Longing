package com.seligames.buildUp.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter{
	
	public boolean mouseMoved(int x, int y){
		MyInput.x = x;
		MyInput.y = y;
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = false;
		return true;
	}
	
	public boolean keyDown(int k){
		setKey(k, true);
		return true;
	}
	
	public boolean keyUp(int k){
		setKey(k, false);
		return true;
	}
	
	private void setKey(int k, boolean bool) {
		if(k == Keys.X){
			MyInput.setKey(MyInput.SPACE,bool);
		}
		if(k == Keys.UP){
			MyInput.setKey(MyInput.UP,bool);
		}
		if(k == Keys.DOWN){
			MyInput.setKey(MyInput.DOWN,bool);
		}
		if(k == Keys.RIGHT){
			MyInput.setKey(MyInput.RIGHT,bool);
		}
		if(k == Keys.LEFT){
			MyInput.setKey(MyInput.LEFT,bool);
		}
		if(k == Keys.P){
			MyInput.setKey(MyInput.P,bool);
		}
		if(k == Keys.Z){
			MyInput.setKey(MyInput.Z,bool);
		}
		if(k == Keys.ENTER){
			MyInput.setKey(MyInput.ENTER,bool);
		}
		if(k == Keys.ESCAPE){
			MyInput.setKey(MyInput.ESC,bool);
		}
		if(k == Keys.R){
			MyInput.setKey(MyInput.R,bool);
		}
	}
}
