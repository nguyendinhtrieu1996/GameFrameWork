package com.example.dinhtrieu.gameframework.model;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameView;

public class GameBackground {

    private Bitmap background;
    private Bitmap backgroundReversed;

    private int width;
    private int height;
    private boolean reversedFirst;
    public static final float speed = 1600;

    private int xClip;

    public GameBackground(int screenWidth, int screenHeight){
        background = Assets.gamebackground;
        reversedFirst = false;

        xClip = 0;

        // Create the bitmap
        background = Bitmap.createScaledBitmap(background, screenWidth,
                screenHeight
                , true);

        // Save the width and height for later use
        width = background.getWidth();
        height = background.getHeight();

        //Create a mirror image of the background (horizontal flip)
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        backgroundReversed = Bitmap.createBitmap(background, 0, 0, width, height, matrix, true);
    }

    public void update(double delta){
        xClip -= speed / GameView.FPS;

        if (xClip >= width) {
            xClip = 0;
            reversedFirst = !reversedFirst;
        } else if (xClip <= 0) {
            xClip = width;
            reversedFirst = !reversedFirst;
        }
    }

    //Get

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isReversedFirst() {
        return reversedFirst;
    }

    public float getSpeed() {
        return speed;
    }

    public int getxClip() {
        return xClip;
    }

    public Bitmap getBackground() {
        return background;
    }

    public Bitmap getBackgroundReversed() {
        return backgroundReversed;
    }

}












