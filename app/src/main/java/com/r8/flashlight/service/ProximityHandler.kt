package com.r8.flashlight.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject

@ServiceScoped
class ProximityHandler
    @Inject
    constructor(
        private val sensorManager: SensorManager,
        private val accelerometerHandler: AccelerometerHandler,
    ) {
        private var isRegistered = false
        private var proximitySensor: Sensor? = null

        private val sensorEventListener =
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                            val isNear = event.values[0] < event.sensor.maximumRange
                            if (!isNear) {
                                accelerometerHandler.register()
                            } else {
                                accelerometerHandler.unregister()
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
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        }

        fun register() {
            if (!isRegistered) {
                Log.i(TAG, "register")
                proximitySensor?.also {
                    sensorManager.registerListener(
                        sensorEventListener,
                        proximitySensor,
                        SensorManager.SENSOR_DELAY_NORMAL,
                    )
                    isRegistered = true
                }
            }
        }

        fun unregister() {
            if (isRegistered) {
                Log.i(TAG, "unregister")
                accelerometerHandler.unregister()
                sensorManager.unregisterListener(sensorEventListener)
                isRegistered = false
            }
        }

        companion object {
            private const val TAG = "ProximityHandler"
        }
    }
