package com.nexters.ilab.android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.android.core.domain.entity.UserInfoEntity
import com.nexters.ilab.android.core.domain.entity.UserThumbnail
import com.nexters.ilab.android.feature.mypage.viewmodel.MyPageState
import com.nexters.ilab.android.feature.mypage.viewmodel.MyPageViewModel
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingIndicator
import com.nexters.ilab.core.ui.component.NetworkErrorDialog
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.ServerErrorDialog
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onNavigateToMyAlbumImage: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val navigateToMyAlbum: (Int) -> Unit = {
        viewModel.setSelectedMyAlbum(it)
        onNavigateToMyAlbumImage()
    }

    MyPageScreen(
        uiState = uiState,
        padding = padding,
        onSettingClick = onSettingClick,
        onMoreBtnClick = {},
        onShareBtnClick = viewModel::shareMyAlbumImage,
        onDeleteBtnClick = {},
        onNavigateToMyAlbumImage = navigateToMyAlbum,
        getUserInfo = viewModel::getUserInfo,
        dismissServerErrorDialog = viewModel::dismissServerErrorDialog,
        dismissNetworkErrorDialog = viewModel::dismissNetworkErrorDialog,
    )
}

@Composable
internal fun MyPageScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onMoreBtnClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onDeleteBtnClick: () -> Unit,
    onNavigateToMyAlbumImage: (Int) -> Unit,
    getUserInfo: () -> Unit,
    dismissServerErrorDialog: () -> Unit,
    dismissNetworkErrorDialog: () -> Unit,
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
                    getUserInfo()
                },
            )
        }
        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = {
                    dismissNetworkErrorDialog()
                    getUserInfo()
                },
            )
        }
        MyPageTopAppBar(onSettingClick)
        MyPageContent(
            userInfo = uiState.userInfo,
            myAlbumImageList = uiState.myAlbumFullImageList,
            onMoreBtnClick = onMoreBtnClick,
            onShareBtnClick = onShareBtnClick,
            onDeleteBtnClick = onDeleteBtnClick,
            onNavigateToMyAlbumImage = onNavigateToMyAlbumImage,
        )
    }
}

@Composable
internal fun MyPageTopAppBar(onSettingClick: () -> Unit) {
    ILabTopAppBar(
        titleRes = R.string.mypage_title,
        navigationType = TopAppBarNavigationType.Setting,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .padding(end = 10.dp)
            .height(56.dp),
        onNavigationClick = onSettingClick,
    )
}

@Composable
internal fun MyPageContent(
    userInfo: UserInfoEntity,
    myAlbumImageList: List<UserThumbnail>,
    onMoreBtnClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onDeleteBtnClick: () -> Unit,
    onNavigateToMyAlbumImage: (Int) -> Unit,
) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    val myAlbumCount = myAlbumImageList.size

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 16.dp, start = 20.dp, end = 20.dp),
    ) {
        item(
            span = {
                GridItemSpan(maxLineSpan)
            },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MyPageContentUser(
                    userInfo = userInfo,
                    albumImgCount = myAlbumCount,
                )
            }
        }
        if (myAlbumCount == 0) {
            item(span = span) {
                MyPageContentEmpty()
            }
        } else {
            items(myAlbumCount) { iter ->
                MyAlbumImage(
                    myAlbum = myAlbumImageList[iter],
                    onMoreBtnClick = onMoreBtnClick,
                    onShareBtnClick = onShareBtnClick,
                    onDeleteBtnClick = onDeleteBtnClick,
                    onNavigateToMyAlbumImage = onNavigateToMyAlbumImage,
                    index = iter,
                )
            }
        }
        item(span = span) {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
internal fun MyPageContentUser(
    userInfo: UserInfoEntity,
    albumImgCount: Int,
) {
    Spacer(modifier = Modifier.height(32.dp))
    if (userInfo.profileImageUrl.isEmpty()) {
        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = "profile image",
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier.size(100.dp),
        )
    } else {
        NetworkImage(
            imageUrl = userInfo.profileImageUrl,
            contentDescription = "profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = userInfo.nickname,
        style = Title2,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.height(40.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.mypage_my_album),
            style = Title2,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = albumImgCount.toString(),
            style = Title2,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@Composable
internal fun MyPageContentEmpty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_no_img),
            contentDescription = "no image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.mypage_there_is_no_img),
            textAlign = TextAlign.Center,
            style = Contents1,
            color = MaterialTheme.colorScheme.inverseOnSurface,
        )
    }
}

@Composable
internal fun MyAlbumImage(
    myAlbum: UserThumbnail,
    onMoreBtnClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onDeleteBtnClick: () -> Unit,
    onNavigateToMyAlbumImage: (Int) -> Unit,
    index: Int,

) {
    val isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f),
    ) {
        NetworkImage(
            imageUrl = myAlbum.images.first().imageUrl,
            contentDescription = "My Album Image",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clickable {
                    onNavigateToMyAlbumImage(index)
                },
        )
        Image(
            painter = painterResource(id = R.drawable.bg_img_dim_small),
            contentDescription = "Background Dim",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box {
            IconButton(
                onClick = { !isExpanded },
//                onClick = onMoreBtnClick,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_more),
                    contentDescription = "more",
                    tint = Color.Unspecified,
                )
            }
            onDropDownMenu(
                isDropDownShow = isExpanded,
                onDismiss = {},
                onShareClick = onShareBtnClick,
                onDeleteClick = onDeleteBtnClick,
            )
        }
        Text(
            text = "#" + myAlbum.images.first().imageStyle.name,
            style = Subtitle1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}

@Composable
fun onDropDownMenu(
    isDropDownShow: Boolean,
    onDismiss: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    DropdownMenu(
        expanded = isDropDownShow,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(Color.White)
            .wrapContentSize()
            .clip(RoundedCornerShape(12.dp)),
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.mypage_dropdown_delete),
                    style = Contents2,
                    color = Color.Black,
                )
            },
            onClick = onDeleteClick,
        )
        HorizontalDivider(modifier = Modifier.height(1.dp))
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.mypage_dropdown_share),
                    style = Contents2,
                    color = Color.Black,
                )
            },
            onClick = onShareClick,

            )
    }
}

@DevicePreview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        uiState = MyPageState(),
        padding = PaddingValues(0.dp),
        onSettingClick = {},
        onMoreBtnClick = {},
        onShareBtnClick = {},
        onDeleteBtnClick = {},
        onNavigateToMyAlbumImage = {},
        getUserInfo = {},
        dismissNetworkErrorDialog = {},
        dismissServerErrorDialog = {},
    )
}

@DevicePreview
@Composable
fun MyPageScreenTestPreview() {
    MyPageContentEmpty()
}
