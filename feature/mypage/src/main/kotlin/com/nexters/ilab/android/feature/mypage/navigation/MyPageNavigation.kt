package com.nexters.ilab.android.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.mypage.MyPageRoute

const val MY_PAGE_ROUTE = "my_page_route"

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MY_PAGE_ROUTE, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onNavigateToMyAlbum: (String, List<String>) -> Unit,
) {
    composable(route = MY_PAGE_ROUTE) {
        MyPageRoute(
            padding = padding,
            onSettingClick = onSettingClick,
            onNavigateToMyAlbum = onNavigateToMyAlbum,
        )
    }
}
