package com.r8.flashlight.di

import android.content.Context
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {
    @Provides
    fun provideSensorManager(
        @ApplicationContext context: Context,
    ): SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}
