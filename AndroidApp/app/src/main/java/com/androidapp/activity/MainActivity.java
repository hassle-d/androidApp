package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.androidapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)         Toolbar toolbar;
    @BindView(R.id.btnNewReceipt)   Button btnNewReceipt;
    @BindView(R.id.btnViewReceipts) Button btnViewReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        btnNewReceipt.setOnClickListener(this);
        btnViewReceipts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewReceipt:
                Intent i = new Intent(getApplicationContext(),
                        AddReceiptActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnViewReceipts:
                break;
        }
    }
}
