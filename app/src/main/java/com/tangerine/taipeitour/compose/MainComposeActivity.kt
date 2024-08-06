package com.tangerine.taipeitour.compose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tangerine.taipeitour.compose.others.LocalSnackbarHostState
import com.tangerine.taipeitour.compose.splash.LandingScreen
import com.tangerine.taipeitour.theme.AppTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainComposeActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        setContent {
            KoinAndroidContext {
                TaipeiTourApp {
                    window.statusBarColor = it
                    window.navigationBarColor = it
                }
            }
        }
    }
}

@Composable
fun TaipeiTourApp(barsColor: (Int) -> Unit) {
    AppTheme {
        barsColor(MaterialTheme.colorScheme.onPrimary.toArgb())

        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        var showLandingScreen by rememberSaveable { mutableStateOf(true) }
        var selectingTab by rememberSaveable { mutableStateOf(bottomTabScreens.first().route) }

        val currentBackStack by navController.currentBackStackEntryAsState()

        val currentRoute = currentBackStack?.destination?.route
        val isBelongToMain =
            bottomTabScreens.firstOrNull { currentRoute == it.route } != null

        val scrollState = rememberLazyListState()
        val willHideBottomBar by remember {
            derivedStateOf { scrollState.firstVisibleItemIndex > 0 }
        }

        //Recheck if user navigated back to root screen
        if (isBelongToMain && selectingTab != currentRoute) selectingTab = currentRoute!!

        CompositionLocalProvider(
            values = arrayOf(
                LocalSnackbarHostState provides snackbarHostState
            )
        ) {
            Scaffold(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(), //Important to remove padding otherwise there will be space between parent and children scaffold
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    AnimatedVisibility(
                        visible = !showLandingScreen,//isBelongToMain && !willHideBottomBar,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        BottomBar(currentPage = selectingTab, onSwitchPage = {
                            selectingTab = it
                            navController.navigateSingleTopTo(it)
                        })
                    }
                }
            ) { contentPadding ->
                Box(modifier = Modifier.padding(contentPadding)) {
                    if (showLandingScreen) {
                        LandingScreen(onTimeout = { showLandingScreen = false })
                    } else {
                        NavHost(
                            navController = navController,
                            scrollState = scrollState,
                            isBottomBarHidden = willHideBottomBar
                        )
                    }
                }
            }
        }
    }
}