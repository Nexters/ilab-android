package com.nexters.ilab.feature.createimage

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue200
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue900
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.BackgroundImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingIndicator
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.PagerIndicator
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageState
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageViewModel
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageSideEffect
import kotlinx.collections.immutable.ImmutableList
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

@Composable
internal fun CreateImageCompleteRoute(
    onCloseClick: () -> Unit,
    viewModel: CreateImageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val systemUiController = rememberExSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true,
            isNavigationBarContrastEnforced = false,
        )

        onDispose {}
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        viewModel.deleteCacheDir()
    }

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is CreateImageSideEffect.ShareCreatedImage -> {
                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND_MULTIPLE
                        type = "image/png"
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(sideEffect.imageUriList.map { Uri.parse(it) }))
                    }
                    launcher.launch(Intent.createChooser(shareIntent, null))
                }

                is CreateImageSideEffect.SaveCreatedImageSuccess -> {
                    Toast.makeText(context, context.getString(R.string.create_image_save_complete), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    CreateImageCompleteScreen(
        uiState = uiState,
        onCloseClick = onCloseClick,
        shareCreatedImage = viewModel::shareCreatedImage,
        saveCreatedImage = viewModel::saveCreatedImage,
    )
}

@Composable
private fun CreateImageCompleteScreen(
    uiState: CreateImageState,
    onCloseClick: () -> Unit,
    shareCreatedImage: () -> Unit,
    saveCreatedImage: () -> Unit,
) {
    BackHandler {}

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = R.drawable.bg_create_image_complete_screen,
            contentDescription = "Background Image for Create Image Complete Screen",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )

        Column {
            CreateImageCompleteTopAppBar(onBackClick = onCloseClick)
            CreateImageCompleteContent(
                createdImageList = uiState.createdImageList,
                shareCreatedImage = shareCreatedImage,
                saveCreatedImage = saveCreatedImage,
            )
        }

        if (uiState.isLoading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun CreateImageCompleteTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = null,
        navigationType = TopAppBarNavigationType.Close,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        containerColor = Color.Transparent,
        onNavigationClick = onBackClick,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CreateImageCompleteContent(
    createdImageList: ImmutableList<String>,
    shareCreatedImage: () -> Unit,
    saveCreatedImage: () -> Unit,
) {
    val pageCount = createdImageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.create_image_complete),
            style = Title1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalPager(state = pagerState) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                NetworkImage(
                    imageUrl = createdImageList[page],
                    contentDescription = "Created Image Example ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        PagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.currentPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 18.dp),
        ) {
            ILabButton(
                onClick = shareCreatedImage,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 4.dp),
                containerColor = PurpleBlue200,
                contentColor = PurpleBlue900,
                text = {
                    Text(
                        text = stringResource(id = R.string.create_image_share),
                        style = Subtitle1,
                    )
                },
            )
            ILabButton(
                onClick = saveCreatedImage,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 4.dp),
                text = {
                    Text(
                        text = stringResource(id = R.string.create_image_save),
                        style = Subtitle1,
                    )
                },
            )
        }
    }
}

@DevicePreview
@Composable
fun CreateImageCompleteScreenPreview() {
    CreateImageCompleteScreen(
        uiState = CreateImageState(),
        onCloseClick = {},
        shareCreatedImage = {},
        saveCreatedImage = {},
    )
}
