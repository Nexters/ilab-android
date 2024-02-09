package com.nexters.ilab.android.feature.uploadphoto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray500
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.KeywordImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun InputKeywordRoute(
    onBackClick: () -> Unit,
    onNavigateToCreateImage: () -> Unit,
    viewModel: UploadPhotoViewModel,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    InputKeywordScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onKeywordSelect = viewModel::setSelectedKeyword,
        createProfileImage = onNavigateToCreateImage,
    )
}

@Composable
internal fun InputKeywordScreen(
    uiState: UploadPhotoState,
    onBackClick: () -> Unit,
    onKeywordSelect: (String) -> Unit,
    createProfileImage: () -> Unit,
) {
    Column {
        InputKeywordTopAppBar(onBackClick = onBackClick)
        InputKeywordContent(
            isKeywordSelected = uiState.selectedKeyword.isNotEmpty(),
            onKeywordSelect = onKeywordSelect,
            createProfileImage = createProfileImage,
        )
    }
}

@Composable
internal fun InputKeywordTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = R.string.input_keyword_top_title,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onBackClick,
    )
}

@Composable
internal fun InputKeywordContent(
    isKeywordSelected: Boolean,
    createProfileImage: () -> Unit,
    onKeywordSelect: (String) -> Unit,
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
        CheckableKeywordImage(
            images = keywordImages,
            onKeywordSelect = onKeywordSelect,
        )
        Spacer(modifier = Modifier.weight(1f))
        ILabButton(
            onClick = createProfileImage,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 18.dp)
                .height(60.dp),
            enabled = isKeywordSelected,
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

val keywordImages = persistentListOf(
    Pair(R.drawable.img_keyword_dreamy, "dreamy image"),
    Pair(R.drawable.img_keyword_lonely, "lonely image"),
    Pair(R.drawable.img_keyword_natural, "natural image"),
    Pair(R.drawable.img_keyword_sketch, "sketch image"),
)

@Composable
fun CheckableKeywordImage(
    images: ImmutableList<Pair<Int, String>>,
    onKeywordSelect: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(count = images.size) { index ->
            KeywordImage(
                resId = images[index].first,
                contentDescription = images[index].second,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable {
                        onKeywordSelect(images[index].second)
                    },
            )
        }
    }
}

@DevicePreview
@Composable
fun InputKeywordScreenPreview() {
    InputKeywordScreen(
        uiState = UploadPhotoState(),
        onBackClick = {},
        onKeywordSelect = {},
        createProfileImage = {},
    )
}
