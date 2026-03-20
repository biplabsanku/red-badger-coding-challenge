package com.redbadger.codingchallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldState {
    private final World world;
    private final Set<RobotPosition> scents;
    private final List<Robot> robots;

    public WorldState(World world) {
        this.world = world;
        this.scents = new HashSet<>();
        this.robots = new ArrayList<>();
    }

    public World getWorld() {
        return world;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public boolean hasScent(RobotPosition robotPosition) {
        return scents.contains(robotPosition);
    }

    public void addScent(RobotPosition robotPosition) {
        scents.add(robotPosition);
    }

    public void moveRobotForward(Robot robot) {
        RobotPosition current = robot.getRobotPosition();
        RobotPosition next = current.afterForward();

        if (world.isInside(next.getPosition())) {
            robot.moveTo(next);
            return;
        }

        if (hasScent(current)) {
            return;
        }

        addScent(current);
        robot.markLost();
    }
}