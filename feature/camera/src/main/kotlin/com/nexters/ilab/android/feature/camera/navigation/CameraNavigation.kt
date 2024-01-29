package com.nexters.ilab.android.feature.camera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nexters.ilab.android.feature.camera.UploadCheckRoute
import com.nexters.ilab.android.feature.camera.UploadRoute

const val CAMERA_ROUTE = "camera_route"
const val UPLOAD_ROUTE = "upload_route"
const val UPLOAD_CHECK_ROUTE = "upload_check_route"

fun NavController.navigateToCamera(navOptions: NavOptions) {
    navigate(CAMERA_ROUTE, navOptions)
}

fun NavController.navigateToUploadCheck() {
    navigate(UPLOAD_CHECK_ROUTE)
}

fun NavGraphBuilder.cameraNavGraph(
    onBackClick: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
) {
    navigation(
        startDestination = UPLOAD_ROUTE,
        route = CAMERA_ROUTE,
    ) {
        composable(route = UPLOAD_ROUTE) {
            UploadRoute(
                onBackClick = onBackClick,
                onNavigateToUploadCheck = onNavigateToUploadCheck,
            )
        }

        composable(route = UPLOAD_CHECK_ROUTE) {
            UploadCheckRoute(
                onBackClick = onBackClick,
            )
        }
    }
}
