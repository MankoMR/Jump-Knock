package ch.band.jumpknock.game;

import android.graphics.PointF;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */
public class Placeable {
	public PointF position;
	public int drawableId;
	public void update(int deltaTimeNs){

	}
	public static float getPositionDelta( float velocity, long timeDeltaNs){
		float delta = velocity * ((float)GameVariables.SEC_TO_NANO_SEC / timeDeltaNs);
		return delta;
	}
}
