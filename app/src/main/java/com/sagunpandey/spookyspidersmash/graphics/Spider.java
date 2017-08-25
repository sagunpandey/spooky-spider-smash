package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.sagunpandey.spookyspidersmash.helper.Utility;

/**
 * Created by sagun on 8/10/2017.
 */

public class Spider implements DrawableObject {

    private static final int SPEED_MAXIMUM = 2;
    private static final int SPEED_MINIMUM = 5;

    private double speed = SPEED_MINIMUM;
    private boolean dead = false;
    private boolean reached = false;

    private float lifeTimer;

    private Bitmap state;
    private Point currentPoint = new Point(0, 0);
    private double radius;

    private boolean initialized = false;

    @Override
    public void initialize(Context context, Canvas canvas) {
        if(!initialized) {
            SpiderAssets assets = SpiderAssets.get();
            assets.initialize(context, canvas);

            radius = assets.spiderMoving1.getWidth() * 0.5f;

            setStartPosition(canvas);
            setSpeed(canvas);
            startLife();

            initialized = true;
        }
    }

    @Override
    public void load(Canvas canvas, Context context) {
        initialize(context, canvas);

        crawl(canvas);
    }

    private void startLife() {
        lifeTimer = System.nanoTime() / 1000000000f;
    }

    private void setStartPosition(Canvas canvas) {
        SpiderAssets assets = SpiderAssets.get();

        int minX = 0;
        int maxX = canvas.getWidth() - assets.spiderMoving1.getWidth();

        int x = Utility.getRandomNumberInRange(minX, maxX);
        int y = 0 - assets.spiderMoving1.getHeight();

        currentPoint.set(x, y);
    }

    private void setSpeed(Canvas canvas) {
        int randomSpeed = Utility.getRandomNumberInRange(SPEED_MAXIMUM, SPEED_MINIMUM);
        speed = canvas.getWidth() / randomSpeed;
    }

    private void crawl(Canvas canvas) {
        SpiderAssets assets = SpiderAssets.get();

        float currentTime = System.nanoTime() / 1000000000f;
        float elapsedTime = currentTime - lifeTimer;
        lifeTimer = currentTime;

        int x = currentPoint.x;
        int y = currentPoint.y;

        if(!dead && !reached) {
            y = (int) (currentPoint.y + (speed * elapsedTime));

            float timeDiv = System.currentTimeMillis() / 100 % 10;
            if(timeDiv < 4) {
                state = assets.spiderMoving1;
            } else if(timeDiv < 7) {
                state = assets.spiderMoving2;
            } else {
                state = assets.spiderMoving3;
            }
        } else {
            state = assets.spiderDead;
        }

        currentPoint.set(x, y);
        canvas.drawBitmap(state, x, y, null);
    }

    public boolean wasHit(int x, int y) {
        if(state == null)
            return false;

        int centerX = currentPoint.x + state.getWidth() / 2;
        int centerY = currentPoint.y + state.getHeight() / 2;

        double dis = Math.sqrt((x-centerX)*(x-centerX)+(y-centerY)*(y-centerY));
        return dis <= (radius * 0.66);
    }

    public boolean isDead() {
        return dead;
    }

    public void die() {
        dead = true;
    }

    public void reached() {
        reached = true;
    }

    public Point getCurrentPosition() {
        return currentPoint;
    }
}
