package com.hk.move_image_view_for_crop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.hk.R;
import com.hk.camera_preview_app.image_processing.gestures.MoveGestureDetector;
import com.hk.camera_preview_app.image_processing.gestures.RotateGestureDetector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main16Activity extends AppCompatActivity implements View.OnTouchListener {

    @BindView(R.id.cp_img)
    ImageView mImg;
    @BindView(R.id.cp_face_template)
    ImageView cpFaceTemplate;

    private ScaleGestureDetector mScaleDetector;
    private RotateGestureDetector mRotateDetector;
    private MoveGestureDetector mMoveDetector;

    private Matrix mMatrix = new Matrix();
    private float mScaleFactor = 0.8f;
    private float mRotationDegrees = 0.f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;

    private int mImageHeight, mImageWidth;
    private int mScreenWidth;
    private int mScreenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main16);
        ButterKnife.bind(this);

        mImg.setOnTouchListener(this);

        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mImg.setImageMatrix(mMatrix);

        // Get screen size in pixels.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;

        // Setup Gesture Detectors
        mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
        mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
        mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mRotateDetector.onTouchEvent(event);
        mMoveDetector.onTouchEvent(event);

        float scaledImageCenterX = (mImageWidth * mScaleFactor) / 2;
        float scaledImageCenterY = (mImageHeight * mScaleFactor) / 2;

        Bitmap photoImg = BitmapFactory.decodeResource(getResources(), R.drawable.temp_image);
        mImg.setImageBitmap(photoImg);
        mImageHeight = photoImg.getHeight();
        mImageWidth = photoImg.getWidth();


        mMatrix.reset();
        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mMatrix.postRotate(mRotationDegrees, scaledImageCenterX, scaledImageCenterY);
        mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);

        ImageView view = (ImageView) v;
        view.setImageMatrix(mMatrix);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees -= detector.getRotationDegreesDelta();
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;

            return true;
        }
    }
}
