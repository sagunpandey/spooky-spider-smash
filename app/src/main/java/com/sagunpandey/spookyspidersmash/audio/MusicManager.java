package com.sagunpandey.spookyspidersmash.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import java.io.IOException;

/**
 * Created by sagun on 8/9/2017.
 */

public class MusicManager {

    private AssetManager assets;

    public MusicManager(Context context) {
        this.assets = context.getAssets();
    }

    public Music newMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new Music(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Error loading music file: " + filename);
        }
    }
}
