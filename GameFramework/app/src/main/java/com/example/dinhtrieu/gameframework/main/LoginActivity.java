package com.example.dinhtrieu.gameframework.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dinhtrieu.gameframework.R;
import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.network.HttpLogin;
import com.example.dinhtrieu.gameframework.util.HttpCallback;
import com.example.dinhtrieu.gameframework.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener, HttpCallback {

    //Variable
    private HttpLogin httpLogin;
    private String TAG = "LoginActivity";

    //UI Element
    private EditText edtUserName, edtPassword;
    private Button btnLogIn, btnCreateAccount;

    //Life Circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();
    }

    //Override
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();

                if (userName.equals("") || password.equals("")) {
                    ToastUtil.showShortToast(getApplicationContext(), "Username or password is empty");
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("Username", userName);
                params.put("Password", password);
                params.put("grant_type", "password");
                httpLogin = new HttpLogin(params);
                httpLogin.delegate = this;
                httpLogin.execute();

                break;
            case R.id.btnRegister:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loginCallback(int statusCode) {
        if (statusCode == 200) {
            Authentication.getIntance().saveToken(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), GameMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.showShortToast(getApplicationContext(), "Username or password incorrect");
        }
        Log.d(TAG, "statuscode " + statusCode);
    }

    @Override
    public void registerCallback(int statusCode) {

    }

    //Init
    private void init() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogIn = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnRegister);

        btnLogIn.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);

        httpLogin = new HttpLogin();
        httpLogin.delegate = this;
    }
}
