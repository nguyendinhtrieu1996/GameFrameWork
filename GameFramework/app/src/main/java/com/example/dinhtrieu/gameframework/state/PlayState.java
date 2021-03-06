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
import java.util.concurrent.TimeUnit;

public class PlayState extends State {

    private static final String TAG = "PlayState";

    private GameBackground gameBackground;
    private Player player;
    private List<Cactus> cactusList;
    private UILabel labelScore;
    private int score;
    private boolean isGameOver;
    private UIButton pauseButton;

    private long startGameTime, endGameTime;
    private static final long completionTime = 30000;

    @Override
    public void init() {
        isGameOver = false;
        score = 0;
        labelScore = new UILabel("0", GameMainActivity.GAME_WIDTH / 2 - 6, 150);
        gameBackground = new GameBackground(GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player = new Player(40, 760, 180, 180);
        pauseButton = new UIButton(GameMainActivity.GAME_WIDTH - 200, 50, GameMainActivity.GAME_WIDTH - 100, 150, Assets.pauseButton, Assets.pauseTouchButton);

        cactusList = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            Cactus cactus = new Cactus(760, 200, 200, i);
            cactusList.add(cactus);
        }

        startGameTime = System.currentTimeMillis();
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
        endGameTime = System.currentTimeMillis();
        checkWin();
    }

    @Override
    public void render(Painter g) {
        drawBackground(g);
        pauseButton.render(g, true);

        for (Cactus cactus : cactusList) {
            cactus.render(g);
        }

        Assets.animation.render(g, (int)player.getX(), (int) player.getY());
        labelScore.render(g);
        renderTimer(g);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void load() {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            pauseButton.onTouchDown(scaledX, scaledY);

            if (!pauseButton.isButtonDown()) {
                player.onTouch(e, scaledX, scaledY);
                Assets.playSound(Assets.pointSoundId);
            }
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (pauseButton.isPressed(scaledX, scaledY)) {
                pauseButton.cancel();
                setPauseGame();
            }
        }

        return true;
    }

    //Feature
    private void renderTimer(Painter g) {
        long time = endGameTime - startGameTime;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(time);
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(time);

        g.drawString(min + " : " + sec, 60, 60, 40);
    }

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

    private void checkWin() {
        long time = (int)((endGameTime - startGameTime));

        if (time > completionTime) {
            setCurrentState(new GameCompletionState(score));
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












