package com.androidapp.activity;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Token;
import com.androidapp.network.Auth;
import com.androidapp.network.NetworkError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements MyCallback {

    @BindView(R.id.btnLinkToRegisterScreen)
    TextView btnLinkToRegister;
    @BindView(R.id.username)
    EditText inputUsername;
    @BindView(R.id.password)
    EditText inputPassword;

    private ProgressDialog pDialog;
    private String header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String username = getIntent().getStringExtra("USERNAME");
        if (username != null)
            inputUsername.setText(username);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    @OnClick(R.id.btnLinkToRegisterScreen)
    public void goToRegister() {
        Intent i = new Intent(getApplicationContext(),
                RegisterActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        boolean err = false;

        if (username.trim().isEmpty()) {
            err = true;
            inputUsername.setError(getString(R.string.field_required));
        }
        if (password.trim().isEmpty()) {
            err = true;
            inputPassword.setError(getString(R.string.field_required));
        }

        if (!err) {
            Auth auth = new Auth(this);
            auth.login("login", username, password);
        }
    }

    @Override
    public void successCallback(String tag, Object object) {
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        i.putExtra("TOKEN", ((Token) object).mToken);
        startActivity(i);
        finish();
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {

        Log.d("errorCallback: ", error.mMessage);
    }
}
