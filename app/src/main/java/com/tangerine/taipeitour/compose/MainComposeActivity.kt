package com.tangerine.taipeitour.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.tangerine.taipeitour.compose.attractions.AttractionScreen
import com.tangerine.taipeitour.compose.others.LocalSnackbarHostState
import com.tangerine.taipeitour.compose.splash.LandingScreen
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
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }

        CompositionLocalProvider(
            values = arrayOf(
                LocalSnackbarHostState provides snackbarHostState
            )
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { contentPadding ->
                var showLandingScreen by remember { mutableStateOf(true) }

                Box(modifier = Modifier.padding(contentPadding)) {
                    if (showLandingScreen) {
                        LandingScreen(onTimeout = { showLandingScreen = false })
                    } else {
                        NavHost(navController = navController)
                    }
                }
            }
        }
    }
}