package com.nexters.ilab.android.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray100
import com.nexters.ilab.android.core.designsystem.theme.Gray200
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Suppress("unused")
@Composable
internal fun SettingRoute(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    SettingScreen(
        onBackClick = onBackClick,
        onChangeDarkTheme = onChangeDarkTheme,
        onLogoutClick = onLogoutClick,
        onDeleteAccountClick = onDeleteAccountClick,
    )
}

@Suppress("unused")
@Composable
internal fun SettingScreen(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SettingTopAppBar(onBackClick)
        SettingContent(onLogoutClick, onDeleteAccountClick)
    }
}

@Composable
internal fun SettingContent(
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    SettingCellNavigation(R.string.setting_privacy)
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray200),
    )
    SettingCellText(R.string.setting_current_version, "1.0.0")
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray200),
    )
    SettingCellNavigation(R.string.setting_logout, onLogoutClick)
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Gray100),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(
            text = stringResource(id = R.string.setting_delete_account),
            style = Contents1,
            color = Color.Black,
            modifier = Modifier.clickable(onClick = onDeleteAccountClick),
        )
    }
}

@Composable
internal fun SettingCellText(stringId: Int, version: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = stringId),
            style = Contents1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = version,
            style = Subtitle2,
            color = Color.Black,
        )
    }
}

@Composable
internal fun SettingCellNavigation(
    stringId: Int,
    onNavigationClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onNavigationClick)
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = stringId),
            style = Contents1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_next),
            contentDescription = null,
        )
    }
}

@Composable
internal fun SettingTopAppBar(onBackClick: () -> Unit) {
    ILabTopAppBar(
        titleRes = R.string.setting_title,
        navigationType = TopAppBarNavigationType.Back,
        onNavigationClick = onBackClick,
        navigationIconContentDescription = null,
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
    )
}

@DevicePreview
@Composable
fun UploadPhotoScreenPreview() {
    SettingScreen({}, {}, {}, {})
}
