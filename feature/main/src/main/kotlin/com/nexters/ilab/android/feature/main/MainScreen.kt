package com.nexters.ilab.android.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Gray100
import com.nexters.ilab.android.core.designsystem.theme.Gray400
import com.nexters.ilab.android.core.designsystem.theme.Gray900
import com.nexters.ilab.android.feature.home.navigation.homeNavGraph
import com.nexters.ilab.android.feature.mypage.navigation.myPageNavGraph
import com.nexters.ilab.android.feature.privacypolicy.navigation.privacyPolicyNavGraph
import com.nexters.ilab.android.feature.setting.navigation.settingNavGraph
import com.nexters.ilab.android.feature.uploadphoto.navigation.uploadPhotoNavGraph
import com.nexters.ilab.core.ui.ComponentPreview
import com.nexters.ilab.feature.createimage.navigation.createImageNavGraph
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
internal fun MainScreen(
    onChangeDarkTheme: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    navigator: MainNavController = rememberMainNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val resource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> resource.getString(R.string.error_message_network)
                    else -> resource.getString(R.string.error_message_unknown)
                },
            )
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                ) {
                    homeNavGraph(
                        padding = padding,
                        onSettingClick = { navigator.navigateToSetting() },
                        onShowErrorSnackBar = onShowErrorSnackBar,
                    )

                    uploadPhotoNavGraph(
                        navController = navigator.navController,
                        onBackClick = navigator::popBackStackIfNotHome,
                        onNavigateToPrivacyPolicy = navigator::navigateToPrivacyPolicy,
                        onNavigateToUploadCheck = navigator::navigateToUploadCheck,
                        onNavigateToInputStyle = navigator::navigateToInputStyle,
                        onNavigateToCreateImage = navigator::navigateToCreateImage,
                    )

                    createImageNavGraph(
                        navController = navigator.navController,
                        onCloseClick = navigator::clearBackStack,
                        onNavigateToCreateImageComplete = navigator::navigateToCreateImageComplete,
                    )

                    myPageNavGraph(
                        navController = navigator.navController,
                        padding = padding,
                        onCloseClick = navigator::popBackStackIfNotHome,
                        onSettingClick = { navigator.navigateToSetting() },
                        onNavigateToMyAlbumImage = { navigator.navigateToMyAlbumImage() },
                        onShowErrorSnackBar = onShowErrorSnackBar,
                    )

                    settingNavGraph(
                        onBackClick = navigator::popBackStackIfNotHome,
                        onChangeDarkTheme = onChangeDarkTheme,
                        onNavigateToPrivacyPolicy = navigator::navigateToPrivacyPolicy,
                        onLogoutClick = onLogoutClick,
                        onDeleteAccountClick = onDeleteAccountClick,
                        onShowErrorSnackBar = onShowErrorSnackBar,
                    )

                    privacyPolicyNavGraph(
                        onCloseClick = navigator::popBackStackIfNotHome,
                    )
                }
            }
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Color.White,
    )
}

@Composable
private fun MainBottomBar(
    visible: Boolean,
    tabs: PersistentList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    if (visible) {
        Box(
            modifier = Modifier.background(Color.White),
        ) {
            Column {
                HorizontalDivider(color = Gray100)
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    tabs.forEach { tab ->
                        MainBottomBarItem(
                            tab = tab,
                            selected = tab == currentTab,
                            onClick = { onTabSelected(tab) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (tab == MainTab.UPLOAD_PHOTO) {
            if (LocalInspectionMode.current) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_upload),
                    contentDescription = "Upload Photo Icon",
                    tint = Color.Unspecified,
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.ic_upload_photo)
                        .build(),
                    contentDescription = "Upload Photo Icon",
                    modifier = Modifier,
                )
            }
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(tab.iconResId),
                contentDescription = tab.contentDescription,
                tint = if (selected) Gray900 else Gray400,
            )
        }
    }
}

@ComponentPreview
@Composable
fun MainBottomBarPreview() {
    MainBottomBar(
        visible = true,
        tabs = MainTab.entries.toPersistentList(),
        currentTab = MainTab.HOME,
        onTabSelected = {},
    )
}
