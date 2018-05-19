package com.example.dinhtrieu.gameframework.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dinhtrieu.gameframework.R;
import com.example.dinhtrieu.gameframework.network.HttpChangePass;
import com.example.dinhtrieu.gameframework.network.HttpLogin;
import com.example.dinhtrieu.gameframework.util.ChangPassCallback;

import java.util.HashMap;
import java.util.Map;

public class ChangePassDialog extends DialogFragment {

    private HttpChangePass httpChangePass;

    private EditText edtCurrentPass;
    private EditText edtNewPass;
    private EditText edtConfirmNewPass;

    public ChangePassDialog() {
        httpChangePass = new HttpChangePass();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_pass, null);
        edtCurrentPass = view.findViewById(R.id.edtCurrentPassword);
        edtNewPass = view.findViewById(R.id.edtNewPass);
        edtConfirmNewPass = view.findViewById(R.id.edtConfirmPass);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String currentPass = edtCurrentPass.getText().toString();
                        String newP = edtNewPass.getText().toString();
                        String confirmNewP = edtConfirmNewPass.getText().toString();

                        if (currentPass.equals("") || newP.equals("") || confirmNewP.equals("") || !newP.equals(confirmNewP)) {
                            return;
                        }

                        Map<String, String> params = new HashMap<>();
                        params.put("OldPassword", currentPass);
                        params.put("NewPassword", newP);
                        params.put("ConfirmPassword", confirmNewP);

                        httpChangePass = new HttpChangePass(params);
                        httpChangePass.execute();
                        httpChangePass.delegate = new ChangPassCallback() {
                            @Override
                            public void didFinishChangePass(int code) {

                            }
                        };

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

}
