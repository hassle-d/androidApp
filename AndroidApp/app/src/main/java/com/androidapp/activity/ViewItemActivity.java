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
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.models.Token;
import com.androidapp.models.ValidToken;
import com.androidapp.network.Auth;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewItemActivity extends AppCompatActivity implements MyCallback {

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.alias)
    TextView mAlias;
    @BindView(R.id.end)
    TextView mEnd;
    @BindView(R.id.buy)
    TextView mBuy;
    @BindView(R.id.notifications)
    TextView mNotifications;

    private ProgressDialog pDialog;
    private String header;
    private String mToken;
    private String mItemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
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
        if (item.mImages.size()>0) {
            Picasso.with(this)
                    .load(getResources().getString(R.string.api_url) + "/api/image/" + item.mImages.get(0))
                    .placeholder(R.drawable.blank2)
                    .error(R.drawable.blank2)
                    .resize(70, 70)
                    .into(mImage);
        }
        mAlias.setText(item.mAlias);
        mBuy.setText(item.mDateBuy.substring(0, 10));
        mEnd.setText(item.mDateEnd.substring(0, 10));
        mNotifications.setText(item.mDateNotification.substring(0, 10));
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
