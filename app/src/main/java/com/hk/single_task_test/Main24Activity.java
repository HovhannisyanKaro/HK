package com.hk.single_task_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hk.R;

public class Main24Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main24);

        startActivity(new Intent(this, Main23Activity.class));
    }
}
