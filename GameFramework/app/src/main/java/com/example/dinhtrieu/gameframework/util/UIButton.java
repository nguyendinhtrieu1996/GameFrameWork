package com.example.dinhtrieu.gameframework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class UIButton {
    private static final String TAG = "UIButton";
    private Rect buttonRect;
    private boolean buttonDown;
    private Bitmap buttonImage, buttonDownImage;

    public UIButton(int left, int top, int right, int bottom, Bitmap buttonImage, Bitmap buttonPressedImage) {
        buttonRect = new Rect(left, top, right, bottom);

        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonPressedImage;
    }

    public UIButton(int left, int top, int right, int bottom, Bitmap buttonImage) {
        buttonRect = new Rect(left, top, right, bottom);
        Log.d(TAG, " left" + buttonRect.left + " top " + buttonRect.top + " width " + buttonRect.width() + " height " + buttonRect.height());
        this.buttonImage = buttonImage;
    }

    //draws the button. if your button has a separate image for
    // when it is pressed down, pass true to the boolean, else pass false
    public void render(Painter g, boolean hasDownImage) {
        if (hasDownImage) {
            Bitmap currentButtonImage = buttonDown ? buttonDownImage : buttonImage;
            g.drawImage(currentButtonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
        } else {
            Bitmap currentButtonImage = buttonImage;
            g.drawImage(currentButtonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
        }
    }

    public void onTouchDown(int touchX, int touchY) {
        if (buttonRect.contains(touchX, touchY)) {
            buttonDown = true;
        } else {
            buttonDown = false;
        }
    }

    public void cancel() {
        buttonDown = false;
    }

    public boolean isPressed(int touchX, int touchY) {
        return buttonDown && buttonRect.contains(touchX, touchY);
    }

}

