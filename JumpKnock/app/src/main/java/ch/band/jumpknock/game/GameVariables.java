package ch.band.jumpknock.game;

import android.graphics.PointF;

public class GameVariables {
    public static final int SEC_TO_NANO_SEC = 1_000_000_000;
    //The Size of the Screen:
    public final PointF screenSize;
    //The Size where entities can be set. Its bigger than the screen.
    public final PointF gameFieldSize;
    //The Size of the playerfigur
    public final PointF playerSize;
    //The Size of a platform without decoration
    public final PointF platformSize;

    public GameVariables(PointF screenSize, PointF gameFieldSize, PointF playerSize, PointF platformSize) {
        this.screenSize = screenSize;
        this.gameFieldSize = gameFieldSize;
        this.playerSize = playerSize;
        this.platformSize = platformSize;
    }
    
    public float getTopOrBottomMargin(){
        return  (gameFieldSize.y - screenSize.y )/ 2;
    }
    public float getLeftOrRightMargin(){
        return  (gameFieldSize.x - screenSize.x )/ 2;
    }
}
