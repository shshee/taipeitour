package com.tangerine.taipeitour.compose

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tangerine.taipeitour.compose.others.createShape
import kotlinx.coroutines.launch

@Composable
fun BottomBar(currentPage: String, onSwitchPage: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(
            topEnd = 10.dp,
            topStart = 10.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        modifier = Modifier
            .border(
                BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                createShape(10.dp)
            )

    ) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                bottomTabScreens.forEach {
                    BottomButton(
                        currentPage = currentPage,
                        item = it,
                        onSwitchPage = onSwitchPage,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomButton(
    currentPage: String,
    item: MainScreen,
    onSwitchPage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val animVisibleState = remember { MutableTransitionState(false) }
    val scope = rememberCoroutineScope()

    scope.launch {
        animVisibleState.targetState = currentPage == item.route
    }

    IconButton(
        onClick = { onSwitchPage(item.route) },
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (currentPage == item.route) item.iconEnabled else item.iconDisabled,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            androidx.compose.animation.AnimatedVisibility(
                visibleState = animVisibleState,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(animationSpec = tween())
            ) {
                Text(
                    text = stringResource(id = item.title),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}