package com.example.dinhtrieu.gameframework.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.util.Config;
import com.example.dinhtrieu.gameframework.util.HttpCallback;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpSaveScore extends AsyncTask<Void, Void, Integer> {

    //Variable
    private final String TAG = "HTTPScore";
    private Map<String, String> params= null;
    private Context context;

    //Init
    public HttpSaveScore() {}

    public HttpSaveScore(Map<String, String> params, Context context) {
        this.context = context;
        this.params = params;
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        super.onPostExecute(statusCode);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String urlString = Config.baseAPI + "/api/Scores";
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String urlParams = "";
        InputStream inputStream = null;
        String result = "";
        int c;

        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            StringBuilder sj = new StringBuilder();
            for(Map.Entry<String,String> entry : params.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            byte[] out = sj.toString().getBytes();

            httpURLConnection.setFixedLengthStreamingMode(out.length);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + Authentication.getIntance().getToken(context));
            httpURLConnection.connect();

            try
            {
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(out);
                os.flush();

                Log.d(TAG, "statuscode " + httpURLConnection.getResponseCode());
                Log.d(TAG, "messsage " + httpURLConnection.getResponseMessage());

                return httpURLConnection.getResponseCode();
            }
            catch (Exception e)
            {
                return HttpURLConnection.HTTP_BAD_REQUEST;
            }

        } catch (Exception ex) {
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
    }

}
