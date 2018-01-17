package com.hk.cash_bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.hk.R;

public class Main6Activity extends AppCompatActivity {
    ImageView mImageView;
    ImageView mImageView2;
    private LruCache<String, Bitmap> mMemoryCache;
    private static final String BITMAP_CASHED_KEY = "bitmap_cashed_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        mImageView = (ImageView) findViewById(R.id.imageview_id);
        mImageView2 = (ImageView) findViewById(R.id.imageview_id_2);
        initBitmapCashLogic();
    }

    public void cash(View view) {
        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache();
        Bitmap bm = mImageView.getDrawingCache();
        addBitmapToMemoryCache(bm);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getCash(View view) {
        Bitmap returnedBitmap = getBitmapFromMemCache();
        mImageView2.setImageBitmap(returnedBitmap);
    }

    private void initBitmapCashLogic() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(Bitmap bitmap) {
        mMemoryCache.put(BITMAP_CASHED_KEY, bitmap);
    }

    public Bitmap getBitmapFromMemCache() {
        return mMemoryCache.get(BITMAP_CASHED_KEY);
    }

}
