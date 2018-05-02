package com.example.dinhtrieu.gameframework.state;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.model.Cactus;
import com.example.dinhtrieu.gameframework.model.GameBackground;
import com.example.dinhtrieu.gameframework.model.Player;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;
import com.example.dinhtrieu.gameframework.util.UILabel;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    private static final String TAG = "PlayState";

    private GameBackground gameBackground;
    private Player player;
    private List<Cactus> cactusList;
    private UILabel labelScore;
    private int score;
    private boolean isGameOver;

    @Override
    public void init() {
        isGameOver = false;
        score = 0;
        labelScore = new UILabel("0", GameMainActivity.GAME_WIDTH / 2 - 6, 150);
        gameBackground = new GameBackground(GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player = new Player(40, 760, 180, 180);

        cactusList = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            Cactus cactus = new Cactus(760, 200, 200);
            cactusList.add(cactus);
        }
    }

    @Override
    public void update(float delta) {
        gameBackground.update(delta);
        Assets.animation.update(delta);
        player.update(delta);

        for (Cactus cactus : cactusList) {
            cactus.update(delta);
        }

        checkGameOver();

        if (!isGameOver) {
            updateScore();
            labelScore.setText(score + "");
        }
    }

    @Override
    public void render(Painter g) {
        drawBackground(g);

        for (Cactus cactus : cactusList) {
            cactus.render(g);
        }

        Assets.animation.render(g, (int)player.getX(), (int) player.getY());
        labelScore.render(g);
    }

    @Override
    public void load() {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        player.onTouch(e, scaledX, scaledY);
        Assets.playSound(Assets.pointSoundId);
        return false;
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
            g.getCanvas().drawBitmap(gameBackground.getBackground(), fromRect2, toRect2, g.getPaint());
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect1, toRect1, g.getPaint());
        }
    }

    private void updateScore() {
        for (int i = 0; i < cactusList.size(); ++i) {
            int cactusX = cactusList.get(i).getRect().right;
            int playerX = player.getRect().left;

            if (cactusX <  playerX && !cactusList.get(i).isPassed()) {
                cactusList.get(i).setPassed(true);
                score++;
                Assets.playSound(Assets.wingSoundId);
            }
        }
    }

    private void checkGameOver() {
        for (Cactus cactus : cactusList) {
            if (player.getRect().intersect(cactus.getRect())) {
                isGameOver = true;
                setCurrentState(new GameOverState(score));
                Assets.playSound(Assets.hitSoundId);
            }
        }
    }

}












