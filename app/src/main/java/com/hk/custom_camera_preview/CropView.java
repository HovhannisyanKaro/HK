package com.hk.custom_camera_preview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hk.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Hovhannisyan.Karo on 12.11.2017.
 */

public class CropView extends android.support.v7.widget.AppCompatImageView {

    Paint paint = new Paint();
    private int initial_size = 300;
    private static Point leftTop, rightBottom, center, previous;
    private Context context;

    private static final int DRAG = 0;
    private static final int LEFT = 1;
    private static final int TOP = 2;
    private static final int RIGHT = 3;
    private static final int BOTTOM = 4;

    private int imageScaledWidth, imageScaledHeight;

    // Adding parent class constructors
    public CropView(Context context) {
        super(context);
        initCropView(context);
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initCropView(context);
    }

    public CropView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCropView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (leftTop.equals(0, 0))
            resetPoints();
        canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y, paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                previous.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (isActionInsideRectangle(event.getX(), event.getY())) {
                    adjustRectangle((int) event.getX(), (int) event.getY());
                    invalidate(); // redraw rectangle
                    previous.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                previous = new Point();
                break;
        }
        return true;
    }

    private void initCropView(Context context) {
        this.context = context;
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        leftTop = new Point();
        rightBottom = new Point();
        center = new Point();
        previous = new Point();
    }

    public void resetPoints() {
        center.set(getWidth() / 2, getHeight() / 2);
        leftTop.set((getWidth() - initial_size) / 2, (getHeight() - initial_size) / 2);
        rightBottom.set(leftTop.x + initial_size, leftTop.y + initial_size);
    }

    private static boolean isActionInsideRectangle(float x, float y) {
        int buffer = 10;
        return (x >= (leftTop.x - buffer) && x <= (rightBottom.x + buffer) && y >= (leftTop.y - buffer) && y <= (rightBottom.y + buffer)) ? true : false;
    }

    private boolean isInImageRange(PointF point) {
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        getImageMatrix().getValues(f);

        // Calculate the scaled dimensions
        imageScaledWidth = Math.round(getDrawable().getIntrinsicWidth() * f[Matrix.MSCALE_X]);
        imageScaledHeight = Math.round(getDrawable().getIntrinsicHeight() * f[Matrix.MSCALE_Y]);

        return (point.x >= (center.x - (imageScaledWidth / 2)) && point.x <= (center.x + (imageScaledWidth / 2)) && point.y >= (center.y - (imageScaledHeight / 2)) && point.y <= (center.y + (imageScaledHeight / 2))) ? true : false;
    }

    private void adjustRectangle(int x, int y) {
        int movement;
        switch (getAffectedSide(x, y)) {
            case LEFT:
                movement = x - leftTop.x;
                if (isInImageRange(new PointF(leftTop.x + movement, leftTop.y + movement)))
                    leftTop.set(leftTop.x + movement, leftTop.y + movement);
                break;
            case TOP:
                movement = y - leftTop.y;
                if (isInImageRange(new PointF(leftTop.x + movement, leftTop.y + movement)))
                    leftTop.set(leftTop.x + movement, leftTop.y + movement);
                break;
            case RIGHT:
                movement = x - rightBottom.x;
                if (isInImageRange(new PointF(rightBottom.x + movement, rightBottom.y + movement)))
                    rightBottom.set(rightBottom.x + movement, rightBottom.y + movement);
                break;
            case BOTTOM:
                movement = y - rightBottom.y;
                if (isInImageRange(new PointF(rightBottom.x + movement, rightBottom.y + movement)))
                    rightBottom.set(rightBottom.x + movement, rightBottom.y + movement);
                break;
            case DRAG:
                movement = x - previous.x;
                int movementY = y - previous.y;
                if (isInImageRange(new PointF(leftTop.x + movement, leftTop.y + movementY)) && isInImageRange(new PointF(rightBottom.x + movement, rightBottom.y + movementY))) {
                    leftTop.set(leftTop.x + movement, leftTop.y + movementY);
                    rightBottom.set(rightBottom.x + movement, rightBottom.y + movementY);
                }
                break;
        }
    }

    private static int getAffectedSide(float x, float y) {
        int buffer = 10;
        if (x >= (leftTop.x - buffer) && x <= (leftTop.x + buffer))
            return LEFT;
        else if (y >= (leftTop.y - buffer) && y <= (leftTop.y + buffer))
            return TOP;
        else if (x >= (rightBottom.x - buffer) && x <= (rightBottom.x + buffer))
            return RIGHT;
        else if (y >= (rightBottom.y - buffer) && y <= (rightBottom.y + buffer))
            return BOTTOM;
        else
            return DRAG;
    }

    public byte[] getCroppedImage() {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        float x = leftTop.x - center.x + (drawable.getBitmap().getWidth() / 2);
        float y = leftTop.y - center.y + (drawable.getBitmap().getHeight() / 2);
        Bitmap cropped = Bitmap.createBitmap(drawable.getBitmap(), (int) x, (int) y, (int) rightBottom.x - (int) leftTop.x, (int) rightBottom.y - (int) leftTop.y);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropped.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

//    public Bitmap getCroppedBitmap(Bitmap bitmap) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
////        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,bitmap.getWidth() / 2, paint);
//        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ellipse);
////        canvas.drawBitmap(icon, leftTop.x, leftTop.y, paint);
//        canvas.drawBitmap(icon , icon.getHeight()/13,icon.getWidth()/13, paint);
////        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
//        //return _bmp;
//        return output;
//    }

//    public Bitmap getCroppedBitmap(Bitmap bitmap) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
//                bitmap.getWidth() / 2, paint);
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
//        //return _bmp;
//        return output;
//    }
}