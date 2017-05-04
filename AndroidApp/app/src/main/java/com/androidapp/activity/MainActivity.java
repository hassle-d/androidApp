package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToken = getIntent().getStringExtra("TOKEN");

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.btnNewReceipt)
    public void newReceipt() {
        Intent i = new Intent(getApplicationContext(),
                AddReceiptActivity.class);
        i.putExtra("TOKEN", mToken);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnViewReceipts)
    public void viewReceipt() {

    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d(tag, "successCallback");
        switch (tag) {
            case "getItemList":
                List<Item> items = (List<Item>) object;
                Log.d(tag, "NB Items " + items.size());
                break;
            case "addItem":
                break;

        }
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }
}
