package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */


/**
 * The type Decoration.
 * Stores necessary Information for placing Decoration on a Platform.
 */
public class Decoration {

	/**
	 * The Drawable id identifying what Drawable will be drawed on top of a Decoration
	 */
	@DrawableRes
	private int drawableId;
	/**
	 * The horizontal relativ Position on the Platform.
	 * 0 means the leftside of the Decoration is aligned with the left side of the platform and
	 * otherwise with 1.0 for the right side.
	 */
	@FloatRange(from = 0.0, to = 1.0)
	private float position;

	public Decoration(int drawableId, float position)
	{
		this.drawableId = drawableId;
		this.position = position;
	}

	/**
	 * gives back the value of drawableId
	 * @return
	 */
	public int getDrawableId() {
		return drawableId;
	}

	/**
	 * save the value in drawableId
	 * @param drawableId
	 */
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}

	/**
	 * gives back the value of position
	 * @return
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * saves the value in position
	 * @param position
	 */
	public void setPosition(float position) {
		this.position = position;
	}
}
