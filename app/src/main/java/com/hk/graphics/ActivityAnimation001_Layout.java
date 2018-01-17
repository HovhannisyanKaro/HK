package com.hk.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

import com.hk.R;

/**
 * Created by Hovhannisyan.Karo on 18.11.2017.
 */

public class ActivityAnimation001_Layout extends View {

    private static int speed = 5;
    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke, blue_paintbrush_stroke, green_paintbrush_stroke;
    Path triangle;
    Bitmap cherry_bm;
    int cherry_x, cherry_y;
    int x_dir, y_dir;
    int bitmapWidth, bitmapHeight;

    public ActivityAnimation001_Layout(Context context) {
        super(context);
        setBackgroundResource(R.drawable.check);
        cherry_x = 320;
        cherry_y = 470;
        x_dir = speed;
        y_dir = speed;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Fill
        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        //Stroke
        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

        Rect rectangle001 = new Rect();
        rectangle001.set(210,150,250,175);
        canvas.drawRect(rectangle001, red_paintbrush_fill);

        canvas.drawCircle(400,400,40,red_paintbrush_stroke);
        canvas.drawCircle(400,400,20,blue_paintbrush_fill);

        triangle = new Path();
        triangle.moveTo(400,400);
        triangle.lineTo(600,600);
        triangle.moveTo(600,600);
        triangle.lineTo(200,600);
        triangle.moveTo(200,600);
        triangle.lineTo(400,400);

        canvas.drawPath(triangle, red_paintbrush_stroke);

        canvas.drawCircle(600,600,40,red_paintbrush_stroke);
        canvas.drawCircle(200,600,40,red_paintbrush_stroke);

        cherry_bm = BitmapFactory.decodeResource(getResources(), R.drawable.cherry);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.cherry, options);
        bitmapWidth = options.outWidth;
        bitmapHeight = options.outHeight;
        if (cherry_x >= canvas.getWidth() - bitmapWidth){
            x_dir = -speed;
        }
        if (cherry_x <= 0){
            x_dir = speed;
        }
        if (cherry_y >= canvas.getHeight() - bitmapHeight){
            y_dir = -speed;
        }
        if (cherry_y <= 0){
            y_dir = speed;
        }
        cherry_x = cherry_x + x_dir;
        cherry_y = cherry_y + y_dir;
        canvas.drawBitmap(cherry_bm, cherry_x, cherry_y, null);

        invalidate();

    }
}
