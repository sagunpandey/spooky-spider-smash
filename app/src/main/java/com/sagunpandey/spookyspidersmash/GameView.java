package com.sagunpandey.spookyspidersmash;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by sagun on 8/9/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        getHolder().setFormat(0x00000004); // RGB_565
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (gameThread == null){
            Handler handler = new Handler();
            gameThread = new GameThread(surfaceHolder, getContext(), handler);
            gameThread.setRunning(true);
            gameThread.start();

            setFocusable(true);
            setFocusableInTouchMode(true);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void onPause() {
        if(gameThread != null) {
            gameThread.onPause();
            boolean retry = true;
            gameThread.setRunning(false);
            while (retry) {
                try {
                    gameThread.join();
                    gameThread = null;
                    retry = false;
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public void onResume() {
        if(gameThread != null)
            gameThread.onResume();
    }

    public void notifyGameQuit() {
        if(gameThread != null)
            gameThread.notifyGameQuit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x, y;
        int action = event.getAction();
        x = event.getX();
        y = event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            if (gameThread != null)
                gameThread.handleTouch((int) x, (int) y);
        }
        return true;
    }
}
