package ch.band.jumpknock.game;

import android.graphics.PointF;

import androidx.annotation.DrawableRes;
/*
 *Copyright (c) 2020 Manuel Koloska, All rights reserved.
 */
public class GameVariables {
    public static final int SEC_TO_NANO_SEC = 1_000_000_000;
    //The Size of the Screen:
    public final PointF screenSize;
    //The Size where entities can be set. Its bigger than the screen.
    public final PointF gameFieldSize;
    //The Size of the playerfigur
    public final PointF playerSize;
    @DrawableRes
    public final int playerDrawId;
    //The Size of a platform without decoration
    public final PointF platformSize;
    @DrawableRes
    public final int[] platformDrawIds;
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

    public float getTopOrBottomMargin(){
        return  (gameFieldSize.y - screenSize.y )/ 2;
    }
    public float getLeftOrRightMargin(){
        return  (gameFieldSize.x - screenSize.x )/ 2;
    }
    public PointF getScreenPosition(){
        return new PointF(getLeftOrRightMargin(),getTopOrBottomMargin());
    }
}
