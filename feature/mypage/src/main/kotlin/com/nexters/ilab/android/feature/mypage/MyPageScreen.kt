package com.nexters.ilab.android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue200
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Gray200
import com.nexters.ilab.android.core.designsystem.theme.Gray900
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Suppress("unused")
@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    MyPageScreen(
        padding = padding,
        onSettingClick = onSettingClick,
    )
}

@Composable
internal fun MyPageScreen(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MyPageTopAppBar(onSettingClick)
        MyPageContent()
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
            .height(56.dp),
        onNavigationClick = onSettingClick,
    )
}

@Composable
internal fun MyPageContent() {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 45.dp),
    ) {
        item (span = span) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                MyPageContentUser(profileImgUrl = "", albumImgCount = 12)
            }

        }
        items(12) { _ ->
            MyAlbumImage({}, imgUrl = "")
        }
    }
}


@Composable
internal fun MyPageContentUser(profileImgUrl: String, albumImgCount: Int) {
    Spacer(modifier = Modifier.height(32.dp))
    // UI 구현 용으로 프로필 url 대신 기본 Image 사용
    Image(
        painter = painterResource(R.drawable.ic_profile),
        contentDescription = "profile image",
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
        modifier = Modifier.size(100.dp),
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "name" + stringResource(id = R.string.mypage_name_postfix),
        style = Title2,
        color = Gray900,
    )
    Spacer(modifier = Modifier.height(40.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.mypage_my_album),
            style = Title2,
            color = Gray900,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = albumImgCount.toString(),
            style = Title2,
            color = Blue600,
        )
    }
}

@Composable
internal fun MyAlbumImage(onMoreBtnClick: () -> Unit, imgUrl: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
    ) {
        // UI 구현 용으로 album url 대신 sample Image 사용
        Image(
            painter = painterResource(R.drawable.ic_myalbum_sample),
            contentDescription = "MyAlbum image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
        )
        IconButton(
            onClick = onMoreBtnClick,
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
        Text(
            text = "#스케치",
            style = Subtitle1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}

@DevicePreview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(PaddingValues(0.dp), {})
}
