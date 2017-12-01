/*
 * Project:  NextGIS Mobile
 * Purpose:  Mobile GIS for Android.
 * Author:   Dmitry Baryshnikov (aka Bishop), bishop.dev@gmail.com
 * Author:   NikitaFeodonit, nfeodonit@yandex.com
 * Author:   Stanislav Petriakov, becomeglory@gmail.com
 * *****************************************************************************
 * Copyright (c) 2012-2017 NextGIS, info@nextgis.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.volsu.gis.activity;

import android.content.Intent;
import android.support.v7.preference.PreferenceScreen;
import com.volsu.maplibui.activity.NGIDSettingsActivity;
import com.volsu.maplibui.activity.NGPreferenceActivity;
import com.volsu.maplibui.activity.NGWSettingsActivity;
import com.volsu.maplibui.fragment.NGIDSettingsFragment;
import com.volsu.maplibui.fragment.NGPreferenceSettingsFragment;
import com.volsu.maplibui.fragment.NGPreferenceHeaderFragment;
import com.volsu.maplibui.fragment.NGWSettingsFragment;
import com.volsu.maplibui.util.SettingsConstantsUI;
import com.volsu.gis.R;
import com.volsu.gis.fragment.SettingsFragment;
import com.volsu.gis.fragment.SettingsHeaderFragment;
import com.volsu.gis.util.AppConstants;


/**
 * Application preference
 */
public class SettingsActivity
        extends NGPreferenceActivity
{
    @Override
    protected String getPreferenceHeaderFragmentTag()
    {
        return AppConstants.FRAGMENT_SETTINGS_HEADER_FRAGMENT;
    }


    @Override
    protected NGPreferenceHeaderFragment getNewPreferenceHeaderFragment()
    {
        return new SettingsHeaderFragment();
    }


    @Override
    protected String getPreferenceSettingsFragmentTag()
    {
        return AppConstants.FRAGMENT_SETTINGS_FRAGMENT;
    }


    @Override
    protected NGPreferenceSettingsFragment getNewPreferenceSettingsFragment(String subScreenKey)
    {
        NGPreferenceSettingsFragment fragment;
        switch (subScreenKey) {
            default:
                fragment = new SettingsFragment();
                break;
            case SettingsConstantsUI.ACTION_PREFS_NGW:
                fragment = new NGWSettingsFragment();
                break;
            case SettingsConstantsUI.ACTION_PREFS_NGID:
                fragment = new NGIDSettingsFragment();
                break;
        }
        return fragment;
    }


    @Override
    public String getTitleString()
    {
        return getString(R.string.action_settings);
    }


    @Override
    public void setTitle(PreferenceScreen preferenceScreen)
    {
        switch (preferenceScreen.getKey()) {
            default:
                super.setTitle(preferenceScreen);
                return;
            case SettingsConstantsUI.ACTION_PREFS_NGW:
            case SettingsConstantsUI.ACTION_PREFS_NGID:
                break;
        }
    }


    @Override
    protected void onStartSubScreen(PreferenceScreen preferenceScreen)
    {
        Intent intent;
        switch (preferenceScreen.getKey()) {
            default:
                super.onStartSubScreen(preferenceScreen);
                return;
            case SettingsConstantsUI.ACTION_PREFS_NGW:
                intent = new Intent(this, NGWSettingsActivity.class);
                break;
            case SettingsConstantsUI.ACTION_PREFS_NGID:
                intent = new Intent(this, NGIDSettingsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
