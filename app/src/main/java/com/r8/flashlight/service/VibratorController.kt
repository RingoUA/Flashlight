package com.r8.flashlight.service

import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class VibratorController
    @Inject
    constructor(
        private val vibrator: Vibrator,
    ) : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(
            cameraId: String,
            enabled: Boolean,
        ) {
            super.onTorchModeChanged(cameraId, enabled)
            val duration: Long = 200
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
                return
            }
            vibrator.vibrate(duration)
        }
    }
