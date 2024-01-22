package com.nexters.ilab.android.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.setting.SettingRoute

const val SETTING_ROUTE = "setting_route"

fun NavController.navigateToSetting() {
    navigate(SETTING_ROUTE)
}

fun NavGraphBuilder.settingNavGraph(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable(route = SETTING_ROUTE) {
        SettingRoute(
            onBackClick = onBackClick,
            onChangeDarkTheme = onChangeDarkTheme,
            onLogoutClick = onLogoutClick,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
