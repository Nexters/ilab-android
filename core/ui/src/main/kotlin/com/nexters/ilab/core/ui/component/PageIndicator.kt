package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Gray300
import com.nexters.ilab.core.ui.ComponentPreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerIndicator(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Blue600 else Gray300
            val modifier = if (pagerState.currentPage == iteration) Modifier.padding(4.dp)
                .clip(CircleShape)
                .width(18.dp)
                .background(color)
                .size(7.dp) else Modifier.padding(4.dp)
                .clip(CircleShape)
                .background(color)
                .size(7.dp)
            Box(
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ComponentPreview
@Composable
internal fun PagerIndicatorPreview() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    PagerIndicator(pagerState)
}
