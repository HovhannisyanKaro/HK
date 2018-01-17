package com.hk.camera_preview_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hk.R;
import com.hk.camera_preview_app.image_processing.ImageProcess;
import com.hk.main.utils.BitmapUtils;
import com.hk.main.utils.LogUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImage;

import static com.hk.camera_preview_app.image_processing.CropActivity.RotateBitmap;

public class Main14Activity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.surfaceView)
    GLSurfaceView surfaceView;
    @BindView(R.id.img_switch_camera)
    ImageView cameraSwitchView;
    @BindView(R.id.cp_img)
    ImageView cpImg;
    @BindView(R.id.button_capture)
    ImageButton buttonCapture;
    @BindView(R.id.iv_camera_frame)
    ImageView mTemplateImg;

    private static  ImageView cpImgStatic;

    private CropHandler mCropHandler;


    public static final int DISPLAY_IMAGE = 3;

    private int mImageHeight, mImageWidth;
    private int mScreenWidth, mScreenHeight;
    private int mTemplateWidth, mTemplateHeight;

    private GPUImage mGPUImage;


    private CameraHelper mCameraHelper;
    private CameraLoader mCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);
        ButterKnife.bind(this);

        cpImgStatic = cpImg;
        mGPUImage = new GPUImage(this);
        mGPUImage.setGLSurfaceView(surfaceView);

        mCameraHelper = new CameraHelper(this);
        mCamera = new CameraLoader();
        cameraSwitchView.setOnClickListener(this);
        if (!mCameraHelper.hasFrontCamera() || !mCameraHelper.hasBackCamera()) {
            cameraSwitchView.setVisibility(View.GONE);
        }

        buttonCapture.setOnClickListener(this);

        getScreenSizeInPixels();
        Bitmap faceTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.camera_frame_0);
        mTemplateWidth = faceTemplate.getWidth();
        mTemplateHeight = faceTemplate.getHeight();

        // Set template image accordingly to device screen size.
        if (mScreenWidth == 320 && mScreenHeight == 480) {
            mTemplateWidth = 218;
            mTemplateHeight = 300;
            faceTemplate = Bitmap.createScaledBitmap(faceTemplate, mTemplateWidth, mTemplateHeight, true);
            mTemplateImg.setImageBitmap(faceTemplate);
        }

        mCropHandler = new CropHandler(this);
    }

    private void getScreenSizeInPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume();
    }

    @Override
    protected void onPause() {
        mCamera.onPause();
        super.onPause();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.img_switch_camera:
                mCamera.switchCamera();
                break;
            case R.id.button_capture:
                if (mCamera.mCameraInstance.getParameters().getFocusMode().equals(
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    takePicture();
                } else {
                    mCamera.mCameraInstance.autoFocus(new Camera.AutoFocusCallback() {

                        @Override
                        public void onAutoFocus(final boolean success, final Camera camera) {
                            takePicture();
                        }
                    });
                }
                break;
        }
    }

    private void takePicture() {
        // TODO get a size that is about the size of the screen
        Camera.Parameters params = mCamera.mCameraInstance.getParameters();
        params.setRotation(90);
        mCamera.mCameraInstance.setParameters(params);
        for (Camera.Size size : params.getSupportedPictureSizes()) {
            LogUtils.d("ASDF", "Supported: " + size.width + "x" + size.height);
        }
        mCamera.mCameraInstance.takePicture(null, null,
                new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] bytes, final Camera camera) {
                        Bitmap photoImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        if (photoImg != null) {
                            cpImgStatic.setImageBitmap(RotateBitmap(photoImg, 90f));
                            mImageHeight = photoImg.getHeight();
                            mImageWidth = photoImg.getWidth();
                            cpImgStatic.buildDrawingCache(true);
                            cpImgStatic.setDrawingCacheEnabled(true);
                            mTemplateImg.buildDrawingCache(true);
                            mTemplateImg.setDrawingCacheEnabled(true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Crop image using the correct template size.
                                    Bitmap croppedImg = null;
                                    if (mScreenWidth == 320 && mScreenHeight == 480) {

                                        croppedImg = ImageProcess.cropImageVer2(cpImgStatic.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                                    } else {
                                        croppedImg = ImageProcess.cropImageVer2(cpImgStatic.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                                    }
                                    cpImgStatic.setDrawingCacheEnabled(false);
                                    mTemplateImg.setDrawingCacheEnabled(false);
                                    // Send a message to the Handler indicating the Thread has finished.
                                    mCropHandler.obtainMessage(DISPLAY_IMAGE, -1, -1, croppedImg).sendToTarget();

                                }
                            }).start();

                        }
                    }
                });
    }

    private class CameraLoader {

        private int mCurrentCameraId = 0;
        private Camera mCameraInstance;

        public void onResume() {
            setUpCamera(mCurrentCameraId);
        }

        public void onPause() {
            releaseCamera();
        }

        public void switchCamera() {
            releaseCamera();
            mCurrentCameraId = (mCurrentCameraId + 1) % mCameraHelper.getNumberOfCameras();
            setUpCamera(mCurrentCameraId);
        }

        private void setUpCamera(final int id) {
            mCameraInstance = getCameraInstance(id);
            Camera.Parameters parameters = mCameraInstance.getParameters();
            // TODO adjust by getting supportedPreviewSizes and then choosing
            // the best one for screen size (best fill screen)
            if (parameters.getSupportedFocusModes().contains(
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

            mCameraInstance.setParameters(parameters);

            int orientation = mCameraHelper.getCameraDisplayOrientation(Main14Activity.this, mCurrentCameraId);
            CameraHelper.CameraInfo2 cameraInfo = new CameraHelper.CameraInfo2();
            mCameraHelper.getCameraInfo(mCurrentCameraId, cameraInfo);
            boolean flipHorizontal = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
            mGPUImage.setUpCamera(mCameraInstance, orientation, flipHorizontal, false);
        }

        /**
         * A safe way to get an instance of the Camera object.
         */


        private Camera getCameraInstance(final int id) {
            Camera c = null;
            try {
                c = mCameraHelper.openCamera(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return c;
        }

        private void releaseCamera() {
            mCameraInstance.setPreviewCallback(null);
            mCameraInstance.release();
            mCameraInstance = null;
        }
    }

    private static class CropHandler extends Handler {
        WeakReference<Main14Activity> mThisCA;

        CropHandler(Main14Activity ca) {
            mThisCA = new WeakReference<Main14Activity>(ca);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Main14Activity ca = mThisCA.get();
            if (msg.what == DISPLAY_IMAGE) {
                Bitmap cropImg = (Bitmap) msg.obj;
                goToEditPhotoActivity(cropImg, ca);
            }
        }

        private void goToEditPhotoActivity(Bitmap bitmap, Context context) {
            cpImgStatic.setImageBitmap(null);
            Intent intent = new Intent(context, Main15Activity.class);
            intent.putExtra("croped_image", BitmapUtils.convertBitmapToByteArray(bitmap));
            context.startActivity(intent);
        }
    }
}
