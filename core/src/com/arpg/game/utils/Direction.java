package com.arpg.game.utils;

public enum Direction {
    UP(0, 1, 90.0f, 2),
    DOWN(0, -1, 270.0f, 3),
    LEFT(-1, 0, 180.0f, 0),
    RIGHT(1, 0, 0.0f, 1),
    LOOK_UP(0, 0.0000001f, 90.0f, 2),
    LOOK_DOWN(0, -0.0000001f, 270.0f, 3),
    LOOK_LEFT(-0.0000001f, 0, 180.0f, 0),
    LOOK_RIGHT(0.0000001f, 0, 0.0f, 1);

    private float x;
    private float y;
    private int imageIndex;
    private float angle;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public float getAngle() {
        return angle;
    }

    Direction(float x, float y, float angle, int imageIndex) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.imageIndex = imageIndex;
    }
}
