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
	 * runs logic which should be run each update
	 * @param deltaTimeNs
	 *
	 * Its marked as deprecated, because children classes need a variety of additional
	 * and or different information than just the delta in time.
	 * Therefore its marked as deprecated.
	 */
	@Deprecated
	public void update(int deltaTimeNs){

	}

	/**
	 * Calculates the distance reached with
	 * @param velocity    the velocity
	 * @param timeDeltaNs the time delta ns
	 * @returns the distance with the velocity und delta time.
	 */
	public static float calcDistance(float velocity, long timeDeltaNs){
		return velocity * ((float)GameVariables.getSecToNanoSec() / timeDeltaNs);
	}

	/**
	 * @return the position on the game-field
	 */
	public PointF getPosition() {
		return position;
	}

	/**
	 * @return the resource id of the drawable represented by this class
	 */
	public int getDrawableId() {
		return drawableId;
	}
}
