package com.hk.edit_photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hk.R;
import com.hk.main.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * Created by Hovhannisyan.Karo on 12.12.2017.
 */

public class EditPhotoView extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    Unbinder unbinder;
    @BindView(R.id.seekBar)
    SeekBar seekBar;


    @BindView(R.id.rl_brightness)
    RelativeLayout rlBrightness;
    @BindView(R.id.rl_contrast)
    RelativeLayout rlContrast;
    @BindView(R.id.rl_exposure)
    RelativeLayout rlExposure;
    @BindView(R.id.rl_sharpness)
    RelativeLayout rlSharpness;
    @BindView(R.id.rl_saturation)
    RelativeLayout rlSaturation;
    @BindView(R.id.gpu_image_view)
    GPUImageView mGPUImageView;

    private GPUImageFilter mFilter;
    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;

    private Bitmap imageBitmap;

    public EditPhotoView(Context context) {
        super(context);
        init(context);
    }

    public EditPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_photo_edit, this, true);
        unbinder = ButterKnife.bind(this, v);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        mGPUImageView.setImage(bm);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public Bitmap getBitmap() {
        return imageBitmap;
    }

    public void setBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (mFilterAdjuster != null) {
            mFilterAdjuster.adjust(progress);
        }
        mGPUImageView.requestRender();
        LogUtils.d("progress", String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick({R.id.rl_brightness, R.id.rl_contrast, R.id.rl_exposure, R.id.rl_sharpness, R.id.rl_saturation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_contrast:
                switchFilterTo(new GPUImageContrastFilter(2.0f));
                mGPUImageView.requestRender();
                break;
            case R.id.rl_brightness:
                switchFilterTo(new GPUImageBrightnessFilter(1.5f));
                mGPUImageView.requestRender();
                break;
            case R.id.rl_exposure:
                switchFilterTo(new GPUImageExposureFilter(0.0f));
                mGPUImageView.requestRender();
                break;
            case R.id.rl_sharpness:
                switchFilterTo(new GPUImageSharpenFilter(2.0f));
                mGPUImageView.requestRender();
                break;
            case R.id.rl_saturation:
                switchFilterTo(new GPUImageSaturationFilter(1.0f));
                mGPUImageView.requestRender();
                break;
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
}
