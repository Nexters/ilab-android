package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nexters.ilab.android.core.common.extension.noRippleClickable
import com.nexters.ilab.core.ui.ComponentPreview

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.noRippleClickable {},
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(com.nexters.ilab.android.core.designsystem.R.raw.lottie_loading_indicator),
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 30,
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(100.dp),
            )
        }
    }
}

@ComponentPreview
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator(modifier = Modifier.fillMaxSize())
}
