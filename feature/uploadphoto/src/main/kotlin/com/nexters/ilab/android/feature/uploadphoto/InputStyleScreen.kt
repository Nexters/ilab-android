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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.common.extension.noRippleClickable
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.core.domain.entity.StyleEntity
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoSideEffect
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoState
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoViewModel
import com.nexters.ilab.core.ui.ComponentPreview
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingIndicator
import com.nexters.ilab.core.ui.component.NetworkErrorDialog
import com.nexters.ilab.core.ui.component.ServerErrorDialog
import com.nexters.ilab.core.ui.component.StyleImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
internal fun InputStyleRoute(
    onBackClick: () -> Unit,
    onNavigateToCreateImage: (String, Int) -> Unit,
    viewModel: UploadPhotoViewModel,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is UploadPhotoSideEffect.CreateProfileImage -> {
                    onNavigateToCreateImage(
                        withContext(Dispatchers.IO) {
                            URLEncoder.encode(
                                sideEffect.imageUrl,
                                StandardCharsets.UTF_8.toString(),
                            )
                        },
                        sideEffect.styleId,
                    )
                }

                else -> {}
            }
        }
    }

    InputStyleScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onStyleSelect = viewModel::setSelectedStyle,
        createProfileImage = viewModel::createProfileImage,
        getStyleList = viewModel::getStyleList,
        dismissServerErrorDialog = viewModel::dismissServerErrorDialog,
        dismissNetworkErrorDialog = viewModel::dismissNetworkErrorDialog,
    )
}

@Composable
internal fun InputStyleScreen(
    uiState: UploadPhotoState,
    onBackClick: () -> Unit,
    onStyleSelect: (String) -> Unit,
    createProfileImage: () -> Unit,
    getStyleList: () -> Unit,
    dismissServerErrorDialog: () -> Unit,
    dismissNetworkErrorDialog: () -> Unit,
) {
    Column {
        if (uiState.isLoading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = {
                    dismissServerErrorDialog()
                    getStyleList()
                },
            )
        }
        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = {
                    dismissNetworkErrorDialog()
                    getStyleList()
                },
            )
        }
        InputStyleTopAppBar(onBackClick = onBackClick)
        InputStyleContent(
            styleList = uiState.styleList.toImmutableList(),
            selectedStyle = uiState.selectedStyle,
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
    styleList: ImmutableList<StyleEntity>,
    selectedStyle: String,
    isStyleSelected: Boolean,
    createProfileImage: () -> Unit,
    onStyleSelect: (String) -> Unit,
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            CheckableStyleImageList(
                styleList = styleList,
                selectedStyle = selectedStyle,
                onStyleSelect = onStyleSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 88.dp),
            )
        }
        ILabButton(
            onClick = createProfileImage,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 18.dp)
                .height(60.dp),
            enabled = isStyleSelected,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
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

@Composable
fun CheckableStyleImageList(
    styleList: ImmutableList<StyleEntity>,
    selectedStyle: String,
    onStyleSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItemIndex by remember {
        mutableStateOf<Int?>(
            styleList.indexOfFirst { it.name == selectedStyle },
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(34.dp),
        horizontalArrangement = Arrangement.spacedBy(26.dp),
    ) {
        item(
            span = {
                GridItemSpan(maxLineSpan)
            },
        ) {
            Column {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.what_style_prefer),
                    style = Title1,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.creates_image_based_on_selected_style),
                    style = Contents1,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Spacer(modifier = Modifier.height(26.dp))
            }
        }
        items(
            count = styleList.size,
            key = { index -> styleList[index].id },
        ) { index ->
            val backgroundColor = if (selectedItemIndex == index) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            } else {
                Color.Transparent
            }
            StyleImage(
                imageUrl = styleList[index].defaultImageUrl,
                styleName = "#${styleList[index].name}",
                backgroundColor = backgroundColor,
                contentDescription = "Style Image",
                isSelectedIndex = selectedItemIndex == index,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .noRippleClickable {
                        selectedItemIndex = if (selectedItemIndex == index) null else index
                        onStyleSelect(styleList[index].name)
                    },
            )
        }
        item(
            span = {
                GridItemSpan(maxLineSpan)
            },
        ) {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@DevicePreview
@Composable
fun InputStyleScreenPreview() {
    ILabTheme {
        InputStyleScreen(
            uiState = UploadPhotoState(),
            onBackClick = {},
            onStyleSelect = {},
            createProfileImage = {},
            getStyleList = {},
            dismissServerErrorDialog = {},
            dismissNetworkErrorDialog = {},
        )
    }
}

@ComponentPreview
@Composable
fun CheckableStyleImageListPreview() {
    ILabTheme {
        CheckableStyleImageList(
            styleList = persistentListOf(
                StyleEntity(
                    id = 0,
                    name = "ㅇㅇ",
                    defaultImageUrl = "",
                ),
            ),
            selectedStyle = "",
            onStyleSelect = {},
        )
    }
}
