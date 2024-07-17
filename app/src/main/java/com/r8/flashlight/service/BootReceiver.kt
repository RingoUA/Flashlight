package com.r8.flashlight.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.r8.flashlight.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    private lateinit var preferences: SharedPreferences

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        Log.i(TAG, "onReceive")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            if (isShouldStartOnBoot()) {
                FlashlightService.startForeground(context)
            }
        }
    }

    @Inject
    fun setSharedPreferences(preferences: SharedPreferences) {
        Log.i(TAG, "setSharedPreferences: $preferences")
        this.preferences = preferences
    }

    private fun isShouldStartOnBoot(): Boolean =
        preferences.getBoolean(
            Constants.PREF_START_SERVICE_ON_BOOT,
            false,
        )

    companion object {
        private const val TAG = "BootReceiver"
    }
}
