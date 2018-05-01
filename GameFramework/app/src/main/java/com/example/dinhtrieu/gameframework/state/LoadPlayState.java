package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.util.Painter;

public class LoadPlayState extends State {
    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {
        unload();
        setCurrentState(new PlayState());
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {
        Assets.unloadBitmap(Assets.menuBackground);
    }
}

