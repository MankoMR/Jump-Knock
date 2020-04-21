package ch.band.jumpknock.game;

import android.graphics.PointF;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameVariablesTest {

    @Test
    public void getSecToNanoSec() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        assertEquals(1000000000,gameVariables.getSecToNanoSec());
    }

    @Test
    public void getScreenSize() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getScreenSize().x = 9f;
        gameVariables.getScreenSize().y = 9f;
        assertTrue(9 == gameVariables.getScreenSize().x);
        assertTrue(9 == gameVariables.getScreenSize().y);
    }

    @Test
    public void getGameFieldSize() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getGameFieldSize().x = 9f;
        gameVariables.getGameFieldSize().y = 9f;
        assertTrue(9f == gameVariables.getGameFieldSize().x);
        assertTrue(9f == gameVariables.getGameFieldSize().y);
    }

    @Test
    public void getPlayerSize() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getPlayerSize().x = 9f;
        gameVariables.getPlayerSize().y = 9f;
        assertTrue(9f == gameVariables.getPlayerSize().x);
        assertTrue(9f == gameVariables.getPlayerSize().y);
    }

    @Test
    public void getPlayerDrawId() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        assertTrue(9 == gameVariables.getPlayerDrawId());
    }

    @Test
    public void getPlatformSize() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getPlatformSize().x = 9f;
        gameVariables.getPlatformSize().y = 9f;
        assertTrue(9f == gameVariables.getPlatformSize().x);
        assertTrue( 9f == gameVariables.getPlatformSize().y);
    }

    @Test
    public void getPlatformDrawIds() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        assertEquals(intArray,gameVariables.getPlatformDrawIds());
    }

    @Test
    public void getDecorationDrawIds() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        assertEquals(intArray,gameVariables.getDecorationDrawIds());
    }

    @Test
    public void getTopOrBottomMargin() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        PointF bigNumber = new PointF(19f, 19f);
        GameVariables gameVariables = new GameVariables(floatNumber ,bigNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getScreenSize().y = 9f;
        gameVariables.getGameFieldSize().y = 19f;
        assertTrue(5 == gameVariables.getTopOrBottomMargin());

    }


    @Test
    public void getLeftOrRightMargin() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        PointF bigNumber = new PointF(19f, 19f);
        GameVariables gameVariables = new GameVariables(floatNumber ,bigNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getScreenSize().x = 9f;
        gameVariables.getGameFieldSize().x = 19f;
        assertTrue(5 == gameVariables.getLeftOrRightMargin());
    }

    @Test
    public void getScreenPosition() {

        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        PointF bigNumber = new PointF(19f, 19f);
        GameVariables gameVariables = new GameVariables(floatNumber ,bigNumber,floatNumber,floatNumber,9, intArray, intArray);
        gameVariables.getScreenSize().x = 9f;
        gameVariables.getGameFieldSize().x = 19f;
        gameVariables.getScreenSize().y = 9f;
        gameVariables.getGameFieldSize().y = 19f;
        gameVariables.getScreenPosition().x = 5;
        gameVariables.getScreenPosition().y = 5;
        //assertTrue(5 == gameVariables.getScreenPosition().x);
        //assertTrue(5 == gameVariables.getScreenPosition().y);
    }
}