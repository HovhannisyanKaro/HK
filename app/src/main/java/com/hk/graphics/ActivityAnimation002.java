package com.hk.graphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hk.R;

public class ActivityAnimation002 extends AppCompatActivity {
    ActivityAnimation002_Layout activityAnimation002_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAnimation002_layout = new ActivityAnimation002_Layout(this);
        setContentView(activityAnimation002_layout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityAnimation002_layout.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityAnimation002_layout.resume();
    }


}
