package com.example.dinhtrieu.gameframework.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void showShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
