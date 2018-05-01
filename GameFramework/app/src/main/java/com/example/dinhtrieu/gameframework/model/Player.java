package com.example.dinhtrieu.gameframework.model;

import android.graphics.Rect;

public class Player {
    private float x, y;
    private int width, height, velY;
    private Rect rect, duckRect, ground;

    private boolean isAlive;
    private boolean isDucked;
    private float duckDuration;

    private static final int JUMP_VELOCITY = -600;
    private static final int ACCEL_GRAVITY = 1800;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        ground = new Rect(0, 405, 0 + 800, 405 + 45);
        rect = new Rect();
        duckRect = new Rect();
        isAlive = true;
        isDucked = false;
    }

    public void update(float delta) {
        if (duckDuration > 0 && isDucked) {
            duckDuration -= delta;
        } else {
            isDucked = false;
            duckDuration = .9f;
        }

        if (!isGrounded()) {
            velY += ACCEL_GRAVITY * delta;
        } else {
            y = 406 - height;
            velY = 0;
        }

        y += velY * delta;
//        updateRects();
    }

    public void duck() {
        if (!isGrounded()) {
            isDucked = true;
        }
    }

    public void pushBack(int dx) {
        x -= dx;
        //Play sound

        if (x < width / 2) {
            isAlive = false;
            rect.set((int) x, (int) y, (int) x + width, (int) y + height);
        }
    }

    public boolean isGrounded() {
        return Rect.intersects(rect, ground);
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getX() {
        return x;
    }

}
