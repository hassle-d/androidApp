package com.androidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidapp.R;
import com.androidapp.app.AppConfig;
import com.androidapp.helper.OutputMediaFile;


public class AddPicToReceiptActivity extends AppCompatActivity  {

    private Uri fileUri;
    private String token;
    private String idItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pic_to_receipt);

        Intent i = getIntent();

        token = i.getStringExtra("TOKEN");
        idItem = i.getStringExtra("idItem");

        Button capturedImageButton = (Button)findViewById(R.id.btnCapturePicture);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                OutputMediaFile outputMediaFile = new OutputMediaFile();

                fileUri = outputMediaFile.getOutputMediaFileUri(AppConfig.MEDIA_TYPE_IMAGE);
                startActivityForResult(photoCaptureIntent, AppConfig.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(AppConfig.CAMERA_CAPTURE_IMAGE_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            launchUploadActivity();
        }
    }

    private void launchUploadActivity(){
        Intent i = new Intent(AddPicToReceiptActivity.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("TOKEN", token);

        startActivity(i);
    }
}
