package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Token;
import com.androidapp.models.User;
import com.androidapp.network.Auth;
import com.androidapp.network.NetworkError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements MyCallback {

    @BindView(R.id.email)
    EditText inputEmail;
    @BindView(R.id.username)
    EditText inputUsername;
    @BindView(R.id.password)
    EditText inputPassword;
    @BindView(R.id.confpassword)
    EditText inputConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLinkToLoginScreen)
    public void goToLogin() {
        Intent i = new Intent(getApplicationContext(),
                LoginActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        String mail = inputEmail.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String confPassword = inputConfPassword.getText().toString();

        boolean err = false;

        if (mail.trim().isEmpty()) {
            err = true;
            inputEmail.setError(getString(R.string.field_required));
        }
        if (username.trim().isEmpty()) {
            err = true;
            inputUsername.setError(getString(R.string.field_required));
        }
        if (password.trim().isEmpty()) {
            err = true;
            inputPassword.setError(getString(R.string.field_required));
        }
        if (confPassword.trim().isEmpty()) {
            err = true;
            inputConfPassword.setError(getString(R.string.field_required));
        }
        if (!(password.equals(confPassword))) {
            err = true;
            inputConfPassword.setError(getString(R.string.password_not_same));
        }

        if (!err) {
            Auth auth = new Auth(this);
            auth.register("register", mail, username, password);
        }
    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d("", "successCallback: ");
        Intent i = new Intent(getApplicationContext(),
                LoginActivity.class);
        i.putExtra("USERNAME", ((User) object).mUsername);
        startActivity(i);
        finish();
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }
}
