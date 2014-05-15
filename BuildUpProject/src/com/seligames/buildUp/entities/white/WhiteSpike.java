package com.seligames.buildUp.entities.white;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.seligames.buildUp.BuildUpGame;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.abstracts.Spike;

public class WhiteSpike extends Spike{
	public WhiteSpike(Vector2 pos) {
		super(pos);
		
		Texture t = BuildUpGame.assetManager.get("res/whiteSpike.png");
		TextureRegion[] tr = TextureRegion.split(t,
				(int) Constants.TILE_HEIGHT, (int) Constants.TILE_WIDTH)[0];
		animation.setFrames(tr);
	}
}
