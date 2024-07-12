package com.tangerine.taipeitour.compose.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tangerine.taipeitour.R
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    val animVisibleState = remember { MutableTransitionState(false) }
    var completedAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1)
        animVisibleState.targetState = true

        delay(1000)
        animVisibleState.targetState = false
        completedAnimation = true
    }

    if (!animVisibleState.targetState &&
        !animVisibleState.currentState && completedAnimation
    ) {
        LaunchedEffect(Unit) {
            currentOnTimeout.invoke()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visibleState = animVisibleState,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Image(painterResource(id = R.drawable.taipei), contentDescription = null)
        }
    }
}
