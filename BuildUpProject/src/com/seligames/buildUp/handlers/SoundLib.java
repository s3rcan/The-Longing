package com.seligames.buildUp.handlers;

import com.badlogic.gdx.audio.Music;

public class SoundLib {

	public static Music splashScreen;
	public static Music intro;
	public static Music PickUpv2;
	public static Music RockDeath;
	public static Music RockMove;
	public static Music Rubble_FallDown;
	public static Music GameOver;
	public static Music CloudMoveWaterSplashv2;
	public static Music CloudDeathv2;
	public static Music bgv3;
	public static Music bgv2;
	public static Music Windball_move_rainycloud;
	public static Music splashv6;
	
	public static void init(){
		intro.setLooping(true);
		bgv2.setLooping(true);
		bgv3.setLooping(true);
		RockMove.setLooping(true);
//		Windball_move_rainycloud.setLooping(true);

	}
}
