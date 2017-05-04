package com.androidapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidapp.R;
import com.androidapp.interfaces.ItemListAdapterCallback;
import com.androidapp.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damien on 5/4/2017.
 */

public class ItemListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    int i = 0;
    Item tempValues=null;
    private Activity activity;
    private List<Item> data;
    private ItemListAdapterCallback callback;

    public ItemListAdapter(Activity a, List<Item> d, ItemListAdapterCallback c){
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        callback = c;
    }

    @Override
    public int getCount() {
        if (data.size() <= 0){
            return 1;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
        tempValues = data.get(position);

        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.clickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView) v.getParent();
                final int position = listView.getPositionForView(v);
                callback.showItem(data.get(position).mId);
            }
        });


        holder.alias.setText(tempValues.mAlias);
        holder.dateEnd.setText(tempValues.mDateEnd.substring(0, 10));
        if (tempValues.mImages.size()>0) {
            Log.d("getView: ", activity.getResources().getString(R.string.api_url) + "/api/image/" + tempValues.mImages.get(0));
            Picasso.with(activity)
                    .load(activity.getResources().getString(R.string.api_url) + "/api/image/" + tempValues.mImages.get(0))
                    .placeholder(R.drawable.blank2)
                    .error(R.drawable.blank2)
                    .resize(70, 70)
                    .into(holder.image);
        }
        else {
            holder.image.setImageDrawable(activity.getDrawable(R.drawable.blank2));
        }


        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.alias)
        TextView alias;
        @BindView(R.id.buy)
        TextView dateBuy;
        @BindView(R.id.end)
        TextView dateEnd;
        @BindView(R.id.clickzone)
        RelativeLayout clickZone;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
