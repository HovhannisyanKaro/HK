package com.hk.touch_imageview_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hk.R;

public class Main17Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        findViewById(R.id.single_touchimageview_button).setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main17Activity.this, SingleTouchImageViewActivity.class));
            }
        });
        findViewById(R.id.viewpager_example_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main17Activity.this, ViewPagerExampleActivity.class));
            }
        });
        findViewById(R.id.mirror_touchimageview_button).setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main17Activity.this, MirroringExampleActivity.class));
            }
        });
        findViewById(R.id.switch_image_button).setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main17Activity.this, SwitchImageExampleActivity.class));
            }
        });
        findViewById(R.id.switch_scaletype_button).setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main17Activity.this, SwitchScaleTypeExampleActivity.class));
            }
        });
    }
}
