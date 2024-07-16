package com.r8.flashlight.service

import android.hardware.camera2.CameraManager
import android.os.Vibrator
import javax.inject.Inject

class VibratorController @Inject constructor(
    private val vibrator: Vibrator
) : CameraManager.TorchCallback() {

    override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
        super.onTorchModeChanged(cameraId, enabled)
        vibrator.vibrate(200)
    }
}
