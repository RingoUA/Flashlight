package com.r8.flashlight.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.r8.flashlight.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FlashlightService : LifecycleService() {
    private lateinit var sensorManager: SensorManager
    private lateinit var cameraManager: CameraManager
    private lateinit var vibratorController: VibratorController

    private lateinit var proximityHandler: ProximityHandler

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()

        cameraManager.registerTorchCallback(vibratorController, null)

        startForegroundService()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        Log.i(TAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        proximityHandler.unregister()
        cameraManager.unregisterTorchCallback(vibratorController)
        super.onDestroy()
    }

    @Inject
    fun setSensorManager(sensorManager: SensorManager) {
        Log.i(TAG, "setSensorManager: $sensorManager")
        this.sensorManager = sensorManager
    }

    @Inject
    fun setCameraManager(cameraManager: CameraManager) {
        Log.i(TAG, "setCameraManager: $cameraManager")
        this.cameraManager = cameraManager
    }

    @Inject
    fun setVibratorController(vibratorController: VibratorController) {
        Log.i(TAG, "setVibratorController: $vibratorController")
        this.vibratorController = vibratorController
    }

    @Inject
    fun setProximityHandler(proximityHandler: ProximityHandler) {
        Log.i(TAG, "setProximityHandler: $proximityHandler")
        this.proximityHandler = proximityHandler
        this.proximityHandler.register()
    }

    private fun createNotification(): Notification {
        val channelId = "my_service_channel"

        val channel =
            NotificationChannel(
                channelId,
                "Flashlight notification",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Flashlight")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun startForegroundService() {
        val notification = createNotification()
        startForeground(1, notification)
    }

    companion object {
        private const val TAG = "FlashlightService"

        fun startForeground(context: Context?) {
            val serviceIntent = Intent(context, FlashlightService::class.java)
            context?.startForegroundService(serviceIntent)
        }

        fun stopService(context: Context?) {
            val serviceIntent = Intent(context, FlashlightService::class.java)
            context?.stopService(serviceIntent)
        }
    }
}