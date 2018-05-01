package com.example.dinhtrieu.gameframework.state;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.model.GameBackground;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

public class PlayState extends State {

    private GameBackground gameBackground;
    private long fps = 60;

    @Override
    public void init() {
        load();
        gameBackground = new GameBackground(GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT, 60);
    }

    @Override
    public void update(float delta) {
        fps = (long)(1 / delta);
        gameBackground.update(fps);
    }

    @Override
    public void render(Painter g) {
        drawBackground(g);
    }

    @Override
    public void load() {
        Assets.gamebackground = Assets.loadBitmap("gamebackground.png", true);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false; //This needs to be set to true if there is touch input
    }

    //Feature
    private void drawBackground(Painter g) {
        Rect fromRect1 = new Rect(0, 0, gameBackground.getWidth() - gameBackground.getxClip(), gameBackground.getHeight());
        Rect toRect1 = new Rect(gameBackground.getxClip(), 0, gameBackground.getWidth(), GameMainActivity.GAME_HEIGHT);

        // For the reversed background
        Rect fromRect2 = new Rect(gameBackground.getWidth() - gameBackground.getxClip(), 0, gameBackground.getWidth(), gameBackground.getHeight());
        Rect toRect2 = new Rect(0, 0, gameBackground.getxClip(), GameMainActivity.GAME_HEIGHT);

        //draw the two background bitmaps
        if (!gameBackground.isReversedFirst()) {
            g.getCanvas().drawBitmap(gameBackground.getBackground(), fromRect1, toRect1, g.getPaint());
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect2, toRect2, g.getPaint());
        } else {
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect2, toRect2, g.getPaint());
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect1, toRect1, g.getPaint());
        }
    }

}












