package com.nexters.ilab.android.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.android.core.domain.entity.ProfileEntity
import com.nexters.ilab.android.core.domain.entity.StyleEntity
import com.nexters.ilab.android.feature.home.viewmodel.HomeSideEffect
import com.nexters.ilab.android.feature.home.viewmodel.HomeState
import com.nexters.ilab.android.feature.home.viewmodel.HomeViewModel
import com.nexters.ilab.core.ui.ComponentPreview
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.BackgroundImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingIndicator
import com.nexters.ilab.core.ui.component.NetworkErrorDialog
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.PagerIndicator
import com.nexters.ilab.core.ui.component.ServerErrorDialog
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onNavigateToUploadPhoto: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.OnCreateImageBtnClickFromStyle -> {
                    onNavigateToUploadPhoto(sideEffect.selectedStyle)
                }

                is HomeSideEffect.OnCreateImageBtnClickFromProfile -> {
                    onNavigateToUploadPhoto(sideEffect.selectedProfile)
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        padding = padding,
        onSettingClick = onSettingClick,
        onCreateImageBtnClickFromStyle = viewModel::onCreateImageBtnClickFromStyle,
        onCreateImageBtnClickFromProfile = viewModel::onCreateImageBtnClickFromProfile,
        openProfileImageDialog = viewModel::openProfileImageDialog,
        dismissProfileImageDialog = viewModel::dismissProfileImageDialog,
        getStyleProfileList = viewModel::getStyleProfileList,
        dismissNetworkErrorDialog = viewModel::dismissNetworkErrorDialog,
        dismissServerErrorDialog = viewModel::dismissServerErrorDialog,
        setSelectedStyleImage = viewModel::setSelectedStyleImage,
    )
}

@Composable
internal fun HomeScreen(
    uiState: HomeState,
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onCreateImageBtnClickFromStyle: () -> Unit,
    onCreateImageBtnClickFromProfile: () -> Unit,
    openProfileImageDialog: (Int) -> Unit,
    dismissProfileImageDialog: () -> Unit,
    getStyleProfileList: () -> Unit,
    dismissNetworkErrorDialog: () -> Unit,
    dismissServerErrorDialog: () -> Unit,
    setSelectedStyleImage: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isLoading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = {
                    dismissServerErrorDialog()
                    getStyleProfileList()
                },
            )
        }
        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = {
                    dismissNetworkErrorDialog()
                    getStyleProfileList()
                },
            )
        }
        if (uiState.isProfileImageDialogVisible) {
            ProfileImageDialog(
                profileImage = uiState.selectedProfileImage,
                onCloseClick = dismissProfileImageDialog,
                onCreateImageBtnClickFromProfile = {
                    dismissProfileImageDialog()
                    onCreateImageBtnClickFromProfile()
                },
            )
        }
        HomeTopAppBar(onSettingClick = onSettingClick)
        HomeContent(
            styleImageList = uiState.styleImageList,
            profileImageList = uiState.profileImageList,
            onCreateImageBtnClickFromStyle = onCreateImageBtnClickFromStyle,
            openProfileImageDialog = openProfileImageDialog,
            setSelectedStyleImage = setSelectedStyleImage,
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
    styleImageList: List<StyleEntity>,
    profileImageList: List<ProfileEntity>,
    onCreateImageBtnClickFromStyle: () -> Unit,
    openProfileImageDialog: (Int) -> Unit,
    setSelectedStyleImage: (Int) -> Unit,
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
                onCreateImageBtnClickFromStyle = onCreateImageBtnClickFromStyle,
                setSelectedStyleImage = setSelectedStyleImage,
            )
        }

        itemsIndexed(profileImageList) { index, item ->
            val imageRatio = if (index % 6 == 1 || index % 6 == 3) imgSize.dp else (imgSize / 2 - 6).dp
            val startDp = if (index % 6 == 0 || index % 6 == 2 || index % 6 == 3) 20.dp else 0.dp
            val endDp = if (index % 6 == 0 || index % 6 == 2 || index % 6 == 3) 0.dp else 20.dp

            ProfileImageItem(
                profileImage = item,
                imageRatio = imageRatio,
                startDp = startDp,
                endDp = endDp,
                openProfileImageDialog = {
                    openProfileImageDialog(index)
                },
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
    styleImageList: List<StyleEntity>,
    onCreateImageBtnClickFromStyle: () -> Unit,
    setSelectedStyleImage: (Int) -> Unit,
) {
    val pageCount = styleImageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            setSelectedStyleImage(page)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = if (isSystemInDarkTheme()) R.drawable.bg_home_screen_dark
            else R.drawable.bg_home_screen,
            contentDescription = "Background Image for Home Screen",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.home_style),
                color = MaterialTheme.colorScheme.inversePrimary,
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
                        text = "#" + styleImageList[page].name,
                        textAlign = TextAlign.Center,
                        style = Title1,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    NetworkImage(
                        imageUrl = styleImageList[page].defaultImageUrl,
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
                onClick = onCreateImageBtnClickFromStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(48.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = {
                    Text(
                        text = stringResource(id = R.string.home_generate_img_with_this_style),
                        style = Subtitle1,
                    )
                },
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.home_profile),
                style = Title2,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp),
            )
        }
    }
}

@Composable
internal fun ProfileImageItem(
    profileImage: ProfileEntity,
    imageRatio: Dp,
    startDp: Dp,
    endDp: Dp,
    openProfileImageDialog: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(imageRatio)
            .padding(start = startDp, end = endDp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = openProfileImageDialog),
        contentAlignment = Alignment.Center,
    ) {
        NetworkImage(
            imageUrl = profileImage.imageUrl,
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize(),
        )
        Image(
            painter = painterResource(id = R.drawable.bg_img_dim_small),
            contentDescription = "Background Dim",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = "#" + profileImage.name,
            style = Subtitle1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileImageDialog(
    profileImage: ProfileEntity,
    onCloseClick: () -> Unit,
    onCreateImageBtnClickFromProfile: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onCloseClick,
        modifier = Modifier
            .aspectRatio(9f / 12f)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NetworkImage(
                imageUrl = profileImage.imageUrl,
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
            )
            Image(
                painter = painterResource(id = R.drawable.bg_img_dim_large),
                contentDescription = "Background Dim",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
                    .size(40.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile_close),
                    contentDescription = "Close",
                    tint = Color.Unspecified,
                )
            }

            Column {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "#" + profileImage.name,
                    color = Color.White,
                    style = Title1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                ILabButton(
                    onClick = onCreateImageBtnClickFromProfile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                        .height(48.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = {
                        Text(
                            text = stringResource(id = R.string.home_generate_img_with_this_style),
                            style = Subtitle1,
                        )
                    },
                )
            }
        }
    }
}

@DevicePreview
@Composable
internal fun HomeScreenPreview() {
    ILabTheme {
        HomeScreen(
            uiState = HomeState(),
            padding = PaddingValues(0.dp),
            onSettingClick = {},
            onCreateImageBtnClickFromStyle = {},
            onCreateImageBtnClickFromProfile = {},
            openProfileImageDialog = {},
            dismissProfileImageDialog = {},
            getStyleProfileList = {},
            dismissNetworkErrorDialog = {},
            dismissServerErrorDialog = {},
            setSelectedStyleImage = {},
        )
    }
}

@ComponentPreview
@Composable
fun ProfileImageDialogPreview() {
    ILabTheme {
        ProfileImageDialog(
            profileImage = ProfileEntity("", "", ""),
            onCloseClick = {},
            onCreateImageBtnClickFromProfile = {},
        )
    }
}
