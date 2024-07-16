package com.r8.flashlight.service

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.util.Log
import javax.inject.Inject

class FlashlightController
    @Inject
    constructor(private val cameraManager: CameraManager) {
        private var isFlashOn = false
        private val cameraId: String = cameraManager.cameraIdList[0]
        val flashlightState: Boolean get() = isFlashOn

        fun toggleFlashlight() {
            Log.i(TAG, "toggleFlashlight")
            cameraId.let {
                try {
                    cameraManager.setTorchMode(it, !isFlashOn)
                    isFlashOn = !isFlashOn
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }
        }

        companion object {
            private const val TAG = "FlashlightController"
        }
    }
