package com.redbadger.codingchallenge;

public class ForwardCommand implements Command {
    @Override
    public void execute(Robot robot, WorldState worldState) {
        worldState.moveRobotForward(robot);
    }
}