package edu.byuh.cis.cs203.slide203ai.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import edu.byuh.cis.cs203.slide203ai.R;

public class Prefs extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        PreferenceScreen screen = getPreferenceManager()
                .createPreferenceScreen(this);

        CheckBoxPreference sound = new CheckBoxPreference(this);
        sound.setTitle(R.string.bg_music);
        sound.setSummaryOn(R.string.description_music_on);
        sound.setSummaryOff(R.string.description_music_off);
        sound.setChecked(true);
        sound.setKey("SOUNDFX");
        screen.addPreference(sound);

        CheckBoxPreference soundfx = new CheckBoxPreference(this);
        soundfx.setTitle(R.string.bg_sd_fx);
        soundfx.setSummaryOn(R.string.sf_on);
        soundfx.setSummaryOff(R.string.sf_off);
        soundfx.setChecked(true);
        soundfx.setKey("SOUNDFX");
        screen.addPreference(soundfx);

        ListPreference speed = new ListPreference(this);
        speed.setTitle(R.string.speed);
        speed.setSummary(R.string.speed_description);
        speed.setKey("SPEED");
        String[] entries = {"Fast", "Medium", "Slow"};
        String[] values = {"10", "50", "100"};
        speed.setEntries(entries);
        speed.setEntryValues(values);
        screen.addPreference(speed);

        ListPreference theme = new ListPreference(this);
        theme.setTitle(R.string.theme);
        theme.setSummary(R.string.theme_description);
        theme.setKey("THEME");
        String[] ent = {"USA", "FRANCE"};
        String[] valTheme = {"USA", "FRANCE"};
        theme.setEntries(ent);
        theme.setEntryValues(valTheme);
        screen.addPreference(theme);

        setPreferenceScreen(screen);
    }


    /**
     *
     * @param c
     * @return
     */
    public static boolean getSoundPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("SOUNDFX", true);
    }

    public static int getSpeed(Context c) {
        String oaks = PreferenceManager.getDefaultSharedPreferences(c).getString("SPEED", "100");
        return Integer.parseInt(oaks);
    }

    public static String getTheme(Context c) {
        String whatTheme = PreferenceManager.getDefaultSharedPreferences(c).getString("THEME", "USA");
        return whatTheme;
    }

}
