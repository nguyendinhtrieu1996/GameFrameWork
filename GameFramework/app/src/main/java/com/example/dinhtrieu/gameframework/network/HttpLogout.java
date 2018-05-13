package com.example.dinhtrieu.gameframework.network;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.model.TokenModel;
import com.example.dinhtrieu.gameframework.util.Config;
import com.example.dinhtrieu.gameframework.util.HttpAccountCallback;
import com.example.dinhtrieu.gameframework.util.HttpCallback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpLogout extends AsyncTask<Void, Void, Integer> {

    //Variable
    public HttpAccountCallback delegate;
    private Context context;

    //Init
    public HttpLogout(Context context) {
        this.context = context;
    }


    @Override
    protected void onPostExecute(Integer statusCode) {
        super.onPostExecute(statusCode);
        delegate.didFinishLogout(statusCode);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String urlString = Config.baseAPI + "/api/Account/Logout";
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

            httpURLConnection.setRequestProperty("Authorization", "Bearer " + Authentication.getIntance().getToken(context));
            httpURLConnection.connect();

            try
            {
                Log.d("HTTPLOGIN", "statuscode " + httpURLConnection.getResponseCode());
                Log.d("HTTPLOGIN", "messsage " + httpURLConnection.getResponseMessage());

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream input = httpURLConnection.getInputStream();
                    InputStreamReader inputreader = new InputStreamReader(input);
                    BufferedReader r = new BufferedReader(inputreader);

                    while ((c = r.read()) != -1) {
                        result += (char)c;
                    }
                    Log.d("HTTPLOGOUT", result);
                }
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
