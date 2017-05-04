package com.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.models.ValidToken;
import com.androidapp.network.Auth;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewItemActivity extends AppCompatActivity implements MyCallback {

    private ProgressDialog pDialog;
    private String header;
    private String mToken;
    private String mItemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mToken = getIntent().getStringExtra("TOKEN");
        mItemId = getIntent().getStringExtra("ITEMID");

        Items items = new Items(this);
        items.getItem("getItem", mToken, mItemId);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    private void setInfos(Item item) {
        Log.d("setInfos: ", item.mAlias);
    }

    @Override
    public void successCallback(String tag, Object object) {
        switch (tag) {
            case "getItem":
                setInfos((Item) object);
                break;
        }
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {

        Log.d("errorCallback: ", error.mMessage);
    }
}
