package com.nexters.ilab.android.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.login.LoginRoute

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions) {
    navigate(LOGIN_ROUTE, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    onLoginClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(
            onLoginClick = onLoginClick,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
