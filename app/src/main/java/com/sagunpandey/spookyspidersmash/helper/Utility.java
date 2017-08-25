package com.sagunpandey.spookyspidersmash.helper;

import java.util.Random;

/**
 * Created by sagun on 8/11/2017.
 */

public class Utility {

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
