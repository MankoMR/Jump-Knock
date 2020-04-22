package ch.band.jumpknock.game;

import android.graphics.PointF;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 *Copyright (c) 2020 Fredy Stalder, All rights reserved.
 */
public class PlayerTest {

    @Test
    public void Velocity() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        Player player = new Player(gameVariables,0);
        player.getVelocity().x = 5;
        player.getVelocity().y = 5;
        assertTrue(5 == player.getVelocity().x);
        assertTrue(5 == player.getVelocity().y);
    }

    @Test
    public void Gravity() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        Player player = new Player(gameVariables,0);
        player.setGravity(5f);
        assertTrue(5f == player.getGravity());
    }

    @Test
    public void MaxSpeedPerSec() {
        int[] intArray = {0,1,2};
        PointF floatNumber = new PointF(9f,9f);
        GameVariables gameVariables = new GameVariables(floatNumber ,floatNumber,floatNumber,floatNumber,9, intArray, intArray);
        Player player = new Player(gameVariables,0);
        player.setMaxSpeedPerSec(9f);
        assertTrue(9f == player.getMaxSpeedPerSec());
    }

}