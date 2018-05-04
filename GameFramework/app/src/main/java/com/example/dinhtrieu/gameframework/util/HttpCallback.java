package com.example.dinhtrieu.gameframework.util;

import java.net.HttpURLConnection;

public interface HttpCallback {
    public void loginCallback(int statusCode);
    public void registerCallback(int statusCode);
}
