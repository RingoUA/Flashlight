package com.r8.flashlight.di

import android.content.Context
import android.content.SharedPreferences
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import com.r8.flashlight.service.FlashlightController
import com.r8.flashlight.service.VibratorController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCameraManager(
        @ApplicationContext context: Context,
    ): CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideFlashlightController(cameraManager: CameraManager): FlashlightController = FlashlightController(cameraManager)

    @Provides
    @Singleton
    fun provideVibrator(
        @ApplicationContext context: Context,
    ): Vibrator {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        }
        @Suppress("DEPRECATION")
        return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @Provides
    @Singleton
    fun provideVibratorController(vibrator: Vibrator): VibratorController = VibratorController(vibrator)
}
