package com.androidapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.androidapp.R;
import com.androidapp.adapters.ItemListAdapter;
import com.androidapp.interfaces.ItemListAdapterCallback;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.network.Auth;
import com.androidapp.models.Token;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MyCallback,ItemListAdapterCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.itemList)
    ListView itemList;

    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToken = getIntent().getStringExtra("TOKEN");

        Items items = new Items(this);
        items.getItemList("getItemList", mToken);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Auth auth = new Auth(this);
                auth.logout("logout", mToken);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btnNewReceipt)
    public void newReceipt() {
        Intent i = new Intent(getApplicationContext(),
                AddReceiptActivity.class);
        i.putExtra("TOKEN", mToken);
        startActivity(i);
    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d(tag, "successCallback");
        switch (tag) {
            case "getItemList":
                List<Item> items = (List<Item>) object;
                ItemListAdapter adapter = new ItemListAdapter(this, items, this);
                itemList.setAdapter(adapter);
                Log.d(tag, "NB Items " + items.size());
                break;
            case "addItem":
                break;
            case "logout":
                break;

        }
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }

    @Override
    public void showItem(String itemId) {
        Log.d("showItem: ", itemId);
        Intent i = new Intent(getApplicationContext(),
                ViewItemActivity.class);
        i.putExtra("TOKEN", mToken);
        i.putExtra("ITEMID", itemId);
        startActivity(i);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
