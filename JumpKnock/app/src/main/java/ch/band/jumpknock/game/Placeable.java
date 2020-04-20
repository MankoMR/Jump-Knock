package ch.band.jumpknock.game;

import android.graphics.PointF;

/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */

/**
 * The type Placeable.
 * It is the base Type for Platform and Player. It contains the necessary Information for moving Objects
 */
public class Placeable {
	public PointF position;
	public int drawableId;
	public void update(int deltaTimeNs){

	}

	/**
	 * Calculates the distance reached with
	 * @param velocity    the velocity
	 * @param timeDeltaNs the time delta ns
	 * @returns the Distance with the velocity und delta time.
	 */
	public static float calcDistance(float velocity, long timeDeltaNs){
		float delta = velocity * ((float)GameVariables.SEC_TO_NANO_SEC / timeDeltaNs);
		return delta;
	}
}
