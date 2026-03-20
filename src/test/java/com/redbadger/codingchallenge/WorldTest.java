package com.redbadger.codingchallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void negativeMaxXThrows() {
        assertThrows(IllegalArgumentException.class, () -> new World(-1, 5));
    }

    @Test
    void negativeMaxYThrows() {
        assertThrows(IllegalArgumentException.class, () -> new World(5, -1));
    }

    @Test
    void zeroBoundsAreValid() {
        assertDoesNotThrow(() -> new World(0, 0));
    }

    @Test
    void isInsideReturnsTrueForOrigin() {
        World world = new World(5, 5);
        assertTrue(world.isInside(new Position(0, 0)));
    }

    @Test
    void isInsideReturnsTrueForUpperRightCorner() {
        World world = new World(5, 3);
        assertTrue(world.isInside(new Position(5, 3)));
    }

    @Test
    void isInsideReturnsFalseWhenXExceedsMax() {
        World world = new World(5, 5);
        assertFalse(world.isInside(new Position(6, 0)));
    }

    @Test
    void isInsideReturnsFalseWhenYExceedsMax() {
        World world = new World(5, 5);
        assertFalse(world.isInside(new Position(0, 6)));
    }

    @Test
    void isInsideReturnsFalseForNegativeX() {
        World world = new World(5, 5);
        assertFalse(world.isInside(new Position(-1, 0)));
    }

    @Test
    void isInsideReturnsFalseForNegativeY() {
        World world = new World(5, 5);
        assertFalse(world.isInside(new Position(0, -1)));
    }
}
