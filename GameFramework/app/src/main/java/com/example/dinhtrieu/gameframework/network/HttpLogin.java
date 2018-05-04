package com.example.dinhtrieu.gameframework.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.model.TokenModel;
import com.example.dinhtrieu.gameframework.util.Config;
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

public class HttpLogin extends AsyncTask<Void, Void, Integer> {

    //Variable
    private Map<String, String> params= null;
    public HttpCallback delegate;

    //Init
    public HttpLogin() {}

    public HttpLogin(Map<String, String> params) {
        this.params = params;
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        super.onPostExecute(statusCode);
        delegate.loginCallback(statusCode);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String urlString = Config.baseAPI + "/Token";
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
            httpURLConnection.setRequestProperty("Host", Config.host);
            httpURLConnection.connect();

            try
            {
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(out);
                os.flush();

                Log.d("HTTPLOGIN", "statuscode " + httpURLConnection.getResponseCode());
                Log.d("HTTPLOGIN", "messsage " + httpURLConnection.getResponseMessage());

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream input = httpURLConnection.getInputStream();
                    InputStreamReader inputreader = new InputStreamReader(input);
                    BufferedReader r = new BufferedReader(inputreader);

                    while ((c = r.read()) != -1) {
                        result += (char)c;
                    }

                    JSONObject jsonObject = new JSONObject(result);
                    TokenModel tokenModel = new TokenModel(jsonObject);
                    Authentication.getIntance().setTokenModel(tokenModel);
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
