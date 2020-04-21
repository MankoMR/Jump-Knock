package ch.band.jumpknock.game;

import android.graphics.PointF;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaceableTest {

    @Test
    public void getPosition() {

        Placeable placeable = new Placeable();
        placeable.position = new PointF(5,5);
        placeable.position.x = 5;
        placeable.position.y = 5;
        assertTrue(5 == placeable.getPosition().x);
        assertTrue(5 == placeable.getPosition().y);
    }

    @Test
    public void getDrawableId() {
        Placeable placeable = new Placeable();
        placeable.drawableId = 4;
        assertEquals(4,placeable.drawableId);
    }
}