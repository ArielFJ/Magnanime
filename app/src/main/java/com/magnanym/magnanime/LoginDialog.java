package com.magnanym.magnanime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.button.MaterialButton;
import com.magnanym.magnanime.api.UserCalls;
import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.models.UserCreateModel;

public class LoginDialog extends DialogFragment {

    MutableLiveData<User> user;

    EditText username, password;
    MaterialButton submit, close;

    ImageButton showPw;

    UserCalls userCalls;

    public LoginDialog(MutableLiveData<User> user) {
        this.user = user;
        userCalls = UserCalls.getInstance(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.login_layout, getActivity().findViewById(R.id.dialogContainer));
        builder.setView(root);

        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        submit = root.findViewById(R.id.btnSubmit);
        close = root.findViewById(R.id.close);
        showPw = root.findViewById(R.id.btnShowPw);

        showPw.setOnClickListener(v -> {
            TransformationMethod method = password.getTransformationMethod();
            if(method instanceof PasswordTransformationMethod){
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            password.setSelection(password.getText().toString().length());
        });

        AlertDialog alert = builder.create();

        submit.setOnClickListener(v -> {
            if(username.getText().toString().trim().length() > 0 &&
                    password.getText().toString().trim().length() > 0 ){
                submitData(alert);
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "Fill all the fields before submit.", Toast.LENGTH_SHORT).show();
            }
        });

        close.setOnClickListener( v-> alert.dismiss());

        if(alert.getWindow() != null) {
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        return alert;
    }

    private void submitData(AlertDialog alertDialog) {
        User userObj = new User(username.getText().toString(), password.getText().toString());
        userCalls.logUser(new UserCreateModel(userObj), user);

        alertDialog.dismiss();
    }
}
