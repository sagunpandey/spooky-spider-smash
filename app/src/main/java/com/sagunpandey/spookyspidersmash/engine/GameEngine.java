package com.sagunpandey.spookyspidersmash.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.Toast;

import com.sagunpandey.spookyspidersmash.audio.GameAudio;
import com.sagunpandey.spookyspidersmash.graphics.GameGraphics;
import com.sagunpandey.spookyspidersmash.helper.Utility;

/**
 * Created by sagun on 8/11/2017.
 */

public class GameEngine {

    public static final String PREFERENCE_PRIVATE = "spookyPrefs";

    private float gameTime = 0;
    private boolean gameStarted = false;
    private boolean gameStopped = false;
    private boolean isGameOver = false;

    private float lastSpawnTime = 0;
    private int spawnIn = 0;

    private static final int MAXIMUM_LIVES = 3;
    private int availableLives = MAXIMUM_LIVES;
    private int score = 0;

    private boolean paused;

    private GameGraphics gameGraphics;
    private GameAudio gameAudio;

    private static GameEngine gameEngine;

    private GameEngine() {
        gameGraphics = new GameGraphics(this);
        gameAudio = new GameAudio();
    }

    public static GameEngine get() {
        if(gameEngine == null) {
            gameEngine = new GameEngine();
        }

        return gameEngine;
    }

    public void initGraphics(Context context, Canvas canvas) {
        gameGraphics.initialize(context, canvas);
    }

    public void initSounds(Context context) {
        gameAudio.initialize(context);
    }

    public void update(Context context, Handler handler) {
        if(gameStopped) {
            return;
        }

        if(isGameOver) {
            gameOver(context, handler);
        } else {
            if(!gameStarted) {
                if(gameTime == 0) {
                    while(!gameAudio.isLoaded());
                    gameAudio.playGetReady();
                    gameTime = System.nanoTime() / 1000000000f;
                }
                float currentTime = System.nanoTime() / 1000000000f;
                float elapsedTime = currentTime - gameTime;
                if(elapsedTime >= 3) {
                    gameStarted = true;
                    gameAudio.playMusic();
                }
            } else {
                spawnSpider();
                gameGraphics.checkForSpiderCleanup();
                gameGraphics.checkIfSpiderReachedFood();
            }
        }
    }

    private void checkHighScore(final Context context, Handler handler) {
        SharedPreferences preferences = context
                .getSharedPreferences(PREFERENCE_PRIVATE, Context.MODE_PRIVATE);

        int prevHighScore = preferences.getInt("highScore", 0);

        if(score > prevHighScore) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.clear();
            edit.putInt("highScore", score);
            edit.apply();
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(context, "New High Score: " + score, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void updateGraphics(Canvas canvas, Context context) {
        gameGraphics.refresh(canvas, context);
    }

    private void spawnSpider() {
        if(spawnIn == 0) {
            doSpawn();
        } else {
            float currentTime = System.nanoTime() / 1000000f;
            float elapsedTime = currentTime - lastSpawnTime;

            if(elapsedTime >= spawnIn)
                doSpawn();
        }
    }

    private void doSpawn() {
        gameGraphics.spawnSpider();
        lastSpawnTime = System.nanoTime() / 1000000f;
        spawnIn = Utility.getRandomNumberInRange(500, 5000);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score = score + points;
    }

    public int getAvailableLives() {
        return  availableLives;
    }

    public void lostALife() {
        availableLives--;

        if(availableLives <= 0)
            isGameOver = true;
        else {
            gameAudio.playLifeLose();
        }
    }

    private void gameOver(Context context, Handler handler) {
        gameAudio.playGameOver();
        gameGraphics.clearAllSpiders();
        gameAudio.pauseAll();
        gameStopped = true;

        checkHighScore(context, handler);
    }

    public void reset() {
        gameEngine = null;
    }

    public void resetLives() {
        availableLives = MAXIMUM_LIVES;
    }

    public void resetScore() {
        score = 0;
    }

    public void spiderKilled() {
        gameAudio.playSquish();
        addScore(1);
    }

    public void spiderMissed() {
        gameAudio.playMissed();
    }

    public void handleTouch(int x, int y) {
        gameGraphics.hitSpider(x, y);
    }

    public void onPause() {
        gameAudio.pauseAll();
        paused = true;
    }

    public void onResume() {
        gameAudio.playAll();
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }
}
