package com.androidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.R;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadActivity extends AppCompatActivity implements MyCallback {

    private String filePath = null;
    private ImageView imgPreview;
    private String token;
    private String idItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent i = getIntent();

        filePath = i.getStringExtra("filePath");
        token = i.getStringExtra("TOKEN");
        idItem = i.getStringExtra("idItem");


        imgPreview = (ImageView)findViewById(R.id.imgPreview);
        if (filePath != null) {
            // Displaying the image or video on the screen

            imgPreview.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnUploadPicture)
    public void uploadImage() {
        Items items = new Items(this);
        items.addImage("", token, Uri.parse(filePath), idItem);
    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d("", "successCallback: ");
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
        finish();
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }
}
