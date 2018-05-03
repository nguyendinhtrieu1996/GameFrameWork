package com.example.dinhtrieu.gameframework.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.main.GameView;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.RandomNumberGenerator;

public class Cactus {

    private float x, y;
    private int width, height;
    private int speed;
    private Bitmap cactus;
    private static int last = GameMainActivity.GAME_WIDTH;
    private boolean isPassed;
    private int index;

    private Rect rect;

    public Cactus(float y, int width, int height, int index) {
        this.index = index;
        isPassed = false;
        speed = 1600;
        this.y = y;
        this.width = width;
        this.height = height;
        int gameW = GameMainActivity.GAME_WIDTH;
        this.x = gameW + RandomNumberGenerator.getRandIntBetween(index * gameW / 3, (index + 1) * gameW / 3);

        this.rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        this.cactus = Assets.cactus;
    }

    public void update(float delta) {
        x -= speed / GameView.FPS;

        if (x < -width) {
            int gameW = GameMainActivity.GAME_WIDTH;
            this.x = gameW + RandomNumberGenerator.getRandIntBetween(index * gameW / 3, (index + 1) * gameW / 3);
            isPassed = false;
        }

        updateRect();
    }

    public void updateRect() {
        this.rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public void render(Painter g) {
        g.drawImage(cactus, (int)x, (int)y, width, height);
    }

    //GET SET

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public Rect getRect() {
        return new Rect(rect.left + 35, rect.top + 30, rect.right - 40, rect.bottom);
    }

}






















