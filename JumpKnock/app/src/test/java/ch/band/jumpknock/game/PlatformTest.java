package ch.band.jumpknock.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {

    @Test
    public void OneTimeUse() {
        Platform platform = new Platform();
        platform.setOneTimeUse(true);
        assertTrue(platform.isOneTimeUse());
    }

    @Test
    public void Decoration() {
        Platform platform = new Platform();
        Decoration decoration = new Decoration(5,9f);
        platform.setDecoration(decoration);
        assertEquals(decoration,platform.getDecoration());
    }

}