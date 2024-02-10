package com.nexters.ilab.android.feature.mypage

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.PagerIndicator
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

@Composable
internal fun MyAlbumImageRoute(
    onCloseClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val myPageState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val systemUiController = rememberExSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true,
            isNavigationBarContrastEnforced = false,
        )
        onDispose {}
    }

    MyAlbumImageScreen(
        uiState = myPageState,
        onCloseClick = onCloseClick,
        onShareBtnClick = {},
        onSaveBtnClick = {},
    )
}

@Composable
private fun MyAlbumImageScreen(
    uiState: MyPageState,
    onCloseClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onSaveBtnClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = R.drawable.bg_create_image_complete_screen,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        Column {
            MyAlbumImageTopAppBar(onBackClick = onCloseClick)
            MyAlbumImageContent(
                myAlbumImageList = uiState.myAlbumImageList,
                onShareBtnClick = onShareBtnClick,
                onSaveBtnClick = onSaveBtnClick,
            )
        }
    }
}

@Composable
private fun MyAlbumImageTopAppBar(
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
private fun MyAlbumImageContent(
    myAlbumImageList: List<Triple<String, String, String>>,
    onShareBtnClick: () -> Unit,
    onSaveBtnClick: () -> Unit,
) {
    val pageCount = myAlbumImageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "#" + myAlbumImageList[0].third,
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
                    imageUrl = myAlbumImageList[page].first,
                    contentDescription = myAlbumImageList[page].second,
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
                .padding(start = 4.dp, end = 4.dp, bottom = 18.dp),
        ) {
            ILabButton(
                onClick = onShareBtnClick,
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
                onClick = onSaveBtnClick,
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
fun MyAlbumImageScreenPreview() {
    MyAlbumImageScreen(
        uiState = MyPageState(
            selectedPhotoUri = "",
        ),
        onCloseClick = {},
        onShareBtnClick = {},
        onSaveBtnClick = {},
    )
}
