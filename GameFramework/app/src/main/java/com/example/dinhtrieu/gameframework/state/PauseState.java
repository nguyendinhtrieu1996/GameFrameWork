package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

public class PauseState extends State {
    private UIButton resumeButton;
    private UIButton restartButton;
    private UIButton exitButton;

    @Override
    public void init() {
        resumeButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                                    320,
                                    (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                                    440,
                                    Assets.resumeButton);

        restartButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                490,
                (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                610,
                Assets.restartButton);

        exitButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                660,
                (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                780,
                Assets.exitButton);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menuboard,
                (GameMainActivity.GAME_WIDTH - 900) / 2,
                (GameMainActivity.GAME_HEIGHT - 900) / 2,
                900,
                900);

        resumeButton.render(g, false);
        restartButton.render(g, false);
        exitButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            resumeButton.onTouchDown(scaledX, scaledY);
            restartButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (resumeButton.isPressed(scaledX, scaledY)) {
                restartButton.cancel();
                setResumeGame();
            }

            if (restartButton.isPressed(scaledX, scaledY)) {
                restartButton.cancel();
                setCurrentState(new PlayState());
            }

            if (exitButton.isPressed(scaledX, scaledY)) {
                exitButton.cancel();
                setCurrentState(new MenuState());
            }
        }
        return true;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

}








