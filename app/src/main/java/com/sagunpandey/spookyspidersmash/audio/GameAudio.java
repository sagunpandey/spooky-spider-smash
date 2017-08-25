package com.sagunpandey.spookyspidersmash.audio;

import android.content.Context;

import com.sagunpandey.spookyspidersmash.helper.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagun on 8/12/2017.
 */

public class GameAudio {

    private List<Sound> squishSounds;

    private Sound missed;

    private Sound lifeLose;

    private Sound getReady;
    private Sound gameOver;

    private Music music;

    private boolean initialized = false;

    public void initialize(Context context) {
        if(!initialized) {
            SoundManager soundManager = new SoundManager(context);

            squishSounds = new ArrayList<>();
            Sound squash1 = soundManager.newSound("sound/squish_1.mp3");
            Sound squash2 = soundManager.newSound("sound/squish_2.mp3");
            Sound squash3 = soundManager.newSound("sound/squish_3.mp3");
            squishSounds.add(squash1);
            squishSounds.add(squash2);
            squishSounds.add(squash3);

            missed = soundManager.newSound("sound/missed.mp3");

            lifeLose = soundManager.newSound("sound/life_lose.mp3");

            getReady = soundManager.newSound("sound/get_ready.mp3");
            gameOver = soundManager.newSound("sound/game_over.mp3");

            MusicManager musicManager = new MusicManager(context);

            music = musicManager.newMusic("music/spooky.mp3");

            initialized = true;
        }
    }

    public boolean isLoaded() {
        boolean loaded;

        loaded = missed.isLoaded();
        loaded &= lifeLose.isLoaded();
        loaded &= getReady.isLoaded();
        loaded &= gameOver.isLoaded();
        for(Sound s: squishSounds) {
            loaded &= loaded & s.isLoaded();
        }

        return loaded;
    }

    public void playSquish() {
        int index = Utility.getRandomNumberInRange(0, squishSounds.size() - 1);
        squishSounds.get(index).play(1f);
    }

    public void playMissed() {
        missed.play(1f);
    }

    public void playLifeLose() {
        lifeLose.play(1f);
    }

    public void playGetReady() {
        getReady.play(1f);
    }

    public void playGameOver() {
        gameOver.play(1f);
    }

    public void playMusic() {
        music.play();
        music.setLooping(true);
    }

    public void pauseAll() {
        music.stop();
    }

    public void playAll() {
        playMusic();
    }
}
