package com.gulum.expresseasy;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Administrator on 2016/2/8.
 */
public class SettingFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }
}
