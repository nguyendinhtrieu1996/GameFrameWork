package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

public class PlayState extends State {

    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gamebackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void load() {
        Assets.gamebackground = Assets.loadBitmap("gamebackground.png", true);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false; //This needs to be set to true if there is touch input
    }
}

