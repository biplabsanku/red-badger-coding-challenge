package com.redbadger.codingchallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void getXReturnsX() {
        assertEquals(3, new Position(3, 7).getX());
    }

    @Test
    void getYReturnsY() {
        assertEquals(7, new Position(3, 7).getY());
    }

    @Test
    void equalPositionsAreEqual() {
        assertEquals(new Position(2, 4), new Position(2, 4));
    }

    @Test
    void differentXAreNotEqual() {
        assertNotEquals(new Position(1, 4), new Position(2, 4));
    }

    @Test
    void differentYAreNotEqual() {
        assertNotEquals(new Position(2, 3), new Position(2, 4));
    }

    @Test
    void equalPositionsHaveSameHashCode() {
        assertEquals(new Position(2, 4).hashCode(), new Position(2, 4).hashCode());
    }
}
