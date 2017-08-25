package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.sagunpandey.spookyspidersmash.R;
import com.sagunpandey.spookyspidersmash.engine.GameEngine;

/**
 * Created by sagun on 8/10/2017.
 */

public class GameMenu implements DrawableObject {

    private GameEngine engine;

    private Paint menuPaint;
    private Rect boundary;

    private int scoreX;
    private int scoreY;
    private Paint scorePaint;

    private Bitmap life;

    private boolean initialized = false;

    public GameMenu(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void initialize(Context context, Canvas canvas) {
        if(!initialized) {
            positionMenu(canvas);
            initMenuBackground();
            initLife(context);
            initScore(context);

            initialized = true;
        }
    }

    private void positionMenu(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        Point centerOfCanvas = new Point(canvasWidth / 2, canvasHeight / 2);

        int width = (int) (canvasWidth * 0.95f);
        int height = (int) (canvasHeight * 0.07f);

        int left = centerOfCanvas.x - (width / 2);
        int top = (int) (canvasHeight * 0.015f);
        int right = left + width;
        int bottom = top + height;

        boundary = new Rect(left, top, right, bottom);
    }

    private void initMenuBackground() {
        menuPaint = new Paint();
        menuPaint.setColor(Color.parseColor("#0D0D0D"));
        menuPaint.setStrokeWidth(3);
        menuPaint.setAlpha(170);
    }

    private void initLife(Context context) {
        BitmapManager manager = new BitmapManager(context);
        life = manager.newBitmap(R.drawable.life);

        int menuHeight = boundary.height();

        int lifeHeight = life.getHeight();
        float scaleFactor = (float) menuHeight/ (float) lifeHeight;

        life = BitmapManager.scaleBitmap(life, scaleFactor, true);
    }

    private void initScore(Context context) {
        int textSize = 30;

        scorePaint = new Paint();
        scorePaint.setColor(Color.GREEN);
        scorePaint.setTextSize(textSize);
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "font/spidery.ttf");
        scorePaint.setTypeface(font);

        Rect bounds = new Rect();

        String dummyText = "SCORE: 0";
        scorePaint.getTextBounds(dummyText, 0, dummyText.length(), bounds);

        scoreX = boundary.left + (int) (boundary.width() * 0.01f);
        scoreY = boundary.bottom - (int) ((boundary.height() - bounds.height()) * 0.7f) ;
    }

    @Override
    public void load(Canvas canvas, Context context) {
        initialize(context, canvas);

        canvas.drawRect(boundary, menuPaint);
        drawLives(canvas);
        drawScore(canvas);
    }

    private void drawLives(Canvas canvas) {
        int wLife = life.getWidth();

        int x = boundary.right - wLife - (int) (boundary.width() * 0.01f);
        int y = boundary.top;

        for(int i = 0; i < engine.getAvailableLives(); i++) {
            canvas.drawBitmap(life, x, y, null);
            x = x - wLife;
        }
    }

    private void drawScore(Canvas canvas) {
        canvas.drawText("SCORE: " + String.valueOf(engine.getScore()), scoreX, scoreY, scorePaint);
    }
}
