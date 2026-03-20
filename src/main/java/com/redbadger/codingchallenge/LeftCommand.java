package com.redbadger.codingchallenge;

public class LeftCommand implements Command {
    @Override
    public void execute(Robot robot, WorldState worldState) {
        robot.turnLeft();
    }
}