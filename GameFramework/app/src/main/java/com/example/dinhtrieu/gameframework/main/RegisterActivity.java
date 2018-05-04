package com.example.dinhtrieu.gameframework.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dinhtrieu.gameframework.R;
import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.network.HttpLogin;
import com.example.dinhtrieu.gameframework.network.HttpRegister;
import com.example.dinhtrieu.gameframework.util.HttpCallback;
import com.example.dinhtrieu.gameframework.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity implements View.OnClickListener, HttpCallback {

    private HttpLogin httpLogin;
    private HttpRegister httpRegister;
    private String username, pass, confirmPass;

    //UI Elements
    private EditText edtEmail, edtPass, edtConfirmPass;
    private Button btnSignUp;

    //Life circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        init();
    }

    //Override
    @Override
    public void onClick(View v) {
        username = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        confirmPass = edtConfirmPass.getText().toString();

        if (username.equals("") || pass.equals("") || confirmPass.equals("")) {
            ToastUtil.showShortToast(getApplicationContext(), "Please fill all data");
            return;
        }

        if (!pass.equals(confirmPass)) {
            ToastUtil.showShortToast(getApplicationContext(), "Password does not match");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("Email", username);
        params.put("Password", pass);
        params.put("ConfirmPassword", confirmPass);
        httpRegister = new HttpRegister(params);
        httpRegister.delegate = this;
        httpRegister.execute();
    }

    @Override
    public void loginCallback(int statusCode) {
        if (statusCode == 200) {
            Authentication.getIntance().saveToken(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), GameMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.showShortToast(getApplicationContext(), "Check Username and password");
        }
    }

    @Override
    public void registerCallback(int statusCode) {
        if (statusCode == 200) {
            Map<String, String> params = new HashMap<>();
            params.put("Username", username);
            params.put("Password", pass);
            params.put("grant_type", "password");
            httpLogin = new HttpLogin(params);
            httpLogin.delegate = this;
            httpLogin.execute();
        } else {
            ToastUtil.showShortToast(getApplicationContext(), "Check Username and password");
        }
    }

    //Init
    private void init() {
        edtConfirmPass = findViewById(R.id.edtRe_ConfirmPassword);
        edtEmail = findViewById(R.id.edtRe_UserName);
        edtPass = findViewById(R.id.edtRe_Password);
        btnSignUp = findViewById(R.id.btnRe_SignUp);

        httpLogin = new HttpLogin();
        httpRegister = new HttpRegister();
        btnSignUp.setOnClickListener(this);
        httpLogin.delegate = this;
        httpRegister.delegate = this;
    }

}






