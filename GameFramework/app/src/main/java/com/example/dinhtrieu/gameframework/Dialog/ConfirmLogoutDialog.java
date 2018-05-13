package com.example.dinhtrieu.gameframework.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.dinhtrieu.gameframework.businessmodel.Authentication;
import com.example.dinhtrieu.gameframework.main.LoginActivity;
import com.example.dinhtrieu.gameframework.network.HttpLogout;
import com.example.dinhtrieu.gameframework.util.HttpAccountCallback;
import com.example.dinhtrieu.gameframework.util.ToastUtil;

public class ConfirmLogoutDialog extends DialogFragment implements HttpAccountCallback {

    private HttpLogout httpLogout;
    private Context context;

    public ConfirmLogoutDialog() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    private void ex() {
        httpLogout = new HttpLogout(getActivity());
        httpLogout.delegate = this;
        httpLogout.execute();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ex();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }

    @Override
    public void didFinishLogout(int code) {
        if (code == 200) {
            Authentication.getIntance().logout(getActivity());
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            //ToastUtil.showShortToast(context, "Some Error");
        }
    }
}
