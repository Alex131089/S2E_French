/*
 * Copyright (C) 2012 OlegKrikun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.krikun.s2e;

import android.os.Bundle;
import android.preference.Preference;
import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class Settings extends SherlockPreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        getSupportActionBar().setDisplayShowHomeEnabled(false);

        setOnPreferenceChange();
    }

    private void setMountState(boolean mounts_mode) {
        if (Helper.checkConfigDir()) {
            if (mounts_mode) Helper.createMountFile();
            else Helper.deleteMountFile();
        }
    }

    private void setOnPreferenceChange() {

        Preference mounts = findPreference("mounts_ext4");
        mounts.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object object) {
                boolean value = object.equals(true);
                setMountState(value);
                return true;
            }
        });

        Preference ReadAhead = findPreference("set_read_ahead");
        ReadAhead.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object object) {
                boolean value = object.equals(true);
                setReadAhead(value);
                return true;
            }
        });

        Preference ReadAheadValue = findPreference("read_ahead_values");
        ReadAheadValue.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object object) {
                String value = object.toString();
                setReadAheadValue(value);
                return true;
            }
        });
    }

    private void setReadAhead(boolean ReadAhead) {
        if (Helper.checkConfigDir()) {
            if (ReadAhead) {
                Helper.createReadAheadFile();
                String value = App.getPrefs().getString("read_ahead_values", null);
                if (value != null) Helper.writeReadAheadValue(value);
            } else Helper.deleteReadAheadFile();
        }
    }

    private void setReadAheadValue(String read_ahead_value) {
        if (Helper.checkConfigDir()) Helper.writeReadAheadValue(read_ahead_value);
    }
}
