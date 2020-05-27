package ch.band.jumpknock.game;

import android.opengl.GLES20;

import androidx.annotation.Nullable;


/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Platform extends Placeable {
	private boolean isOneTimeUse = false;
	private Decoration decoration = null;


	public Platform(){
		super();
	}

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

	@Override
	public void draw() {
		super.draw();

	}
}
