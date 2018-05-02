package com.example.dinhtrieu.gameframework.state;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.dinhtrieu.gameframework.main.Assets;
import com.example.dinhtrieu.gameframework.main.GameMainActivity;
import com.example.dinhtrieu.gameframework.model.Cactus;
import com.example.dinhtrieu.gameframework.model.GameBackground;
import com.example.dinhtrieu.gameframework.model.Player;
import com.example.dinhtrieu.gameframework.util.Painter;
import com.example.dinhtrieu.gameframework.util.UIButton;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    private GameBackground gameBackground;
    private long fps = 60;
    private Player player;
    private List<Cactus> cactusList;

    @Override
    public void init() {
        load();
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
        fps = (long)(1 / delta);
        gameBackground.update(fps);
        Assets.animation.update(delta);
        player.update(delta);

        for (Cactus cactus : cactusList) {
            cactus.update(delta);
        }
    }

    @Override
    public void render(Painter g) {
        drawBackground(g);

        for (Cactus cactus : cactusList) {
            cactus.render(g);
        }

        Assets.animation.render(g, (int)player.getX(), (int) player.getY());
    }

    @Override
    public void load() {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        player.onTouch(e, scaledX, scaledY);
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

}












