package com.nexters.ilab.android.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue500
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.core.ui.component.BackgroundImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.PagerIndicator
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Suppress("unused")
@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        padding = padding,
        onSettingClick = onSettingClick,
    )
}

@Composable
internal fun HomeScreen(
    uiState: HomeState,
    padding: PaddingValues,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopAppBar(onSettingClick)
        HomeContent(
            styleImageList = uiState.styleImageList,
            profileImageList = uiState.profileImageList,
            onGenerateImgBtnClick = {},
        )
    }
}

@Composable
internal fun HomeTopAppBar(onSettingClick: () -> Unit) {
    ILabTopAppBar(
        titleRes = R.string.normal,
        navigationType = TopAppBarNavigationType.Setting,
        navigationIconContentDescription = "home title bar",
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = 20.dp, end = 10.dp)
            .height(56.dp),
        isTextLogo = true,
        onNavigationClick = onSettingClick,
    )
}

@Composable
internal fun HomeContent(
    styleImageList: List<ProfileImage>,
    profileImageList: List<ProfileImage>,
    onGenerateImgBtnClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val imgSize = (configuration.screenWidthDp - 52)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(count = 2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
        verticalItemSpacing = 12.dp,
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            HomeKeywordView(
                styleImageList = styleImageList,
                onGenerateImgBtnClick = onGenerateImgBtnClick,
            )
        }

        itemsIndexed(profileImageList) { index, item ->
            val imageRatio = if (index % 6 == 1 || index % 6 == 3) imgSize.dp else (imgSize / 2 - 6).dp
            val startDp =  if (index % 6 == 0 || index % 6 == 2 || index % 6 == 3)  20.dp else 0.dp
            val endDp =  if (index % 6 == 0 || index % 6 == 2 || index % 6 == 3)  0.dp else 20.dp

            KeywordSampleImageItem(
                profileImage = item,
                imageRatio = imageRatio,
                startDp = startDp,
                endDp = endDp
            )
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeKeywordView(
    styleImageList: List<ProfileImage>,
    onGenerateImgBtnClick: () -> Unit,
) {
    val pageCount = styleImageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = R.drawable.bg_home_screen,
            contentDescription = "Background Image for Home Screen",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.home_style),
                color = Blue500,
                style = Subtitle2,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "#" + styleImageList[page].profileKeyword,
                        textAlign = TextAlign.Center,
                        style = Title1,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    NetworkImage(
                        imageUrl = styleImageList[page].profileImage,
                        contentDescription = "Style Image Example ${page + 1}",
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 999.dp, topEnd = 999.dp))
                            .size(width = 200.dp, height = 266.dp),
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
            Spacer(modifier = Modifier.height(32.dp))
            ILabButton(
                onClick = onGenerateImgBtnClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(48.dp),
                containerColor = Blue600,
                contentColor = Color.White,
                text = {
                    Text(
                        text = stringResource(id = R.string.home_generate_img_with_this_keyword),
                        style = Subtitle1,
                    )
                },
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.home_profile),
                style = Title2,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Composable
internal fun KeywordSampleImageItem(
    profileImage: ProfileImage,
    imageRatio: Dp,
    startDp: Dp,
    endDp: Dp
) {
    Box(
        modifier = Modifier
            .height(imageRatio)
            .padding(start = startDp, end = endDp)
            .clip(RoundedCornerShape(12.dp)),

        contentAlignment = Alignment.Center,
    ) {
        NetworkImage(
            imageUrl = profileImage.profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = "#" + profileImage.profileKeyword,
            style = Subtitle1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun previewHomeScreen() {
    HomeScreen(HomeState(), PaddingValues(0.dp), {})
}
