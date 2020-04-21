package ch.band.jumpknock.game;

import android.graphics.PointF;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */

/**
 * The type Placeable.
 * It is the base Type for Platform and Player. It contains the necessary Information for moving Objects
 */
public class Placeable {
	protected PointF position;
	protected int drawableId;

	/**
	 * updatet deltaTime
	 * @param deltaTimeNs
	 */
	public void update(int deltaTimeNs){

	}

	/**
	 * Calculates the distance reached with
	 * @param velocity    the velocity
	 * @param timeDeltaNs the time delta ns
	 * @returns the Distance with the velocity und delta time.
	 */
	public static float calcDistance(float velocity, long timeDeltaNs){
		float delta = velocity * ((float)GameVariables.getSecToNanoSec() / timeDeltaNs);
		return delta;
	}

	/**
	 * gibt den wert position zurück
	 * @return
	 */
	public PointF getPosition() {
		return position;
	}

	/**
	 * gibt den wert in drawableId zurück
	 * @return
	 */
	public int getDrawableId() {
		return drawableId;
	}
}
