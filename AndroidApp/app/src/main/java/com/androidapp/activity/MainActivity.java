package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
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

        testEndpoints(); // A ENLEVER

        setSupportActionBar(toolbar);
    }

    // A ENLEVER
    public void testEndpoints() {
        Items items = new Items(this);
        items.getItemList("getItemList", mToken);
        items.addItem("addItem", mToken, "Tournevis", "2017-04-01", "2018-04-01", "2018-03-01", true, true);
    }

    @OnClick(R.id.btnNewReceipt)
    public void newReceipt() {
        Intent i = new Intent(getApplicationContext(),
                AddReceiptActivity.class);
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
