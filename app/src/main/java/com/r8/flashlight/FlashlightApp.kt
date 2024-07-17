package com.r8.flashlight

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.r8.flashlight.service.FlashlightService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FlashlightApp : Application() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()

        if (isShouldStartOnBoot()) {
            FlashlightService.startForeground(this)
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
        private const val TAG = "FlashlightApp"
    }
}
