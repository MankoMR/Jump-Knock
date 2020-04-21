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
    //The size of the screen:
    private final PointF screenSize;
    //The size where entities can be set. Its bigger than the screen.
    private final PointF gameFieldSize;
    //The size of the player figure
    private final PointF playerSize;
    //The PictureId of the player
    @DrawableRes
    private final int playerDrawId;
    //The size of a platform without decoration
    private final PointF platformSize;
    //Possible platform pictures to use
    @DrawableRes
    private final int[] platformDrawIds;
    //Possible decoration pictures to use
    @DrawableRes
    private final int[] decorationDrawIds;

    public GameVariables(PointF screenSize, PointF gameFieldSize, PointF playerSize,PointF platformSize, int playerDrawId, @DrawableRes  int[] platformDrawIds,@DrawableRes int[] decorationDrawIds) {
        this.screenSize = screenSize;
        this.gameFieldSize = gameFieldSize;
        this.playerSize = playerSize;
        this.playerDrawId = playerDrawId;
        this.platformSize = platformSize;
        this.platformDrawIds = platformDrawIds;
        this.decorationDrawIds = decorationDrawIds;
    }

    /**
     * @return multiplier to get get from nanosecond to second
     */
    public static int getSecToNanoSec() {
        return SEC_TO_NANO_SEC;
    }

    /**
     * @return the size of the screen
     * must be smaller than game-field size
     */
    public PointF getScreenSize() {
        return screenSize;
    }

    /**
     * @return the size of the game-field
     * must be larger the the screenSize.
     */
    public PointF getGameFieldSize() {
        return gameFieldSize;
    }

    /**
     * @return the size the player must have.
     */
    public PointF getPlayerSize() {
        return playerSize;
    }

    /**
     * @return the player picture
     */
    @DrawableRes
    public int getPlayerDrawId() {
        return playerDrawId;
    }

    /**
     * @return the Size  that a platform must have.
     */
    public PointF getPlatformSize() {
        return platformSize;
    }

    /**
     * @return possible types of platform pictures
     */
    @DrawableRes
    public int[] getPlatformDrawIds() {
        return platformDrawIds;
    }

    /**
     * @return all possible decoration pictures which can be placed on platforms
     */
    @DrawableRes
    public int[] getDecorationDrawIds() {
        return decorationDrawIds;
    }

    /**
     * Calculates the top or bottom margin between screenSize and gameFieldSize
	 *
     * @return (gamefield height - screen height) / 2
     */
    public float getTopOrBottomMargin(){
        return  (gameFieldSize.y - screenSize.y )/ 2;
    }

    /**
     * Calculates the left or right margin between screenSize and gameFieldSize
	 *
     * @return (gamefield width - screen width) / 2
     */
    public float getLeftOrRightMargin(){
        return  (gameFieldSize.x - screenSize.x )/ 2;
    }

    /**
	 * Calculates the Position of the Screen on the Gamefield.
     *
     * @return position of screen on the gamefield.
     */
    public PointF getScreenPosition(){
        return new PointF(getLeftOrRightMargin(),getTopOrBottomMargin());
    }
}
