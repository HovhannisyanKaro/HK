package com.hk.edit_photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.hk.R;

import butterknife.BindView;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class Main12Activity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    GPUImageFilter mFilter;
    GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
    @BindView(R.id.gpuImage)
    GPUImageView mGPUImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(this);
        mGPUImageView = (GPUImageView) findViewById(R.id.gpuImage);
        findViewById(R.id.button_choose_filter).setOnClickListener(this);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        if (bm != null) {
            mGPUImageView.setImage(bm);
        }
    }

    private void switchFilterTo(final GPUImageFilter filter) {
        if (mFilter == null || (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
            mFilter = filter;
            mGPUImageView.setFilter(mFilter);
            mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter);
            findViewById(R.id.seekBar).setVisibility(mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (mFilterAdjuster != null) {
            mFilterAdjuster.adjust(progress);
        }
        mGPUImageView.requestRender();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button_choose_filter:
                GPUImageFilterTools.showDialog(this, new GPUImageFilterTools.OnGpuImageFilterChosenListener() {

                    @Override
                    public void onGpuImageFilterChosenListener(final GPUImageFilter filter) {
                        switchFilterTo(filter);
                        mGPUImageView.requestRender();
                    }

                });
                break;
            case R.id.button_save:
//                saveImage();
                break;

            default:
                break;
        }

    }
}
