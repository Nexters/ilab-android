package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Gray300
import com.nexters.ilab.core.ui.ComponentPreview
import kotlin.math.absoluteValue

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    selectedIndicatorColor: Color = Blue600,
    unselectedIndicatorColor: Color = Gray300,
    indicatorSize: Dp = 6.dp,
    selectedIndicatorWidth: Dp = 18.dp,
    indicatorPadding: Dp = 4.dp,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { page ->
            val color =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset = ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)
                    selectedIndicatorColor.copy(alpha = offsetPercentage)
                } else {
                    unselectedIndicatorColor
                }

            Box(
                modifier = if (currentPage == page) {
                    Modifier
                        .padding(indicatorPadding)
                        .width(selectedIndicatorWidth)
                        .height(indicatorSize)
                        .clip(CircleShape)
                        .background(color)
                } else {
                    Modifier
                        .padding(indicatorPadding)
                        .size(indicatorSize)
                        .clip(CircleShape)
                        .background(color)
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ComponentPreview
@Composable
internal fun PagerIndicatorPreview() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    PagerIndicator(
        pageCount = pagerState.pageCount,
        currentPage = pagerState.currentPage,
        targetPage = pagerState.currentPage,
        currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
    )
}
