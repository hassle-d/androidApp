package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.models.User;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReceiptActivity extends AppCompatActivity implements MyCallback {

    @BindView(R.id.alias)
    EditText inputAlias;
    @BindView(R.id.dateBuy)
    EditText inputDateBuy;
    @BindView(R.id.dateEnd)
    EditText inputDateEnd;
    @BindView(R.id.dateNotification)
    EditText inputDateNotification;

    private SwitchCompat inputEmailNotification;
    private SwitchCompat inputSmsNotification;
    private boolean emailNotification;
    private boolean smsNotification;

    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        inputEmailNotification = (SwitchCompat) findViewById(R.id.emailNotification);
        inputSmsNotification = (SwitchCompat) findViewById(R.id.smsNotification);
        mToken = getIntent().getStringExtra("TOKEN");

        inputEmailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    emailNotification = true;
                } else {
                    emailNotification = false;
                }
            }
        });

        inputSmsNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    smsNotification = true;
                } else {
                    smsNotification = false;
                }
            }
        });
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddReceipt)
    public void addReceipt() {
        String alias = inputAlias.getText().toString();
        String dateBuy = inputDateBuy.getText().toString();
        String dateEnd = inputDateEnd.getText().toString();
        String dateNotification = inputDateNotification.getText().toString();

        boolean err = false;

        if (alias.trim().isEmpty()) {
            err = true;
            inputAlias.setError(getString(R.string.field_required));
        }
        if (dateBuy.trim().isEmpty()) {
            err = true;
            inputDateBuy.setError(getString(R.string.field_required));
        }
        if (dateEnd.trim().isEmpty()) {
            err = true;
            inputDateEnd.setError(getString(R.string.field_required));
        }
        if (dateNotification.trim().isEmpty()) {
            err = true;
            inputDateNotification.setError(getString(R.string.field_required));
        }

        if (!err){
        Items items = new Items(this);
        items.addItem("addItem", mToken, alias, dateBuy, dateEnd, dateNotification, emailNotification, smsNotification);
        }
    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d("", "successCallback: ");
        Intent i = new Intent(getApplicationContext(),
                AddPicToReceiptActivity.class);
        i.putExtra("TOKEN", mToken);
        i.putExtra("idItem", ((Item) object).mId);
        startActivity(i);
        finish();
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }
}
