package ch.band.jumpknock.game;

import android.graphics.PointF;

import androidx.annotation.DrawableRes;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */

/**
 * The type GameVariables.
 * It is used to simplify the parameter passing for creating GameManager und functions within.
 */
public class GameVariables {
    private static final int SEC_TO_NANO_SEC = 1_000_000_000;
    //The Size of the Screen:
    private final PointF screenSize;
    //The Size where entities can be set. Its bigger than the screen.
    private final PointF gameFieldSize;
    //The Size of the playerfigur
    private final PointF playerSize;
    //The PictureId of the Player
    @DrawableRes
    private final int playerDrawId;
    //The Size of a platform without decoration
    private final PointF platformSize;
    //Possible Platform Pictures to use
    @DrawableRes
    private final int[] platformDrawIds;
    //Possible Decoration Pictures to use
    @DrawableRes
    private final int[] decorationDrawIds;
    public GameVariables(PointF screenSize, PointF gameFieldSize, PointF playerSize,PointF platformSize, int playerDrawId,  int[] platformDrawIds, int[] decorationDrawIds) {
        this.screenSize = screenSize;
        this.gameFieldSize = gameFieldSize;
        this.playerSize = playerSize;
        this.playerDrawId = playerDrawId;
        this.platformSize = platformSize;
        this.platformDrawIds = platformDrawIds;
        this.decorationDrawIds = decorationDrawIds;
    }

    /**
     * gibt den wert aus SEC_TO_NANO_SEC zurück
     * @return
     */
    public static int getSecToNanoSec() {
        return SEC_TO_NANO_SEC;
    }

    /**
     * gibt den wert aus sreenSize zurück
     * @return
     */
    public PointF getScreenSize() {
        return screenSize;
    }

    /**
     * gibt den wert aus gameFieldSize zurück
     * @return
     */
    public PointF getGameFieldSize() {
        return gameFieldSize;
    }

    /**
     * gibt den wert aus playerSize zurück
     * @return
     */
    public PointF getPlayerSize() {
        return playerSize;
    }

    /**
     * gibt den wert aus playerDrawId zurück
     * @return
     */
    public int getPlayerDrawId() {
        return playerDrawId;
    }

    /**
     * gibt den wert aus platformSize zurück
     * @return
     */
    public PointF getPlatformSize() {
        return platformSize;
    }

    /**
     * gibt den wert aus platformDrawIds zurück
     * @return
     */
    public int[] getPlatformDrawIds() {
        return platformDrawIds;
    }

    /**
     * gibt den wert aus decorationDrawIds zurück
     * @return
     */
    public int[] getDecorationDrawIds() {
        return decorationDrawIds;
    }

    /**
     * berechnet die margin von oben und unten
	 * (gamefield height - screen height) / 2
     * @return
     */
    public float getTopOrBottomMargin(){
        return  (gameFieldSize.y - screenSize.y )/ 2;
    }

    /**
     * berechnet die margin von links und rechts
	 * (gamefield width - screen width) / 2
     * @return
     */
    public float getLeftOrRightMargin(){
        return  (gameFieldSize.x - screenSize.x )/ 2;
    }

    /**
	 * Calculates the Position of the Screen on the Gamefield.
     * @return
     */
    public PointF getScreenPosition(){
        return new PointF(getLeftOrRightMargin(),getTopOrBottomMargin());
    }
}
