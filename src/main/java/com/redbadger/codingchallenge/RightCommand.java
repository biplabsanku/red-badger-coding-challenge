package com.redbadger.codingchallenge;

public class RightCommand implements Command {
    @Override
    public void execute(Robot robot, WorldState worldState) {
        robot.turnRight();
    }
}