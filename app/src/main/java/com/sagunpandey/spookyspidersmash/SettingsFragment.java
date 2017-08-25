package com.sagunpandey.spookyspidersmash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import static com.sagunpandey.spookyspidersmash.engine.GameEngine.PREFERENCE_PRIVATE;

/**
 * Created by sagun on 7/29/2017.
 */

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_fragment);

        SharedPreferences prefs = getActivity()
                .getSharedPreferences(PREFERENCE_PRIVATE, Context.MODE_PRIVATE);

        int highScore = prefs.getInt("highScore", 0);

        Preference highScorePref = findPreference("keyHighScore");
        highScorePref.setSummary(String.valueOf(highScore));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
