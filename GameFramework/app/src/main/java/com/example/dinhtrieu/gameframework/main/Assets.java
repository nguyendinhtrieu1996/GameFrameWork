package com.example.dinhtrieu.gameframework.main;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.graphics.BitmapFactory.Options;
import android.os.Build;
import android.util.Log;

import com.example.dinhtrieu.gameframework.animation.Frame;
import com.example.dinhtrieu.gameframework.animation.LoopingAnimation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Assets {

    private static MediaPlayer mediaPlayer;

    private static SoundPool soundPool;

    public static Bitmap menuBackground, gamebackground, gameloadingbackground, gameoverbackground, cactus, menuboard, winMenuboard;
    public static Bitmap continueButton, quitButton, pauseButton, pauseTouchButton, playButton, exitButton, resumeButton,
            restartButton, newgamebutton, savescorebutton, savebutton, changepassbutton, highscorebutton;
    public static LoopingAnimation animation;

    public static int hitSoundId = -1, wingSoundId = -1, pointSoundId = -1;

    public static Typeface typeface;

    public static void load() {
        gameloadingbackground = Assets.loadBitmap("game_loading.jpg", true);
        gamebackground = Assets.loadBitmap("gamebackground.jpg", true);
        menuBackground = Assets.loadBitmap("menubackground.jpg", true);
        cactus = Assets.loadBitmap("cactus.png", true);
        gameoverbackground = Assets.loadBitmap("gameover_bg.png", true);
        menuboard = Assets.loadBitmap("menu.png", true);
        winMenuboard = Assets.loadBitmap("win_menu.png", true);

        playButton = Assets.loadBitmap("play_button.png", true);
        continueButton = Assets.loadBitmap("continue_button.png", true);
        quitButton = Assets.loadBitmap("quit_button.png", true);
        pauseButton = Assets.loadBitmap("pause.png", true);
        pauseTouchButton = Assets.loadBitmap("pause_touch.png", true);
        exitButton = Assets.loadBitmap("exit_button.png", true);
        restartButton = Assets.loadBitmap("restart_button.png", true);
        resumeButton = Assets.loadBitmap("resum_button.png", true);
        newgamebutton = Assets.loadBitmap("new_game_button.png", true);
        savescorebutton = Assets.loadBitmap("save_score_button.png", true);
        savebutton = Assets.loadBitmap("save_button.png", true);
        changepassbutton = Assets.loadBitmap("changepass_button.png", true);
        highscorebutton = Assets.loadBitmap("score_button.png", true);

        typeface = Typeface.create( Typeface.createFromAsset(GameMainActivity.assets, "fonts/pixel.TTF"), Typeface.BOLD);

        soundPool = buildSoundPool();
        hitSoundId = loadSound("sounds/Hit.mp3");
        wingSoundId = loadSound("sounds/Wing.mp3");
        pointSoundId = loadSound("sounds/Point.mp3");

        //Loaf sprite map and initial animaton
        List<Bitmap> bitmaps = loadListBitmap("sprite_map.png", true, 13);
        List<Frame> frames = new ArrayList<>();

        for (int i = 0; i < bitmaps.size(); ++i) {
            Frame frame = new Frame(bitmaps.get(i), 0.05);
            frames.add(frame);
        }

        animation = new LoopingAnimation(true, frames);

    }

    public static void onPause() {
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void onResume() {

    }

    //used for loading Bitmap Images
    public static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Options options = new Options();
        //  options.inDither = true;
        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                options);
        return bitmap;
    }

    public static List<Bitmap> loadListBitmap(String filename, boolean transparency, int qty) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Options options = new Options();
        //  options.inDither = true;
        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

        int width = bitmap.getWidth() / qty;
        int height = bitmap.getHeight();

        int x = 0;
        Bitmap cropBitmap;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < qty; ++i) {
            cropBitmap = Bitmap.createBitmap(bitmap, x, 0, width, height);
            bitmaps.add(cropBitmap);
            x += width;
        }

        return bitmaps;
    }

    //unloads the bitmap and clears up memory
    public static void unloadBitmap(Bitmap bitmap) {
        bitmap.recycle();
        bitmap = null;
    }

    // loads sounds (I think they have to be in .wav format)
    public static int loadSound(String filename) {
        int soundID = 0;
        if (soundPool == null) {
            buildSoundPool();
        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundID;
    }


    //SoundPool was deprecated as of android 5.0 (lolipop) so
    //we have to check what the version of android is in order
    //to determine which is the best way to load sound
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(25)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        return soundPool;
    }

    //converts a PictureDrawable to a Bitmap because hardware acceleration does
    //not support drawing Drawables or Pictures
    private static Bitmap pictureDrawableToBitmap(PictureDrawable pictureDrawable){
        Bitmap bitmap = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pictureDrawable.getPicture());
        return bitmap;
    }


    //play a sound that is already loaded into memory
    public static void playSound(int soundID) {
        if (soundPool != null) {
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
    }

    //Used to stream music without loading it into the memory
    public static void playMusic(String fileName, boolean looping) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            AssetFileDescriptor afd = GameMainActivity.assets.openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}














