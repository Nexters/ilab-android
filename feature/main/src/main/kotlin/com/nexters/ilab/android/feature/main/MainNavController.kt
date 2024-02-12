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
import com.nexters.ilab.android.feature.uploadphoto.navigation.navigateToUploadPhoto
import com.nexters.ilab.android.feature.uploadphoto.navigation.navigateToUploadCheck
import com.nexters.ilab.android.feature.home.navigation.HOME_ROUTE
import com.nexters.ilab.android.feature.home.navigation.navigateToHome
import com.nexters.ilab.android.feature.mypage.navigation.navigateToMyPage
import com.nexters.ilab.android.feature.setting.navigation.navigateToSetting
import com.nexters.ilab.android.feature.uploadphoto.navigation.navigateToInputStyle
import com.nexters.ilab.feature.createimage.navigation.navigateToCreateImage
import com.nexters.ilab.feature.createimage.navigation.navigateToCreateImageComplete
import com.nexters.ilab.android.feature.mypage.navigation.navigateToMyAlbumImage

internal class MainNavController(
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
            MainTab.UPLOAD_PHOTO -> navController.navigateToUploadPhoto(navOptions)
            MainTab.MY_PAGE -> navController.navigateToMyPage(navOptions)
        }
    }

    fun navigateToUploadCheck() {
        navController.navigateToUploadCheck()
    }

    fun navigateToInputStyle() {
        navController.navigateToInputStyle()
    }

    fun navigateToCreateImage() {
        navController.navigateToCreateImage()
    }

    fun navigateToCreateImageComplete() {
        navController.navigateToCreateImageComplete()
    }

    fun navigateToMyAlbumImage() {
        navController.navigateToMyAlbumImage()
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

    fun clearBackStack() {
        val options = NavOptions.Builder()
            .setPopUpTo(navController.graph.findStartDestination().id, inclusive = false)
            .build()
        navController.navigate(startDestination, options)
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
internal fun rememberMainNavController(
    navController: NavHostController = rememberNavController(),
): MainNavController = remember(navController) {
    MainNavController(navController)
}
