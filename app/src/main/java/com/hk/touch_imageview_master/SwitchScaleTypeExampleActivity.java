package com.hk.touch_imageview_master;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.hk.R;

public class SwitchScaleTypeExampleActivity extends AppCompatActivity {


    private TouchImageView image;
    private Activity activity;
    private static final ScaleType[] scaleTypes = {ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE, ScaleType.FIT_XY, ScaleType.FIT_CENTER};
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_scale_type_example);
        activity = this;
        image = (TouchImageView) findViewById(R.id.img);

        //
        // Set next scaleType with each button click
        //
        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                index = ++index % scaleTypes.length;
                ScaleType currScaleType = scaleTypes[index];
                image.setScaleType(currScaleType);
                Toast.makeText(activity, "ScaleType: " + currScaleType, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
