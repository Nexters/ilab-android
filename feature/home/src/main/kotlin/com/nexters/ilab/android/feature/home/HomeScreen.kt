package com.nexters.ilab.android.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue500
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Gray300
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.android.core.designsystem.theme.pretendardFamily
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Suppress("unused")
@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(padding = padding, onSettingClick)
}

@Composable
internal fun HomeScreen(padding: PaddingValues, onSettingClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopAppBar(onSettingClick)
        HomeContent({})
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
internal fun HomeContent(onGenerateImgBtnClick: () -> Unit) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item (span = span) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                HomeKeywordView(onGenerateImgBtnClick)
                HomeProfileView()
            }

        }
        items(20) { iter ->
            KeywordSampleImageItem(imgUrl = "https://picsum.photos/200/300")
        }
    }
}

@Composable
internal fun KeywordSampleImageItem(imgUrl: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
            .background(color = Blue600),
    ) {
        NetworkImage(
            imageUrl = imgUrl,
            contentDescription = "MyAlbum image",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
        )
        Text(
            text = "#몽환적인",
            style = Subtitle1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeKeywordView(onGenerateImgBtnClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_home_background),
            contentDescription = "home background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(484.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(484.dp)
                .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.home_style),
                color = Blue500,
                style = Subtitle2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            val pagerState = rememberPagerState(pageCount = {
                4
            })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "#$page",
                        textAlign = TextAlign.Center,
                        style = Title1,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_home_sample),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(200.dp, 266.dp),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    PagerIndicator(pagerState)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))
            ILabButton(
                onClick = onGenerateImgBtnClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerIndicator(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Blue600 else Gray300
            val modifier = if (pagerState.currentPage == iteration) Modifier.padding(4.dp)
                .clip(CircleShape)
                .width(18.dp)
                .background(color)
                .size(7.dp) else Modifier.padding(4.dp)
                .clip(CircleShape)
                .background(color)
                .size(7.dp)
            Box(
                modifier = modifier,
            )
        }
    }
}

@Composable
internal fun HomeProfileView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(38.dp))
        Text(
            text = stringResource(id = R.string.home_profile),
            style = Title2,
            color = Color.Black,
        )

    }
}

@Preview(showBackground = true)
@Composable
internal fun previewHomeScreen() {
    HomeScreen(PaddingValues(0.dp), {})
}
