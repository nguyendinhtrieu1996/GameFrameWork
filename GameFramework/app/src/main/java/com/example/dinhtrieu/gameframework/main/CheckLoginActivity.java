package com.example.dinhtrieu.gameframework.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dinhtrieu.gameframework.R;
import com.example.dinhtrieu.gameframework.businessmodel.Authentication;

public class CheckLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        if (Authentication.getIntance().getToken(getApplicationContext()) != null) {
            Intent intent = new Intent(getApplicationContext(), GameMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
