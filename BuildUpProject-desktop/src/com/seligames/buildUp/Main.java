package com.seligames.buildUp;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Constants.gameName;
		cfg.useGL30 = false;
		cfg.resizable = false;
		cfg.width = Constants.V_WIDTH;
		cfg.height = Constants.V_HEIGHT;
		
		new LwjglApplication(new BuildUpGame(), cfg);
	}
}
