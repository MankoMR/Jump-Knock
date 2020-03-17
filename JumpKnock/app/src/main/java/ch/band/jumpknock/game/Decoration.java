package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;

import ch.band.jumpknock.R;

public class Decoration {

	@DrawableRes
	public int drawableId;
	public float position;

	public static final int[] DecResIds= new int[]{
			R.drawable.tree, R.drawable.bush1, R.drawable.bush2
	};
}
