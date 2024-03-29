package com.nexters.ilab.android.feature.myalbum

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
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
import com.nexters.ilab.android.feature.myalbum.viewmodel.MyAlbumState
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.android.feature.myalbum.viewmodel.MyAlbumSideEffect
import com.nexters.ilab.android.feature.myalbum.viewmodel.MyAlbumViewModel
import com.nexters.ilab.android.core.common.MyAlbumModel
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.BackgroundImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.PagerIndicator
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController

@Composable
internal fun MyAlbumRoute(
    onCloseClick: () -> Unit,
    myAlbum: MyAlbumModel,
    viewModel: MyAlbumViewModel = hiltViewModel(),
) {
    val myPageState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val systemUiController = rememberExSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkTheme,
            isNavigationBarContrastEnforced = false,
        )
        onDispose {}
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        viewModel.deleteCacheDir()
    }
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is MyAlbumSideEffect.ShareMyAlbum -> {
                    val uriList = ArrayList(sideEffect.imageUriList.map { Uri.parse(it) })
                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND_MULTIPLE
                        type = "image/*"
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        // https://stackoverflow.com/questions/57689792/permission-denial-while-sharing-file-with-fileprovider
                        val clipData = ClipData.newRawUri("", uriList.get(0)).apply {
                            for (i in 1 until uriList.size) {
                                addItem(ClipData.Item(uriList[i]))
                            }
                        }
                        setClipData(clipData)
                    }
                    launcher.launch(Intent.createChooser(shareIntent, null))
                }

                is MyAlbumSideEffect.SaveMyAlbumSuccess -> {
                    Toast.makeText(context, context.getString(R.string.create_image_save_complete), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MyAlbumScreen(
        uiState = myPageState,
        myAlbum = myAlbum,
        initMyAlbum = viewModel::initMyAlbum,
        onCloseClick = onCloseClick,
        onShareBtnClick = viewModel::shareMyAlbum,
        onSaveBtnClick = viewModel::saveMyAlbumImage,
    )
}

@Composable
private fun MyAlbumScreen(
    uiState: MyAlbumState,
    myAlbum: MyAlbumModel,
    initMyAlbum: (MyAlbumModel) -> Unit,
    onCloseClick: () -> Unit,
    onShareBtnClick: () -> Unit,
    onSaveBtnClick: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        initMyAlbum(myAlbum)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = if (isSystemInDarkTheme()) R.drawable.bg_my_page_screen_dark
            else R.drawable.bg_my_page_screen,
            contentDescription = "Background Image for My Album Screen",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        Column {
            MyAlbumTopAppBar(onBackClick = onCloseClick)
            MyAlbumContent(
                imageStyle = uiState.styleName,
                myAlbumImageList = uiState.myAlbumImageUrlList,
                onShareBtnClick = onShareBtnClick,
                onSaveBtnClick = onSaveBtnClick,
            )
        }
    }
}

@Composable
private fun MyAlbumTopAppBar(
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
private fun MyAlbumContent(
    imageStyle: String,
    myAlbumImageList: ImmutableList<String>,
    onShareBtnClick: () -> Unit,
    onSaveBtnClick: () -> Unit,
) {
    val pageCount = myAlbumImageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "#$imageStyle",
            style = Title1,
            color = MaterialTheme.colorScheme.onBackground,
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
                    imageUrl = myAlbumImageList[page],
                    contentDescription = "My Album Image",
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
                onClick = onShareBtnClick,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 4.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
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
fun MyAlbumScreenPreview() {
    ILabTheme {
        MyAlbumScreen(
            uiState = MyAlbumState(),
            myAlbum = MyAlbumModel(),
            initMyAlbum = {},
            onCloseClick = {},
            onShareBtnClick = {},
            onSaveBtnClick = {},
        )
    }
}
