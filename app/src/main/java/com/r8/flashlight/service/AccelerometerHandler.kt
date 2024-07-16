package com.r8.flashlight.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject
import kotlin.math.sqrt

@ServiceScoped
class AccelerometerHandler
    @Inject
    constructor(
        private val sensorManager: SensorManager,
        private val flashlightController: FlashlightController,
    ) {
        private val shakeThresholdGravity = 2.7f
        private val shakeSlopTimeMs = 500
        private val shakeCountResetTimeMs = 3000

        private var shakeTimestamp: Long = 0
        private var shakeCount: Int = 0

        private var accelerometer: Sensor? = null

        private val sensorEventListener =
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                            val (x, y, z) = it.values

                            val gX = x / SensorManager.GRAVITY_EARTH
                            val gY = y / SensorManager.GRAVITY_EARTH
                            val gZ = z / SensorManager.GRAVITY_EARTH

                            val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

                            if (gForce > shakeThresholdGravity) {
                                val now = System.currentTimeMillis()

                                if (shakeTimestamp + shakeSlopTimeMs > now) {
                                    return
                                }

                                if (shakeTimestamp + shakeCountResetTimeMs < now) {
                                    shakeCount = 0
                                }

                                shakeTimestamp = now
                                shakeCount++

                                if (shakeCount >= 2) {
                                    shakeCount = 0
                                    flashlightController.toggleFlashlight()
                                }
                            }
                        }
                    }
                }

                override fun onAccuracyChanged(
                    sensor: Sensor?,
                    accuracy: Int,
                ) { }
            }

        init {
            Log.i(TAG, "init")
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }

        fun register() {
            Log.i(TAG, "register")
            accelerometer?.also {
                sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }

        fun unregister() {
            Log.i(TAG, "unregister")
            sensorManager.unregisterListener(sensorEventListener)
        }

        companion object {
            private const val TAG = "AccelerometerHandler"
        }
    }
