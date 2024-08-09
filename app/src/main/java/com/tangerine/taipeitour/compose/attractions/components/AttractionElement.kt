package com.tangerine.taipeitour.compose.attractions.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tangerine.core.model.Attraction
import com.tangerine.taipeitour.compose.others.ImageDisplay
import com.tangerine.taipeitour.compose.others.myPadding
import com.tangerine.taipeitour.compose.others.noRippleClickable
import de.charlex.compose.material3.HtmlText
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoreAttractionElement(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onModifyBookmark: suspend (Int, Boolean) -> Boolean,
    onViewDetails: (Attraction) -> Unit,
    attraction: Attraction,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val imageSize = 110.dp
    val savingState = remember { mutableStateOf(attraction.isSaved) }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(imageSize),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onViewDetails(attraction) }
    ) {
        Row {
            ImageDisplay(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(imageSize),
                url = attraction.images.firstOrNull()?.src
            )

            Box {
                Column(modifier = Modifier.padding(horizontal = myPadding(), vertical = 5.dp)) {
                    HtmlText(
                        text = attraction.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    HtmlText(
                        text = attraction.introduction,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(com.tangerine.core.source.R.string.view_more),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                AnimatedContent(
                    targetState = savingState.value,
                    transitionSpec = { scaleIn() togetherWith fadeOut() }, label = "",
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .noRippleClickable {
                            val doSaving = !attraction.isSaved

                            scope.launch {
                                onModifyBookmark(attraction.id, doSaving).let {
                                    attraction.isSaved = doSaving
                                    savingState.value = doSaving //Save to recomposition
                                }
                            }
                        }
                ) { saved ->
                    if (!saved) Icon(
                        imageVector = Icons.Filled.AddCircleOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = myPadding(), vertical = 5.dp)
                    ) else Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF28A745),
                        modifier = Modifier.padding(horizontal = myPadding(), vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TrendAttractionElement(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onViewDetails: (Attraction) -> Unit,
    attraction: Attraction,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .height(200.dp)
                .width(250.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            onClick = { onViewDetails(attraction) }
        ) {
            Box {
                ImageDisplay(
                    url = attraction.images.firstOrNull()?.src,
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            rememberSharedContentState(key = "images/${attraction.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        )
                )

                HtmlText(
                    text = attraction.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.3f)
                        )
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(myPadding())
                )
            }
        }
    }
}
