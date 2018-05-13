package com.example.dinhtrieu.gameframework.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dinhtrieu.gameframework.Dialog.ChangePassDialog;
import com.example.dinhtrieu.gameframework.Dialog.ConfirmLogoutDialog;
import com.example.dinhtrieu.gameframework.R;
import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.network.HttpLogout;
import com.example.dinhtrieu.gameframework.util.HttpAccountCallback;
import com.example.dinhtrieu.gameframework.util.ToastUtil;

public class SettingActivity extends Activity implements View.OnClickListener, HttpAccountCallback {

    private HttpLogout httpLogout;
    public static String token;

    private ChangePassDialog changePassDialog;

    //UI Elements
    private Button btnChangePass;
    private Button btnLogOut;

    //Life Circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        init();
    }

    //Override
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePass:
                changePassDialog.show(getFragmentManager(), "ChangePass");
                break;
            case R.id.btnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Do you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                httpLogout.execute();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    @Override
    public void didFinishLogout(int code) {
        if (code == 200) {
            Authentication.getIntance().logout(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.showShortToast(getApplicationContext(), "Some Error");
        }
    }

    //Init
    private void init() {
        changePassDialog = new ChangePassDialog();
        token = Authentication.getIntance().getToken(getApplicationContext());
        btnChangePass = findViewById(R.id.btnChangePass);
        btnLogOut = findViewById(R.id.btnLogout);

        btnChangePass.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        httpLogout = new HttpLogout(getApplicationContext());
        httpLogout.delegate = this;
    }
}








