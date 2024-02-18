package com.nexters.ilab.android.feature.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    onNavigateToPrivacyPolicy: () -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable(
        route = SETTING_ROUTE,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
    ) {
        SettingRoute(
            onBackClick = onBackClick,
            onChangeDarkTheme = onChangeDarkTheme,
            onNavigateToPrivacyPolicy = onNavigateToPrivacyPolicy,
            onLogoutClick = onLogoutClick,
            onDeleteAccountClick = onDeleteAccountClick,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
