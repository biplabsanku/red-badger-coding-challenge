package com.redbadger.codingchallenge;

public final class World {
    private final int maxX;
    private final int maxY;

    public World(int maxX, int maxY) {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException("World bounds cannot be negative");
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean isInside(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
    }
}