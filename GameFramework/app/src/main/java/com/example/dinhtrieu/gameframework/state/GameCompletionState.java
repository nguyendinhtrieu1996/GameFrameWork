package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

public class GameCompletionState extends State {

    private UIButton newGameButton;
    private UIButton savescoreButton;
    private UIButton exitButton;
    private int score;

    public GameCompletionState(int score) {
        this.score = score;
        init();
    }

    @Override
    public void init() {
        newGameButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                460,
                (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                580,
                Assets.newgamebutton);

        savescoreButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                610,
                (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                730,
                Assets.savescorebutton);

        exitButton = new UIButton((int)((GameMainActivity.GAME_WIDTH - 300) / 2),
                760,
                (int)((GameMainActivity.GAME_WIDTH - 300) / 2 + 300),
                860,
                Assets.exitButton);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.winMenuboard,
                (GameMainActivity.GAME_WIDTH - 900) / 2,
                (GameMainActivity.GAME_HEIGHT - 900) / 2,
                900,
                900);

        newGameButton.render(g, false);
        savescoreButton.render(g, false);
        exitButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            newGameButton.onTouchDown(scaledX, scaledY);
            savescoreButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (newGameButton.isPressed(scaledX, scaledY)) {
                newGameButton.cancel();
                setCurrentState(new PlayState());
            }

            if (savescoreButton.isPressed(scaledX, scaledY)) {
                savescoreButton.cancel();
                GameMainActivity.saveHighScore(score);
                setCurrentState(new MenuState());
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

