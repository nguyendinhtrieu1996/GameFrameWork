package com.example.dinhtrieu.gameframework.model;

import android.util.Log;

import org.json.JSONObject;

public class TokenModel {

    private static final String TAG = "TokenModel";
    private String access_token;

    public TokenModel() {
    }

    public TokenModel(JSONObject jsonObject) {
        try {
            access_token = jsonObject.getString("access_token");
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    public TokenModel(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }
}
