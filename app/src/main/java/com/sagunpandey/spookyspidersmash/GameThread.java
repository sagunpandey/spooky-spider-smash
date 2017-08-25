package com.sagunpandey.spookyspidersmash;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.sagunpandey.spookyspidersmash.engine.GameEngine;

/**
 * Created by sagun on 8/9/2017.
 */

public class GameThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private Context context;
    private Handler handler;

    private GameEngine engine;

    private int touchX;
    private int touchY;
    private boolean touched = false;

    private boolean isRunning = false;
    private boolean firstRun = true;

    private boolean paused = false;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        this.handler = handler;

        engine = GameEngine.get();
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if(!paused) {
                        if(engine.isPaused())
                            engine.onResume();
                        engine.initGraphics(context, canvas);
                        engine.initSounds(context);
                        if(touched) {
                            engine.handleTouch(touchX, touchY);
                            touched = false;
                        }
                        engine.updateGraphics(canvas, context);
                        if(!firstRun) {
                            engine.update(context, handler);
                        } else {
                            firstRun = false;
                        }
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void onPause() {
        synchronized(surfaceHolder) {
            engine.onPause();
            paused = true;
        }
    }

    public void onResume() {
        synchronized(surfaceHolder) {
            engine.onResume();
            paused = false;
        }
    }

    public void notifyGameQuit() {
        synchronized(surfaceHolder) {
            engine.reset();
        }
    }

    public void handleTouch(int x, int y) {
        synchronized (surfaceHolder) {
            this.touchX = x;
            this.touchY = y;
            touched = true;
        }
    }
}
