package com.androidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidapp.R;
import com.androidapp.app.AppConfig;
import com.androidapp.helper.OutputMediaFile;
import com.androidapp.interfaces.MyCallback;
import com.androidapp.models.Item;
import com.androidapp.network.Items;
import com.androidapp.network.NetworkError;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.List;

import butterknife.BindView;


public class AddPicToReceiptActivity extends AppCompatActivity implements MyCallback {

    private ImageView imageView;
    private Uri fileUri;
    private String token;
    private String idItem;
    private Button validatePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pic_to_receipt);

        Intent i = getIntent();

        token = i.getStringExtra("TOKEN");
        idItem = i.getStringExtra("idItem");

        imageView = (ImageView)findViewById(R.id.imgPreview);
        Button capturedImageButton = (Button)findViewById(R.id.btnCapturePicture);
        validatePictureButton = (Button)findViewById(R.id.btnValidatePicture);

        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                OutputMediaFile outputMediaFile = new OutputMediaFile();

                fileUri = outputMediaFile.getOutputMediaFileUri(AppConfig.MEDIA_TYPE_IMAGE);

                Log.d("", "URI ==: " + fileUri);

                startActivityForResult(photoCaptureIntent, AppConfig.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        validatePictureButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items items = new Items(getApplicationContext());

                items.addImage("addImage", token, fileUri, idItem);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(AppConfig.CAMERA_CAPTURE_IMAGE_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            validatePictureButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void successCallback(String tag, Object object) {
        Log.d("", "successCallback: ");
        switch (tag) {
            case "getItemList":
                List<Item> items = (List<Item>) object;
                Log.d(tag, "NB Items " + items.size());
                break;
            case "addItem":
                Intent i = new Intent(getApplicationContext(),
                        AddPicToReceiptActivity.class);
                i.putExtra("TOKEN", token);
                i.putExtra("idItem", ((Item) object).mId);
                startActivity(i);
                finish();
                break;
            case "addImage":
                Intent y = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(y);
                finish();
                break;
        }
    }

    @Override
    public void errorCallback(String tag, NetworkError error) {
        Log.d("errorCallback: ", error.mMessage);
    }

}
