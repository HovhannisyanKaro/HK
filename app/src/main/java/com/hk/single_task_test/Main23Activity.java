package com.hk.single_task_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main23Activity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    private int p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);
        ButterKnife.bind(this);
        p++;
        tv.setText("" + p);
    }

    public void goNext(View view){
        startActivity(new Intent(this, Main24Activity.class));
    }
}
