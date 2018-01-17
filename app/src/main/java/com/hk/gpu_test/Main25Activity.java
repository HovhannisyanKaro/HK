package com.hk.gpu_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;



public class Main25Activity extends AppCompatActivity {

    @BindView(R.id.gpu_image_view)
    GPUImageView gpuImageView;

    GPUImageFilter mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main25);
        ButterKnife.bind(this);
    }
}
