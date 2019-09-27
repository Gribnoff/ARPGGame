package com.arpg.game;

public enum Direction {
    UP(0, 1, 90.0f), DOWN(0, -1, 270.0f), LEFT(-1, 0, 180.0f), RIGHT(1, 0, 0.0f);

    private int x;
    private int y;
    private float angle;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    Direction(int x, int y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}
