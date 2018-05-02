package com.example.dinhtrieu.gameframework.state;

import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;
import com.example.dinhtrieu.gameframework.util.UILabel;

public class GameOverState extends State {

    private int playerScore;
    private UIButton continueButton;
    private UIButton exitButton;
    private UILabel labelScore;

    public GameOverState(int playerScore) {
        this.playerScore = playerScore;
        labelScore = new UILabel( playerScore + " score", GameMainActivity.GAME_WIDTH / 2, 550);
        labelScore.setSize(130);
        this.continueButton = new UIButton(700, 650, 950, 730, Assets.continueButton);
        this.exitButton = new UIButton(1020, 650, 1270, 730, Assets.exitButton);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameoverbackground, 500, 100, 962, 746);
        continueButton.render(g, false);
        exitButton.render(g, false);
        labelScore.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            continueButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (continueButton.isPressed(scaledX, scaledY)) {
                exitButton.cancel();
                setCurrentState(new PlayState());
            } else if (exitButton.isPressed(scaledX, scaledY)) {
                exitButton.cancel();
                setCurrentState(new MenuState());
            }
        }
        return true;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

}

















