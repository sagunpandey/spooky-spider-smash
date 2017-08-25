package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;

import com.sagunpandey.spookyspidersmash.engine.GameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sagun on 8/10/2017.
 */

public class GameGraphics {

    private GameEngine engine;

    private GameBoard gameBoard;

    private GameMenu gameMenu;
    private List<Spider> spiders;

    private Map<Spider, Float> spidersToClean;

    private boolean initialized = false;

    public GameGraphics(GameEngine engine) {
        this.engine = engine;

        gameBoard = new GameBoard(engine);
        gameMenu = new GameMenu(engine);

        spiders = new ArrayList<>();
        spidersToClean = new HashMap<>();
    }

    public void initialize(Context context, Canvas canvas) {
        if(!initialized) {
            gameBoard.initialize(context, canvas);
            gameMenu.initialize(context, canvas);

            initialized = true;
        }
    }

    public void refresh(Canvas canvas, Context context) {
        gameBoard.load(canvas, context);

        for(Spider spider: spiders) {
            spider.load(canvas, context);
        }

        gameMenu.load(canvas, context);
    }

    public void spawnSpider() {
        Spider spider = new Spider();
        spiders.add(spider);
    }

    public void checkIfSpiderReachedFood() {
        List<Spider> spidersToRemove = new ArrayList<>();
        for(Spider spider: spiders) {
            int foodMargin = gameBoard.getFoodMargin();
            Point point = spider.getCurrentPosition();
            boolean reached = point.y >= foodMargin;
            if(reached) {
                spider.reached();
                engine.lostALife();
                spidersToRemove.add(spider);
            }
        }

        for(Spider spider: spidersToRemove) {
            removeSpider(spider);
        }
    }

    public void checkForSpiderCleanup() {
        for(Spider spider: spidersToClean.keySet()) {
            float currentTime = System.nanoTime() / 1000000000f;
            float inactiveStartTime = spidersToClean.get(spider);
            float elapsedTime = currentTime - inactiveStartTime;

            int spiderCleanUpTime = 5;
            if(elapsedTime >= spiderCleanUpTime)
                removeSpider(spider);
        }
    }

    public void clearAllSpiders() {
        spidersToClean.clear();
        spiders.clear();
    }

    private void removeSpider(Spider spider) {
        spiders.remove(spider);
    }

    public void hitSpider(int x, int y) {
        for(Spider spider: spiders) {
            if(spider.wasHit(x, y) && !spider.isDead()) {
                spider.die();
                engine.spiderKilled();
                spidersToClean.put(spider, System.nanoTime() / 1000000000f);
            } else {
                engine.spiderMissed();
            }
        }
    }
}
