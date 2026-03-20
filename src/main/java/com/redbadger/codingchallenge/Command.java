package com.redbadger.codingchallenge;

public interface Command {
    void execute(Robot robot, WorldState worldState);
}