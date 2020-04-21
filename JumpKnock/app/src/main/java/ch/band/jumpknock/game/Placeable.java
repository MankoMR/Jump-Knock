package ch.band.jumpknock.game;

import android.graphics.PointF;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
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
	 * gibt ein delta aus geschwindikeit und der zeit zurück
	 * @param velocity
	 * @param timeDeltaNs
	 * @return
	 */
	public static float getPositionDelta( float velocity, long timeDeltaNs){
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
