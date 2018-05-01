package com.example.dinhtrieu.gameframework.animation;

import com.example.dinhtrieu.gameframework.util.Painter;

public class LoopingAnimation {
    private Frame[] frames;
    private double[] frameEndTimes;
    private int currentFrameIndex = 0;
    private double totalDuration = 0;
    private double currentTime = 0;

    private boolean looping = true;

    //this can take any number of frames and they will be displayed in the order they are added
    public LoopingAnimation(Boolean looping, Frame... frames) {
        this.looping = looping;
        this.frames = frames;
        frameEndTimes = new double[frames.length];
        for (int i = 0; i < frames.length; i++) {
            Frame f = frames[i];
            totalDuration += f.getDuration();
            frameEndTimes[i] = totalDuration;
        }
    }

    public synchronized void update(float increment) {
        currentTime += increment;
        if (currentTime > totalDuration && looping) {
            wrapAnimation();
        }
        if (currentFrameIndex < frameEndTimes.length) {
            while (currentTime > frameEndTimes[currentFrameIndex]) {
                currentFrameIndex++;
            }
        }
    }

    //restart the Animation
    private synchronized void wrapAnimation() {
        currentFrameIndex = 0;
        currentTime %= totalDuration;
    }

    public synchronized void render(Painter g, int x, int y) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y);
    }

    public synchronized void render(Painter g, int x, int y, int width, int height) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
    }
}
