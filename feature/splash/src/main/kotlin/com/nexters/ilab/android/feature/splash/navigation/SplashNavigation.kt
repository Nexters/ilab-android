package com.nexters.ilab.android.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.splash.SplashRoute

const val SPLASH_ROUTE = "splash_route"

fun NavGraphBuilder.splashNavGraph(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    composable(route = SPLASH_ROUTE) {
        SplashRoute(
            navigateToLogin = navigateToLogin,
            navigateToMain = navigateToMain,
        )
    }
}
