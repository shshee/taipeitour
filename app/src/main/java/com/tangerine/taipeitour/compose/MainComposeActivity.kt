package com.tangerine.taipeitour.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.tangerine.taipeitour.theme.AppTheme

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TaipeiTourApp()
        }
    }
}

@Composable
fun TaipeiTourApp() {
    AppTheme {
        var showLandingScreen by remember { mutableStateOf(true) }

        println("Update: $showLandingScreen")
        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            AttractionScreen()
        }
    }
}