package com.hk.graphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hk.R;

public class AnimationActivity001 extends AppCompatActivity {

    private ActivityAnimation001_Layout activityAnimation001_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAnimation001_layout = new ActivityAnimation001_Layout(this);
        setContentView(activityAnimation001_layout);
    }
}
