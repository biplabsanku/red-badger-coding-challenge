package com.redbadger.codingchallenge;

import java.util.Objects;

public final class RobotPosition {
    private final Position position;
    private final Orientation orientation;

    public RobotPosition(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public RobotPosition afterLeft() {
        Orientation newOrientation = switch (orientation) {
            case N -> Orientation.W;
            case W -> Orientation.S;
            case S -> Orientation.E;
            case E -> Orientation.N;
        };
        return new RobotPosition(position, newOrientation);
    }

    public RobotPosition afterRight() {
        Orientation newOrientation = switch (orientation) {
            case N -> Orientation.E;
            case E -> Orientation.S;
            case S -> Orientation.W;
            case W -> Orientation.N;
        };
        return new RobotPosition(position, newOrientation);
    }

    public RobotPosition afterForward() {
        Position nextPosition = switch (orientation) {
            case N -> new Position(position.getX(), position.getY() + 1);
            case E -> new Position(position.getX() + 1, position.getY());
            case S -> new Position(position.getX(), position.getY() - 1);
            case W -> new Position(position.getX() - 1, position.getY());
        };
        return new RobotPosition(nextPosition, orientation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotPosition)) return false;
        RobotPosition that = (RobotPosition) o;
        return Objects.equals(position, that.position) &&
               orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, orientation);
    }

    @Override
    public String toString() {
        return position.getX() + " " + position.getY() + " " + orientation;
    }
}