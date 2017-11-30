package ankushkumarkamboj.weatherworld;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

public class Fragment7 extends PreferenceFragment {
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    SharedPreferences sf;
    ListPreference lp0, lp1, lp2, lp3, lp4, lp5, lp6, lp7;
    CheckBoxPreference cb0, cb1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.my_prefrences);
    }

    @Override
    public void onStart() {
        super.onStart();
        sf = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                switch (key) {
                    case "temp":
                        lp0 = (ListPreference) findPreference("temp");
                        lp0.setSummary(sharedPreferences.getString("temp", ""));
                        break;
                    case "windSpeed":
                        lp1 = (ListPreference) findPreference("windSpeed");
                        lp1.setSummary(sharedPreferences.getString("windSpeed", ""));
                        break;
                    case "precipUnit":
                        lp2 = (ListPreference) findPreference("precipUnit");
                        lp2.setSummary(sharedPreferences.getString("precipUnit", ""));
                        break;
                    case "visibility":
                        lp3 = (ListPreference) findPreference("visibility");
                        lp3.setSummary(sharedPreferences.getString("visibility", ""));
                        break;
                    case "pressure":
                        lp4 = (ListPreference) findPreference("pressure");
                        lp4.setSummary(sharedPreferences.getString("pressure", ""));
                        break;
                    case "theme":
                        lp5 = (ListPreference) findPreference("theme");
                        lp5.setSummary(sharedPreferences.getString("theme", "Light"));
                    case "dateFormat":
                        lp6 = (ListPreference) findPreference("dateFormat");
                        lp6.setSummary(sharedPreferences.getString("dateFormat", ""));
                        break;
                    case "timeFormat":
                        cb0 = (CheckBoxPreference) findPreference("timeFormat");

                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        sf.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        sf.unregisterOnSharedPreferenceChangeListener(listener);
    }
}

