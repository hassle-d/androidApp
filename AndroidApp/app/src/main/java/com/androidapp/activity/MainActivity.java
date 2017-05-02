package com.androidapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.androidapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnNewReceipt, btnViewReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNewReceipt = (Button) findViewById(R.id.btnNewReceipt);
        btnViewReceipts = (Button) findViewById(R.id.btnViewReceipts);

        btnNewReceipt.setOnClickListener(this);
        btnViewReceipts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewReceipt:
                break;
            case R.id.btnViewReceipts:
                break;
        }
    }
}
