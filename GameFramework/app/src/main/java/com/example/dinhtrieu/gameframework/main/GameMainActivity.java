package com.example.dinhtrieu.gameframework.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.dinhtrieu.gameframework.R;

public class GameMainActivity extends Activity {

    public  static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;
    public static GameView sGame;
    public static AssetManager assets;
    private static SharedPreferences prefs;
    private static final String highScoreKey = "highScoreKey";
    private static int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        prefs = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();
        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sGame.onResume();
    }

    @Override //onPause() is also run when the game is quit
    protected  void onPause() {
        super.onPause();
        sGame.onPause();
    }

    public static void setHighScore(int highScore) {
        GameMainActivity.highScore = highScore;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(highScoreKey, highScore);
        editor.commit();
    }

    private int retrieveHighScore() {
        return prefs.getInt(highScoreKey, 0);
    }

    public static int getHighScore() {
        return highScore;
    }
}
