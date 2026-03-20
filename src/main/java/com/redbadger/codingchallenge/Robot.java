package com.redbadger.codingchallenge;

public class Robot {
    private RobotPosition robotPosition;
    private boolean lost;

    public Robot(RobotPosition robotPosition) {
        this.robotPosition = robotPosition;
        this.lost = false;
    }

    public RobotPosition getRobotPosition() {
        return robotPosition;
    }

    public boolean isLost() {
        return lost;
    }

    public void markLost() {
        this.lost = true;
    }

    public void turnLeft() {
        this.robotPosition = this.robotPosition.afterLeft();
    }

    public void turnRight() {
        this.robotPosition = this.robotPosition.afterRight();
    }

    public void moveTo(RobotPosition newRobotPosition) {
        this.robotPosition = newRobotPosition;
    }

    @Override
    public String toString() {
        String result = robotPosition.toString();
        if (lost) {
            result += " LOST";
        }
        return result;
    }
}