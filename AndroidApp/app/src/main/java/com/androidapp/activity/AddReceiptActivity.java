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
import com.androidapp.network.Items;

import butterknife.OnClick;


public class AddReceiptActivity extends AppCompatActivity  {

    private ImageView imageView;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        imageView = (ImageView)findViewById(R.id.imgPreview);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(AppConfig.CAMERA_CAPTURE_IMAGE_REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
