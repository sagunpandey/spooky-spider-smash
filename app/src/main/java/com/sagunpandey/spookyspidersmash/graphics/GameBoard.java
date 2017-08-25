package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sagunpandey.spookyspidersmash.R;
import com.sagunpandey.spookyspidersmash.engine.GameEngine;

/**
 * Created by sagun on 8/10/2017.
 */

public class GameBoard implements DrawableObject {

    private GameEngine engine;

    private Bitmap background;

    private int foodMargin;

    private boolean initialized = false;

    public GameBoard(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void initialize(Context context, Canvas canvas) {
        if(!initialized) {
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();

            BitmapManager manager = new BitmapManager(context);

            background = manager.newBitmap(R.drawable.background);
            background = BitmapManager.scaleBitmap(background, canvasWidth, canvasHeight, true);

            foodMargin = canvasHeight - (int) (canvasHeight * 0.2);

            initialized = true;
        }
    }

    public void load(Canvas canvas, Context context) {
        initialize(context, canvas);

        canvas.drawBitmap(background, 0, 0, null);
    }

    public int getFoodMargin() {
        return foodMargin;
    }
}
