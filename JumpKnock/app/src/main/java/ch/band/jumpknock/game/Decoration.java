package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */


/**
 * The type Decoration.
 * Stores necessary information for placing decoration on a platform.
 */
public class Decoration {

	/**
	 * The Drawable id identifying what Drawable will be drawed on top of a Decoration
	 */
	@DrawableRes
	private int drawableId;
	/**
	 * The horizontal relative position on the platform.
	 * 0 means the left side of the decoration is aligned with the left side of the platform and
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
	 * @return the Id of the drawable resource.
	 */
	public int getDrawableId() {
		return drawableId;
	}

	/**
	 * change the drawable resource
	 *
	 * @param drawableId
	 */
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}

	/**
	 * @return the relative position of the decoration
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * change the position of the decoration
	 * @param position
	 */
	public void setPosition(float position) {
		this.position = position;
	}
}
