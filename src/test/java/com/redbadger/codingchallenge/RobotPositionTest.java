package com.redbadger.codingchallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotPositionTest {

    // --- afterLeft ---

    @Test
    void afterLeftFromNorthFacesWest() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.N);
        assertEquals(Orientation.W, pos.afterLeft().getOrientation());
    }

    @Test
    void afterLeftFromWestFacesSouth() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.W);
        assertEquals(Orientation.S, pos.afterLeft().getOrientation());
    }

    @Test
    void afterLeftFromSouthFacesEast() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.S);
        assertEquals(Orientation.E, pos.afterLeft().getOrientation());
    }

    @Test
    void afterLeftFromEastFacesNorth() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.E);
        assertEquals(Orientation.N, pos.afterLeft().getOrientation());
    }

    // --- afterRight ---

    @Test
    void afterRightFromNorthFacesEast() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.N);
        assertEquals(Orientation.E, pos.afterRight().getOrientation());
    }

    @Test
    void afterRightFromEastFacesSouth() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.E);
        assertEquals(Orientation.S, pos.afterRight().getOrientation());
    }

    @Test
    void afterRightFromSouthFacesWest() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.S);
        assertEquals(Orientation.W, pos.afterRight().getOrientation());
    }

    @Test
    void afterRightFromWestFacesNorth() {
        RobotPosition pos = new RobotPosition(new Position(0, 0), Orientation.W);
        assertEquals(Orientation.N, pos.afterRight().getOrientation());
    }

    // --- afterForward ---

    @Test
    void afterForwardFacingNorthIncrementsY() {
        RobotPosition pos = new RobotPosition(new Position(2, 2), Orientation.N);
        assertEquals(new Position(2, 3), pos.afterForward().getPosition());
    }

    @Test
    void afterForwardFacingSouthDecrementsY() {
        RobotPosition pos = new RobotPosition(new Position(2, 2), Orientation.S);
        assertEquals(new Position(2, 1), pos.afterForward().getPosition());
    }

    @Test
    void afterForwardFacingEastIncrementsX() {
        RobotPosition pos = new RobotPosition(new Position(2, 2), Orientation.E);
        assertEquals(new Position(3, 2), pos.afterForward().getPosition());
    }

    @Test
    void afterForwardFacingWestDecrementsX() {
        RobotPosition pos = new RobotPosition(new Position(2, 2), Orientation.W);
        assertEquals(new Position(1, 2), pos.afterForward().getPosition());
    }

    @Test
    void afterForwardPreservesOrientation() {
        RobotPosition pos = new RobotPosition(new Position(1, 1), Orientation.N);
        assertEquals(Orientation.N, pos.afterForward().getOrientation());
    }

    // --- equality (used by scent Set) ---

    @Test
    void equalPositionsWithSameOrientationAreEqual() {
        RobotPosition a = new RobotPosition(new Position(1, 2), Orientation.N);
        RobotPosition b = new RobotPosition(new Position(1, 2), Orientation.N);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void samePositionDifferentOrientationAreNotEqual() {
        RobotPosition a = new RobotPosition(new Position(1, 2), Orientation.N);
        RobotPosition b = new RobotPosition(new Position(1, 2), Orientation.E);
        assertNotEquals(a, b);
    }

    @Test
    void toStringFormatsCorrectly() {
        RobotPosition pos = new RobotPosition(new Position(3, 4), Orientation.W);
        assertEquals("3 4 W", pos.toString());
    }
}
