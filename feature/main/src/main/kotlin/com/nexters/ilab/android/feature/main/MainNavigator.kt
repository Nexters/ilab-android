package com.nexters.ilab.android.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.nexters.ilab.android.feature.camera.navigation.navigateToCamera
import com.nexters.ilab.android.feature.home.navigation.HOME_ROUTE
import com.nexters.ilab.android.feature.home.navigation.navigateToHome
import com.nexters.ilab.android.feature.login.navigation.LOGIN_ROUTE
import com.nexters.ilab.android.feature.login.navigation.navigateToLogin
import com.nexters.ilab.android.feature.main.navigation.navigateToMain
import com.nexters.ilab.android.feature.mypage.navigation.navigateToMyPage
import com.nexters.ilab.android.feature.splash.navigation.SPLASH_ROUTE
import com.nexters.ilab.android.feature.setting.navigation.navigateToSetting

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = HOME_ROUTE

    val currentTab: MainTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainTab.Companion::find)

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.CAMERA -> navController.navigateToCamera(navOptions)
            MainTab.MY_PAGE -> navController.navigateToMyPage(navOptions)
        }
    }

    fun navigateToMain() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(LOGIN_ROUTE, inclusive = true)
            .build()
        navController.navigateToMain(navOptions)
    }

    fun navigateToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(SPLASH_ROUTE, inclusive = true)
            .build()
        navController.navigateToLogin(navOptions)
    }

    fun navigateToSetting() {
        navController.navigateToSetting()
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    // https://github.com/droidknights/DroidKnights2023_App/pull/243/commits/4bfb6d13908eaaab38ab3a59747d628efa3893cb
    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HOME_ROUTE)) {
            popBackStack()
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
