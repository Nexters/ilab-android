package com.nexters.ilab.android.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nexters.ilab.android.core.common.extension.sharedViewModel
import com.nexters.ilab.android.feature.mypage.MyAlbumImageRoute
import com.nexters.ilab.android.feature.mypage.MyPageRoute
import com.nexters.ilab.android.feature.mypage.MyPageViewModel

const val MY_PAGE_ROUTE = "my_page_route"
const val MY_PROFILE_ROUTE = "my_profile_route"
const val MY_ALBUM_ROUTE = "my_album_route"

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MY_PAGE_ROUTE, navOptions)
}

fun NavController.navigateToMyAlbumImage() {
    navigate(MY_ALBUM_ROUTE)
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    onCloseClick: () -> Unit,
    onSettingClick: () -> Unit,
    onNavigateToMyAlbumImage: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    navigation(
        startDestination = MY_PROFILE_ROUTE,
        route = MY_PAGE_ROUTE,
    ) {
        composable(route = MY_PROFILE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)
            MyPageRoute(
                padding = padding,
                onSettingClick = onSettingClick,
                onNavigateToMyAlbumImage = onNavigateToMyAlbumImage,
                onShowErrorSnackBar = onShowErrorSnackBar,
                viewModel = viewModel,
            )
        }

        composable(route = MY_ALBUM_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)
            MyAlbumImageRoute(
                onCloseClick = onCloseClick,
                viewModel = viewModel,
            )
        }
    }
}
