package com.example.dinhtrieu.gameframework.state;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

public class MenuState extends State {

    private UIButton playButton;
    private UIButton scoreButton;
    private UIButton changepassButton;
    private UIButton exitButton;
    private static final String TAG = "MenuState";

    @Override
    public void init() {
        int gameW = GameMainActivity.GAME_WIDTH;
        int gameH = GameMainActivity.GAME_HEIGHT;
        int width = 300;
        int height = 120;
        int top = 260;

        playButton = new UIButton((gameW - width) / 2,
                top,
                (gameW - width) / 2 + width,
                top + height,
                Assets.playButton);

        scoreButton = new UIButton((gameW - width) / 2,
                top + height + 40,
                (gameW - width) / 2 + width,
                top + 2 * height + 40,
                Assets.highscorebutton);

        changepassButton = new UIButton((gameW - width) / 2,
                top + 2 * height + 80,
                (gameW - width) / 2 + width,
                top + 3 * height + 80,
                Assets.changepassbutton);

        exitButton = new UIButton((gameW - width) / 2,
                top + 3 * height + 120,
                (gameW - width) / 2 + width,
                top + 4 * height + 120,
                Assets.exitButton);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        int gameW = GameMainActivity.GAME_WIDTH;
        int gameH = GameMainActivity.GAME_HEIGHT;

        g.drawImage(Assets.menuBackground, 0, 0, gameW, gameH);
        g.drawImage(Assets.menuboard, (int)(gameW - 1000) / 2, (int)(gameH - 1000) / 2, 1000, 1000);
        playButton.render(g, false);
        scoreButton.render(g, false);
        changepassButton.render(g, false);
        exitButton.render(g, false);
    }

    @Override
    public void load() {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            scoreButton.onTouchDown(scaledX, scaledY);
            changepassButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            } else if (scoreButton.isPressed(scaledX, scaledY)) {
                scoreButton.cancel();
                setCurrentState(new ScoreState());
            } else if (changepassButton.isPressed(scaledX, scaledY)) {
                changepassButton.cancel();
                startNewActivity();
            } else if (exitButton.isPressed(scaledX, scaledY)) {
                exitButton.cancel();
                System.exit(1);
            }
        }
        return true;
    }
}








