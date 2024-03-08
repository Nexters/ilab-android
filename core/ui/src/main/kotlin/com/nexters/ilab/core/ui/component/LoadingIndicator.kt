package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.common.extension.noRippleClickable
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.core.ui.ComponentPreview

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.noRippleClickable {},
        contentAlignment = Alignment.Center,
    ) {
        LoadingImage(
            resId = R.drawable.anim_loading,
            contentDescription = "Loading image",
            modifier = Modifier.size(70.dp),
        )
    }
}

@ComponentPreview
@Composable
fun LoadingIndicatorPreview() {
    ILabTheme {
        LoadingIndicator(modifier = Modifier.fillMaxSize())
    }
}

