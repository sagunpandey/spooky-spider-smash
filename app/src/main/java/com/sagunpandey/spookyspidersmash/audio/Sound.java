package com.sagunpandey.spookyspidersmash.audio;

import android.media.SoundPool;

public class Sound {

    private int soundId;
    private SoundPool soundPool;
    private int playId;

    private boolean loaded = false;

    public Sound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        playId = soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void playlooping(float volume) {
        playId = soundPool.play(soundId, volume, volume, 0, -1, 1);
    }

    public void stop() {
        soundPool.stop(playId);
    }

    public void free() {
        soundPool.unload(soundId);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
