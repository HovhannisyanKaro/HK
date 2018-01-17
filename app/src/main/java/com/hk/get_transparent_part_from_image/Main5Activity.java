package com.hk.get_transparent_part_from_image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hk.R;
import com.hk.main.utils.LogUtils;

public class Main5Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.image_with_transparency);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bitmap.getWidth() / 5; i++) {//
            for (int j = 0; j < bitmap.getHeight() / 10; j++) {//
                builder.append(i + " " + j + " | ");
                int pixelColor = bitmap.getPixel(i, j);
                int transparency = ((bitmap.getPixel(i, j) & 0xff000000) >> 24);
                if (transparency == Color.TRANSPARENT) {
                } else {
                }
            }
            LogUtils.d(builder.toString());
//            builder.setLength(0);
        }

    }


}
