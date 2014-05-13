package com.seligames.buildUp.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameButton {

	private float x;
	private float y;
	private float width;
	private float height;

	private TextureRegion reg;

	Vector3 vec;
	private OrthographicCamera cam;

	private boolean clicked;
	
	private boolean down;
	private boolean up;

	public GameButton(TextureRegion reg, float x, float y,
			OrthographicCamera cam) {

		this.reg = reg;
		this.x = x;
		this.y = y;
		this.cam = cam;
		this.clicked = false;

		width = reg.getRegionWidth();
		height = reg.getRegionHeight();
		vec = new Vector3();

	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isReleased() {
		return up;
	}

	public void update(float dt) {

		vec.set(MyInput.x, MyInput.y, 0);
		cam.unproject(vec);

		if (MyInput.isPressed() && vec.x > x - width / 2
				&& vec.x < x + width / 2 && vec.y > y - height / 2
				&& vec.y < y + height / 2) {
			clicked = true;
		} else {
			clicked = false;
		}
		if (MyInput.isDown() && vec.x > x - width / 2 && vec.x < x + width / 2
				&& vec.y > y - height / 2 && vec.y < y + height / 2) {
			down = true;
		} else {
			down = false;
		}
		if (MyInput.isReleased() && vec.x > x - width / 2
				&& vec.x < x + width / 2 && vec.y > y - height / 2
				&& vec.y < y + height / 2) {
			up = true;
		} else {
			up = false;
		}

	}

	public void render(SpriteBatch sb) {

		sb.begin();

		sb.draw(reg, x - width / 2, y - height / 2);


		sb.end();

	}

	public void renderSmaller(SpriteBatch sb) {
		sb.begin();

		sb.draw(reg, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 0.9f, 0.9f, 0, false);


		sb.end();
	}

	// private void drawString(SpriteBatch sb, String s, float x, float y) {
	// int len = s.length();
	// float xo = len * font[0].getRegionWidth() / 2;
	// float yo = font[0].getRegionHeight() / 2;
	// for(int i = 0; i < len; i++) {
	// char c = s.charAt(i);
	// if(c == '/') c = 10;
	// else if(c >= '0' && c <= '9') c -= '0';
	// else continue;
	// sb.draw(font[c], x + i * 9 - xo, y - yo);
	// }
	// }

}
