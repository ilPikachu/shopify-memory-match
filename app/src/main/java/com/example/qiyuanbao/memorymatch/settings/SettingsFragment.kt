package com.example.qiyuanbao.memorymatch.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.qiyuanbao.memorymatch.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}