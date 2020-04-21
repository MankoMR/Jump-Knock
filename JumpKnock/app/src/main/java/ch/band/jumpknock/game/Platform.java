package ch.band.jumpknock.game;

import androidx.annotation.DrawableRes;

import ch.band.jumpknock.R;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class Platform extends Placeable {
	private boolean isOneTimeUse = false;
	private Decoration decoration = null;

	/**
	 * gibt den wert aus isOneTimeUse zurück
	 * @return
	 */
	public boolean isOneTimeUse() {
		return isOneTimeUse;
	}

	/**
	 * speichert den wert in isOneTimeUse
	 * @param oneTimeUse
	 */
	public void setOneTimeUse(boolean oneTimeUse) {
		isOneTimeUse = oneTimeUse;
	}

	/**
	 * gibt den wert aus decoration zurück
	 * @return
	 */
	public Decoration getDecoration() {
		return decoration;
	}

	/**
	 * speichert den wert in decoration
	 * @param decoration
	 */
	public void setDecoration(Decoration decoration) {
		this.decoration = decoration;
	}
}
