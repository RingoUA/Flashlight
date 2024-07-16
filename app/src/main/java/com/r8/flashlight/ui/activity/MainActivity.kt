package com.r8.flashlight.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.r8.flashlight.service.FlashlightController
import com.r8.flashlight.ui.theme.FlashlightTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var flashlightController: FlashlightController

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContent {
            FlashlightTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(preferences, flashlightController)
                }
            }
        }
    }

    @Inject
    fun setShardPreferences(preferences: SharedPreferences) {
        Log.i(TAG, "setShardPreferences: $preferences")
        this.preferences = preferences
    }

    @Inject
    fun setFlashlightController(flashlightController: FlashlightController) {
        Log.i(TAG, "setFlashlightController: $flashlightController")
        this.flashlightController = flashlightController
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
