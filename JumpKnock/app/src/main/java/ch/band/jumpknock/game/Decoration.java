package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;

import ch.band.jumpknock.R;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
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
	public int drawableId;
	/**
	 * The horizontal relativ Position on the Platform.
	 * 0 means the leftside of the Decoration is aligned with the left side of the platform and
	 * otherwise with 1.0 for the right side.
	 */
	@FloatRange(from = 0.0, to = 1.0)
	public float position;
}
