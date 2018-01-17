package com.hk.custom_camera_preview;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hk.R;

import java.io.File;

public class Main4Activity extends AppCompatActivity {

    CropView myCropView;
    ImageView ivCropped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        myCropView = (CropView) findViewById(R.id.crop_view);
        ivCropped = (ImageView) findViewById(R.id.cropped);

        Uri imageUri = Uri.fromFile(new File("/sdcard/cats.jpg"));

        BitmapDrawable b = (BitmapDrawable) BitmapDrawable.createFromPath(imageUri.getPath());

        myCropView.setImageURI(imageUri);


    }

    public void onCrop(View view) {

        Bitmap bmp = BitmapFactory.decodeByteArray(myCropView.getCroppedImage(), 0, myCropView.getCroppedImage().length);
//        showDialog(myCropView.getCroppedBitmap(Bitmap.createScaledBitmap(bmp, ivCropped.getWidth(), ivCropped.getHeight(), false)));
        showDialog(bmp);
    }

    private void showDialog(Bitmap bitmap) {
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_image
                , null));
        ImageView iv = (ImageView) settingsDialog.findViewById(R.id.iv_dialog);
        iv.setImageBitmap(bitmap);
        settingsDialog.show();
    }


}
