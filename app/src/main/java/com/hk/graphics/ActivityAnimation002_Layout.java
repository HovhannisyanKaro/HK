package com.hk.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hk.R;

/**
 * Created by Hovhannisyan.Karo on 18.11.2017.
 */

public class ActivityAnimation002_Layout extends SurfaceView implements Runnable {

    Thread thread = null;
    boolean canDraw = false;
    Bitmap backGroundBCheck;
    Canvas canvas;
    SurfaceHolder surfaceHolder;


    public ActivityAnimation002_Layout(Context context) {
        super(context);
        surfaceHolder = getHolder();
        backGroundBCheck = BitmapFactory.decodeResource(getResources(), R.drawable.check);

    }

    @Override
    public void run() {
        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(backGroundBCheck,0,0, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        canDraw = false;
        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }
}
