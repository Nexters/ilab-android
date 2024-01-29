package com.nexters.ilab.android.feature.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue200
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue900
import com.nexters.ilab.android.core.designsystem.theme.SystemGreen
import com.nexters.ilab.android.core.designsystem.theme.SystemRed
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ExampleImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Suppress("unused")
@Composable
internal fun UploadRoute(
    onBackClick: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel(),
) {
    UploadScreen(
        onBackClick = onBackClick,
        onNavigateToUploadCheck = onNavigateToUploadCheck,
    )
}

@Composable
internal fun UploadScreen(
    onBackClick: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UploadTopAppBar(onBackClick = onBackClick)
        UploadContent(onNavigateToUploadCheck = onNavigateToUploadCheck)
    }
}

@Composable
private fun UploadTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = R.string.upload_top_title,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onBackClick,
    )
}

@Composable
private fun UploadContent(
    onNavigateToUploadCheck: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SystemGreen)) {
                    append(stringResource(id = R.string.good))
                }
                append(stringResource(id = R.string.good_example))
            },
        )
        Text(
            text = stringResource(id = R.string.good_example_first),
        )
        Text(
            text = stringResource(id = R.string.good_example_second),
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImageRow(images = goodExamples)
        Spacer(modifier = Modifier.height(42.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SystemRed)) {
                    append(stringResource(id = R.string.bad))
                }
                append(stringResource(id = R.string.bad_example))
            },
        )
        Text(
            text = stringResource(id = R.string.bad_example_first),
        )
        Text(
            text = stringResource(id = R.string.bad_example_second),
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImageRow(images = badExamples)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 4.dp, end = 4.dp, bottom = 18.dp),
        ) {
            ILabButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 4.dp),
                containerColor = PurpleBlue200,
                contentColor = PurpleBlue900,
                text = {
                    Text(
                        text = stringResource(id = R.string.photo_library),
                    )
                },
            )
            ILabButton(
                onClick = onNavigateToUploadCheck,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 4.dp),
                text = {
                    Text(
                        text = stringResource(id = R.string.take_photo),
                    )
                },
            )
        }
    }
}

val goodExamples = persistentListOf(
    Pair(R.drawable.ic_good_example1, "good example 1"),
    Pair(R.drawable.ic_good_example2, "good example 2"),
    Pair(R.drawable.ic_good_example3, "good example 3"),
)
val badExamples = persistentListOf(
    Pair(R.drawable.ic_bad_example1, "bad example 1"),
    Pair(R.drawable.ic_bad_example2, "bad example 2"),
    Pair(R.drawable.ic_bad_example3, "bad example 3"),
)

@Composable
fun ImageRow(images: ImmutableList<Pair<Int, String>>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        images.forEachIndexed { index, (resId, contentDescription) ->
            if (index > 0) Spacer(Modifier.width(12.dp))
            ExampleImage(
                resId = resId,
                contentDescription = contentDescription,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .weight(1f),
            )
        }
    }
}

@DevicePreview
@Composable
fun UploadScreenPreview() {
    UploadScreen(
        onBackClick = {},
        onNavigateToUploadCheck = {},
    )
}
