package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.util.Painter;

public class LoadState extends State {
    @Override
    public void init() {
        load();
        setCurrentState(new MenuState());
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState());
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
        Assets.load();
    }

    @Override
    public void unload() {
        Assets.unloadBitmap(Assets.menuBackground);
    }
}

