package ch.band.jumpknock.game;

import android.graphics.PointF;

import androidx.annotation.DrawableRes;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */

/**
 * The type GameVariables.
 * It is used to simplify the parameter passing for creating GameManager und functions within.
 */
public class GameVariables {
    public static final int SEC_TO_NANO_SEC = 1_000_000_000;
    //The Size of the Screen:
    public final PointF screenSize;
    //The Size where entities can be set. Its bigger than the screen.
    public final PointF gameFieldSize;
    //The Size of the playerfigur
    public final PointF playerSize;
    //The PictureId of the Player
    @DrawableRes
    public final int playerDrawId;
    //The Size of a platform without decoration
    public final PointF platformSize;
    //Possible Platform Pictures to use
    @DrawableRes
    public final int[] platformDrawIds;
    //Possible Decoration Pictures to use
    @DrawableRes
    public final int[] decorationDrawIds;
    public GameVariables(PointF screenSize, PointF gameFieldSize, PointF playerSize,PointF platformSize, int playerDrawId,  int[] platformDrawIds, int[] decorationDrawIds) {
        this.screenSize = screenSize;
        this.gameFieldSize = gameFieldSize;
        this.playerSize = playerSize;
        this.playerDrawId = playerDrawId;
        this.platformSize = platformSize;
        this.platformDrawIds = platformDrawIds;
        this.decorationDrawIds = decorationDrawIds;
    }

    // (gamefield height - screen height) / 2
    public float getTopOrBottomMargin(){
        return  (gameFieldSize.y - screenSize.y )/ 2;
    }
    // (gamefield width - screen width) / 2
    public float getLeftOrRightMargin(){
        return  (gameFieldSize.x - screenSize.x )/ 2;
    }
    //Calculates the Position of the Screen on the Gamefield.
    public PointF getScreenPosition(){
        return new PointF(getLeftOrRightMargin(),getTopOrBottomMargin());
    }
}
