package com.nexters.ilab.android.feature.camera.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.camera.CameraRoute

const val CAMERA_ROUTE = "camera_route"

fun NavController.navigateToCamera(navOptions: NavOptions) {
    navigate(CAMERA_ROUTE, navOptions)
}

fun NavGraphBuilder.cameraNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable(route = CAMERA_ROUTE) {
        CameraRoute(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}
