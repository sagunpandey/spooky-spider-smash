package com.sagunpandey.spookyspidersmash.audio;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

/**
 * Created by sagun on 8/9/2017.
 */

public class Music implements OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;

    public Music(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            isPrepared = true;
        } catch (Exception e) {
            throw new RuntimeException("Error loading music");
        }
    }

    public void free() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public boolean isStopped() {
        return (!isPrepared);
    }

    public void play() {
        if (!mediaPlayer.isPlaying()) {
            try {
                synchronized (this) {
                    if (!isPrepared) {
                        mediaPlayer.prepare();
                        isPrepared = true;
                    }
                    mediaPlayer.start();
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        if (volume < 0)
            volume = 0;
        else if (volume > 1)
            volume = 1;
        mediaPlayer.setVolume(volume, volume);
    }

    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }
}
