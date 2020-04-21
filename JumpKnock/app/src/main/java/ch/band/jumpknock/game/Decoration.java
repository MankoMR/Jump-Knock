package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Decoration {

	@DrawableRes
	private int drawableId;
	private float position;

	public Decoration(int drawableId, float position)
	{
		this.drawableId = drawableId;
		this.position = position;
	}

	/**
	 * gibt den int wert drawableId zurück
	 * @return
	 */
	public int getDrawableId() {
		return drawableId;
	}

	/**
	 * speichert den mitgebenen wert in drawableId ab
	 * @param drawableId
	 */
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}

	/**
	 * gibt den float wert aus position zurück
	 * @return
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * speichert den mitgebenen wert in position ab
	 * @param position
	 */
	public void setPosition(float position) {
		this.position = position;
	}
}
