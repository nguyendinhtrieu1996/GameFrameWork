package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.util.Painter;

public class GameOverState extends State {

    private String playerScore;

    public GameOverState(int playerScore) {
        this.playerScore = "" + playerScore;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
