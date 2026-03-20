package com.redbadger.codingchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldStateTest {

    private World world;
    private WorldState worldState;

    @BeforeEach
    void setUp() {
        world = new World(5, 5);
        worldState = new WorldState(world);
    }

    // --- addRobot / getRobots ---

    @Test
    void getRobotsIsEmptyInitially() {
        assertTrue(worldState.getRobots().isEmpty());
    }

    @Test
    void addedRobotsAreReturnedInOrder() {
        Robot first = robot(0, 0, Orientation.N);
        Robot second = robot(1, 1, Orientation.E);
        worldState.addRobot(first);
        worldState.addRobot(second);
        assertEquals(List.of(first, second), worldState.getRobots());
    }

    // --- scents ---

    @Test
    void noScentInitially() {
        RobotPosition pos = robotPosition(3, 5, Orientation.N);
        assertFalse(worldState.hasScent(pos));
    }

    @Test
    void addedScentIsDetected() {
        RobotPosition pos = robotPosition(3, 5, Orientation.N);
        worldState.addScent(pos);
        assertTrue(worldState.hasScent(pos));
    }

    @Test
    void scentIsDirectional() {
        RobotPosition northScent = robotPosition(3, 5, Orientation.N);
        worldState.addScent(northScent);
        assertFalse(worldState.hasScent(robotPosition(3, 5, Orientation.E)));
    }

    // --- moveRobotForward ---

    @Test
    void moveForwardInsideBoundsMovesRobot() {
        Robot robot = robot(2, 2, Orientation.N);
        worldState.moveRobotForward(robot);
        assertEquals(robotPosition(2, 3, Orientation.N), robot.getRobotPosition());
        assertFalse(robot.isLost());
    }

    @Test
    void moveForwardOutsideBoundsMarksRobotLost() {
        Robot robot = robot(2, 5, Orientation.N);
        worldState.moveRobotForward(robot);
        assertTrue(robot.isLost());
    }

    @Test
    void moveForwardOutsideBoundsRobotStaysAtLastPosition() {
        Robot robot = robot(2, 5, Orientation.N);
        worldState.moveRobotForward(robot);
        assertEquals(robotPosition(2, 5, Orientation.N), robot.getRobotPosition());
    }

    @Test
    void moveForwardOutsideBoundsLeavesScent() {
        Robot robot = robot(2, 5, Orientation.N);
        worldState.moveRobotForward(robot);
        assertTrue(worldState.hasScent(robotPosition(2, 5, Orientation.N)));
    }

    @Test
    void moveForwardIgnoredWhenScentPresent() {
        worldState.addScent(robotPosition(2, 5, Orientation.N));
        Robot robot = robot(2, 5, Orientation.N);
        worldState.moveRobotForward(robot);
        assertFalse(robot.isLost());
        assertEquals(robotPosition(2, 5, Orientation.N), robot.getRobotPosition());
    }

    // --- helpers ---

    private Robot robot(int x, int y, Orientation orientation) {
        return new Robot(robotPosition(x, y, orientation));
    }

    private RobotPosition robotPosition(int x, int y, Orientation orientation) {
        return new RobotPosition(new Position(x, y), orientation);
    }
}
