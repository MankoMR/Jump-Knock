package ch.band.jumpknock.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class DecorationTest {

    @Test
    public void getDrawableId() {
        Decoration decoration = new Decoration(5,4f);
        assertEquals(5, decoration.getDrawableId());
    }

    @Test
    public void setDrawableId() {
        Decoration decoration = new Decoration(5,4f);
        decoration.setDrawableId(9);
        assertEquals(9, decoration.getDrawableId());
    }

    @Test
    public void getPosition() {
        Decoration decoration = new Decoration(5,4f);
        assertTrue(4f == decoration.getPosition());
    }

    @Test
    public void setPosition() {
        Decoration decoration = new Decoration(5,4f);
        decoration.setPosition(9f);
        assertTrue(9f ==  decoration.getPosition());
    }
}