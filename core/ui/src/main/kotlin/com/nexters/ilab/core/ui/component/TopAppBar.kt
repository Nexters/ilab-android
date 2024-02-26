package com.nexters.ilab.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.core.ui.ComponentPreview

@Composable
fun ILabTopAppBar(
    @StringRes titleRes: Int?,
    navigationType: TopAppBarNavigationType,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onNavigationClick: () -> Unit = {},
    isTextLogo: Boolean = false,
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        val icon: @Composable (Modifier, imageVector: ImageVector) -> Unit =
            { modifier, imageVector ->
                IconButton(
                    onClick = onNavigationClick,
                    modifier = modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .then(modifier),
        ) {
            if (navigationType == TopAppBarNavigationType.Back) {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                )
            }

            if (navigationType == TopAppBarNavigationType.Close) {
                icon(
                    Modifier.align(Alignment.CenterEnd),
                    ImageVector.vectorResource(id = R.drawable.ic_close),
                )
            }

            if (navigationType == TopAppBarNavigationType.Setting) {
                icon(
                    Modifier.align(Alignment.CenterEnd),
                    ImageVector.vectorResource(id = R.drawable.ic_setting),
                )
            }

            if (isTextLogo) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterStart),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_text_logo),
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                if (titleRes != null) {
                    Text(
                        text = stringResource(id = titleRes),
                        modifier = Modifier.align(Alignment.Center),
                        style = Subtitle1,
                    )
                }
            }
        }
    }
}

enum class TopAppBarNavigationType { Back, Close, Setting }

@ComponentPreview
@Composable
fun ILabTopAppBarBackPreview() {
    ILabTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "Navigation back icon",
    )
}

@ComponentPreview
@Composable
fun ILabTopAppBarClosePreview() {
    ILabTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.Close,
        navigationIconContentDescription = "Navigation close icon",
    )
}

@ComponentPreview
@Composable
fun ILabTopAppBarSettingPreview() {
    ILabTopAppBar(
        titleRes = android.R.string.untitled,
        navigationType = TopAppBarNavigationType.Setting,
        navigationIconContentDescription = "Navigation Setting icon",
        isTextLogo = true,
    )
}
