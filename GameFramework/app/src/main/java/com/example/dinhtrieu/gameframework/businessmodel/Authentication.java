package com.example.dinhtrieu.gameframework.businessmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dinhtrieu.gameframework.model.TokenModel;

public class Authentication {

    private final String key = "TokenKey";
    private static Authentication instance = null;
    private TokenModel token = null;

    private Authentication() {
    }

    public static Authentication getIntance() {
        if (instance == null) {
            instance = new Authentication();
        }
        return instance;
    }

    public void setTokenModel(TokenModel model) {
        this.token = model;
    }

    public void saveToken(Context context) {
        if (token != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, token.getAccess_token());
            editor.commit();
        }
    }

    public String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
