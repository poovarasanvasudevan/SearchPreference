package com.bytehamster.lib.preferencesearch;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;


public class SearchPreferenceResult {
    private final String key;
    private final int file;
    private final String screen;

    SearchPreferenceResult(String key, int file, String screen) {
        this.key = key;
        this.file = file;
        this.screen = screen;
    }

    /**
     * Returns the key of the preference pressed
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the file in which the result was found
     * @return The file in which the result was found
     */
    public int getResourceFile() {
        return file;
    }

    /**
     * Returns the screen in which the result was found
     * @return The screen in which the result was found
     */
    public String getScreen() {
        return screen;
    }

    /**
     * Highlight the preference that was found
     * @param prefsFragment Fragment that contains the preference
     */
    public void highlight(final PreferenceFragmentCompat prefsFragment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                doHighlight(prefsFragment);
            }
        });

    }

    private void doHighlight(PreferenceFragmentCompat prefsFragment) {
        final Preference prefResult = prefsFragment.findPreference(getKey());

        if (prefResult == null) {
            Log.e("doHighlight", "Preference not found on given screen");
            return;
        }

        prefsFragment.scrollToPreference(prefResult);
        final Drawable oldIcon = prefResult.getIcon();
        final boolean oldSpaceReserved = prefResult.isIconSpaceReserved();

        prefResult.setIcon(R.drawable.searchpreference_ic_arrow_right);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prefResult.setIcon(oldIcon);
                prefResult.setIconSpaceReserved(oldSpaceReserved);
            }
        }, 1000);
    }

    /**
     * Closes the search results page
     * @param activity The current activity
     */
    public void closeSearchPage(AppCompatActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }
}
