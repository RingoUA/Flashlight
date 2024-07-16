package com.r8.flashlight.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        Log.i(TAG, "onReceive")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            FlashlightService.startForeground(context)
        }
    }

    companion object {
        private const val TAG = "BootReceiver"
    }
}
