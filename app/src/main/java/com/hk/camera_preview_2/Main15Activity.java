package com.hk.camera_preview_2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hk.R;
import com.hk.main.utils.BitmapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main15Activity extends AppCompatActivity {

    @BindView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
        ButterKnife.bind(this);

        Bitmap cutedFaceBitmap = BitmapUtils.convertByteArrayToBitmap(getIntent().getByteArrayExtra("croped_image"));
        imageView.setImageBitmap(cutedFaceBitmap);
    }
}
