package com.r8.flashlight.ui.activity

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.r8.flashlight.Constants
import com.r8.flashlight.service.FlashlightController
import com.r8.flashlight.service.FlashlightService

@Composable
fun MainScreen(
    preferences: SharedPreferences,
    flashlightController: FlashlightController,
) {
    var autoStartEnabled by remember {
        mutableStateOf(
            preferences.getBoolean(Constants.PREF_START_SERVICE_ON_BOOT, true),
        )
    }
    var flashlightState by remember { mutableStateOf(flashlightController.flashlightState) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(onClick = { /* TODO */ }) {
                Text(text = "Settings")
            }
            TextButton(onClick = { /* TODO */ }) {
                Text(text = "About")
            }
        }
        Spacer(modifier = Modifier)
        Button(
            onClick = {
                flashlightController.toggleFlashlight()
                flashlightState = flashlightController.flashlightState
            },
            colors =
                ButtonDefaults.buttonColors(
                    Color.Gray,
                ),
            modifier = Modifier.size(250.dp).padding(16.dp),
        ) {
            Text(
                text = if (flashlightState) "OFF" else "ON",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier)
        Row {
            Text(
                text = "Toggle on shake",
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(36.dp))
            val localContext = LocalContext.current
            Switch(
                checked = autoStartEnabled,
                onCheckedChange = {
                    autoStartEnabled = it
                    preferences.edit().putBoolean(Constants.PREF_START_SERVICE_ON_BOOT, it).apply()

                    if (autoStartEnabled) {
                        FlashlightService.startForeground(localContext)
                    } else {
                        FlashlightService.stopService(localContext)
                    }
                },
            )
        }
        Spacer(modifier = Modifier)
    }
}
