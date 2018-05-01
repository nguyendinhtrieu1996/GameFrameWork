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
    private static final String TAG = "MenuState";

    @Override
    public void init() {
        load();
        Bitmap playButtonImage = Assets.loadBitmap("play_button.png", true);
        playButton = new UIButton(800, 300, 1100, 420, playButtonImage);

        Bitmap scoreButtonImage = Assets.loadBitmap("score_button.png", true);
        scoreButton = new UIButton(800, 450, 1100, 570, scoreButtonImage);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
         g.drawImage(Assets.menuBackground, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
         playButton.render(g, false);
         scoreButton.render(g, false);
    }

    @Override
    public void load() {
        Assets.menuBackground = Assets.loadBitmap("menubackground.jpg", true);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            scoreButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            } else if (scoreButton.isPressed(scaledX, scaledY)) {
                scoreButton.cancel();
                setCurrentState(new ScoreState());
            }
        }
        return true;
    }
}








