package com.androidapp.helper;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.androidapp.app.AppConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Steph on 4/5/17.
 */

public class OutputMediaFile {

    // LogCat tag
    private static final String TAG = OutputMediaFile.class.getSimpleName();

    public OutputMediaFile() {
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment
                .getExternalStorageDirectory().getAbsolutePath(),
                AppConfig.IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if(!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + AppConfig.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == AppConfig.MEDIA_TYPE_IMAGE)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        else
            return null;
        return mediaFile;
    }
}
