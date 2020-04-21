package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import ch.band.jumpknock.R;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Platform extends Placeable {
	private boolean isOneTimeUse = false;
	private Decoration decoration = null;

	/**
	 * @return whether this platform can be jumped on one time or multiple times.
	 */
	public boolean isOneTimeUse() {
		return isOneTimeUse;
	}

	/**
	 * @param oneTimeUse sets whether this platform can be jumped on one time or multiple times.
	 */
	public void setOneTimeUse(boolean oneTimeUse) {
		isOneTimeUse = oneTimeUse;
	}

	/**
	 * @return the decoration placed on the platform.
	 *
	 */
	public @Nullable Decoration getDecoration() {
		return decoration;
	}

	/**
	 * @param decoration to place on the platform
	 */
	public void setDecoration(Decoration decoration) {
		this.decoration = decoration;
	}
}
