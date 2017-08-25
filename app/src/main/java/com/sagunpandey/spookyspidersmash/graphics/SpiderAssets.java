package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sagunpandey.spookyspidersmash.R;

/**
 * Created by sagun on 8/11/2017.
 */

public class SpiderAssets {

    public Bitmap spiderMoving1;
    public Bitmap spiderMoving2;
    public Bitmap spiderMoving3;
    public Bitmap spiderDead;

    private boolean initialized = false;

    private static SpiderAssets spiderAssets;

    private SpiderAssets() {

    }

    public static SpiderAssets get() {
        if(spiderAssets == null) {
            spiderAssets = new SpiderAssets();
        }
        return spiderAssets;
    }


    public void initialize(Context context, Canvas canvas) {
        if(!initialized) {
            initializeGraphics(context, canvas);

            initialized = true;
        }
    }

    private void initializeGraphics(Context context, Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int spiderWidth = (int) (canvasWidth * 0.15f);
        float scaleFactor;

        BitmapManager manager = new BitmapManager(context);
        spiderMoving1 = manager.newBitmap(R.drawable.alien_spider_1);
        scaleFactor = (float) spiderWidth / (float) spiderMoving1.getWidth();
        spiderMoving1 = BitmapManager.scaleBitmap(spiderMoving1, scaleFactor, true);

        spiderMoving2 = manager.newBitmap(R.drawable.alien_spider_2);
        spiderMoving2 = BitmapManager.scaleBitmap(spiderMoving2, scaleFactor, true);

        spiderMoving3 = manager.newBitmap(R.drawable.alien_spider_3);
        spiderMoving3 = BitmapManager.scaleBitmap(spiderMoving3, scaleFactor, true);

        spiderDead = manager.newBitmap(R.drawable.alien_spider_dead);
        spiderDead = BitmapManager.scaleBitmap(spiderDead, scaleFactor, true);
    }
}
