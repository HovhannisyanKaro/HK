package com.hk.single_task_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hk.R;

public class Main22Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
    }

    public void goTONext(View view){
        startActivity(new Intent(this, Main23Activity.class));
    }
}
