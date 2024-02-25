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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray100
import com.nexters.ilab.android.core.designsystem.theme.Gray200
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.feature.setting.viewmodel.SettingSideEffect
import com.nexters.ilab.android.feature.setting.viewmodel.SettingState
import com.nexters.ilab.android.feature.setting.viewmodel.SettingViewModel
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabDialog
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun SettingRoute(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val appVersionInfo = viewModel.getVersionInfo(LocalContext.current)

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SettingSideEffect.LogoutSuccess -> onNavigateToLogin()
                is SettingSideEffect.LogoutFail -> {
                    onShowErrorSnackBar(sideEffect.throwable)
                }
                is SettingSideEffect.DeleteAccountSuccess -> onNavigateToLogin()
                is SettingSideEffect.DeleteAccountFail -> {
                    onShowErrorSnackBar(sideEffect.throwable)
                }
            }
        }
    }

    SettingScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onChangeDarkTheme = onChangeDarkTheme,
        onNavigateToPrivacyPolicy = onNavigateToPrivacyPolicy,
        onLogoutClick = viewModel::signOut,
        onDeleteAccountClick = viewModel::deleteAccount,
        openDeleteAccountDialog = viewModel::openDeleteAccountDialog,
        dismissDeleteAccountDialog = viewModel::dismissDeleteAccountDialog,
        appVersionInfo = appVersionInfo,
    )
}

@Suppress("unused")
@Composable
internal fun SettingScreen(
    uiState: SettingState,
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    openDeleteAccountDialog: () -> Unit,
    dismissDeleteAccountDialog: () -> Unit,
    appVersionInfo: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isDeleteAccountDialogVisible) {
            DeleteAccountDialog(
                onCancelClick = dismissDeleteAccountDialog,
                onConfirmClick = {
                    dismissDeleteAccountDialog()
                    onDeleteAccountClick()
                },
            )
        }
        SettingTopAppBar(onBackClick)
        SettingContent(
            onNavigateToPrivacyPolicy = onNavigateToPrivacyPolicy,
            onNavigateToLogin = onLogoutClick,
            openDeleteAccountDialog = openDeleteAccountDialog,
            appVersionInfo = appVersionInfo,
        )
    }
}

@Composable
internal fun SettingContent(
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToLogin: () -> Unit,
    openDeleteAccountDialog: () -> Unit,
    appVersionInfo: String,
) {
    SettingCellNavigation(
        stringId = R.string.setting_privacy,
        onNavigationClick = onNavigateToPrivacyPolicy,
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray200),
    )
    SettingCellText(
        stringId = R.string.setting_current_version,
        version = appVersionInfo,
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray200),
    )
    SettingCellNavigation(
        stringId = R.string.setting_logout,
        onNavigationClick = onNavigateToLogin,
    )
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
            modifier = Modifier
                .clickable(onClick = openDeleteAccountDialog),
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

@Composable
internal fun DeleteAccountDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    ILabDialog(
        titleResId = R.string.setting_delete_account,
        iconResId = R.drawable.ic_warning,
        iconDescription = "Warning Icon",
        firstDescriptionResId = R.string.setting_delete_account_description,
        secondDescriptionResId = null,
        cancelTextResId = R.string.setting_delete_account_cancel,
        confirmTextResId = R.string.setting_delete_account_confirm,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
    )
}

@DevicePreview
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        uiState = SettingState(),
        onBackClick = {},
        onChangeDarkTheme = {},
        onNavigateToPrivacyPolicy = {},
        onLogoutClick = {},
        onDeleteAccountClick = {},
        openDeleteAccountDialog = {},
        dismissDeleteAccountDialog = {},
        appVersionInfo = "",
    )
}
