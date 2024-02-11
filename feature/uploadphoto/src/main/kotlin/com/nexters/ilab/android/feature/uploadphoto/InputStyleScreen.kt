package com.nexters.ilab.android.feature.uploadphoto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.common.extension.noRippleClickable
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray500
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoState
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoViewModel
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.StyleImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun InputStyleRoute(
    onBackClick: () -> Unit,
    onNavigateToCreateImage: () -> Unit,
    viewModel: UploadPhotoViewModel,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    InputStyleScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onStyleSelect = viewModel::setSelectedStyle,
        createProfileImage = onNavigateToCreateImage,
    )
}

@Composable
internal fun InputStyleScreen(
    uiState: UploadPhotoState,
    onBackClick: () -> Unit,
    onStyleSelect: (String) -> Unit,
    createProfileImage: () -> Unit,
) {
    Column {
        InputStyleTopAppBar(onBackClick = onBackClick)
        InputStyleContent(
            isStyleSelected = uiState.selectedStyle.isNotEmpty(),
            onStyleSelect = onStyleSelect,
            createProfileImage = createProfileImage,
        )
    }
}

@Composable
internal fun InputStyleTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = R.string.input_style_top_title,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onBackClick,
    )
}

@Composable
internal fun InputStyleContent(
    isStyleSelected: Boolean,
    createProfileImage: () -> Unit,
    onStyleSelect: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.what_style_prefer),
            style = Title1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.creates_image_based_on_selected_style),
            style = Contents1,
            color = Gray500,
        )
        Spacer(modifier = Modifier.height(60.dp))
        CheckableStyleImage(
            images = styleImages,
            onStyleSelect = onStyleSelect,
        )
        Spacer(modifier = Modifier.weight(1f))
        ILabButton(
            onClick = createProfileImage,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 18.dp)
                .height(60.dp),
            enabled = isStyleSelected,
            containerColor = Blue600,
            contentColor = Color.White,
            text = {
                Text(
                    text = stringResource(id = R.string.create_ai_profile_image),
                    style = Subtitle1,
                )
            },
        )
    }
}

val styleImages = persistentListOf(
    Pair(R.drawable.img_style_dreamlike, "#몽환적인"),
    Pair(R.drawable.img_style_lonely, "#고독한"),
    Pair(R.drawable.img_style_natural, "#자연적인"),
    Pair(R.drawable.img_style_sketch, "#스케치"),
)

@Composable
fun CheckableStyleImage(
    images: ImmutableList<Pair<Int, String>>,
    onStyleSelect: (String) -> Unit,
) {
    var selectedItemIndex by remember { mutableStateOf<Int?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            count = images.size,
            key = { index -> images[index].second },
        ) { index ->
            val backgroundColor = if (selectedItemIndex == index) {
                Blue600.copy(alpha = 0.6f)
            } else {
                Color.Transparent
            }
            Box {
                StyleImage(
                    resId = images[index].first,
                    style = images[index].second,
                    backgroundColor = backgroundColor,
                    contentDescription = "Style Image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .noRippleClickable {
                            selectedItemIndex = if (selectedItemIndex == index) null else index
                            onStyleSelect(images[index].second)
                        },
                )
            }
        }
    }
}

@DevicePreview
@Composable
fun InputStyleScreenPreview() {
    InputStyleScreen(
        uiState = UploadPhotoState(),
        onBackClick = {},
        onStyleSelect = {},
        createProfileImage = {},
    )
}
