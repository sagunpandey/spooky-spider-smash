package com.sagunpandey.spookyspidersmash.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sagun on 8/9/2017.
 */

public class SoundManager {

    private final int MAX_STREAMS = 10;

    private Context context;
    private AssetManager assets;
    private SoundPool soundPool;

    private Map<Integer, Sound> loadingSounds;

    public SoundManager(Context context) {
        this.context = context;
        this.assets = context.getAssets();

        loadingSounds = new HashMap<>();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                Sound sound = loadingSounds.get(sampleId);
                sound.setLoaded(true);
                if(loadingSounds.containsKey(sampleId))
                    loadingSounds.remove(sampleId);
            }
        });
    }

    public Sound newSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            Sound newSound = new Sound(soundPool, soundId);
            loadingSounds.put(soundId, newSound);
            return newSound;
        } catch (IOException e) {
            throw new RuntimeException("Error loading sound file: " + filename);
        }
    }

    public int newSound(int soundResource) {
        return soundPool.load(context, soundResource, 1);
    }

    public int play(int soundID) {
        return soundPool.play(soundID, 1, 1, 1, 0, 1);
    }

    public int play(int soundID, float volume) {
        return soundPool.play(soundID, volume, volume, 1, 0, 1);
    }

    public int play(int soundID, float leftVolume, float rightVolume, int loop, int priority, float rate) {
        return soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
    }

    public int loop(int soundID) {
        return soundPool.play(soundID, 1, 1, 0, -1, 1);
    }

    public int loop(int soundID, int times) {
        return soundPool.play(soundID, 1, 1, 0, times, 1);
    }

    public void stop(int streamID) {
        soundPool.stop(streamID);
    }

    public void pause(int streamID) {
        soundPool.pause(streamID);
    }

    public void resume(int streamID) {
        soundPool.resume(streamID);
    }

    public void pauseAll() {
        soundPool.autoPause();
    }

    public void resumeAll() {
        soundPool.autoResume();
    }

    public void setVolume(int streamID, float volume) {
        soundPool.setVolume(streamID, volume, volume);
    }

    public void setVolume(int streamID, float leftVolume, float rightVolume) {
        soundPool.setVolume(streamID, leftVolume, rightVolume);
    }

    public void setRate(int streamID, float rate) {
        soundPool.setRate(streamID, rate);
    }
}
