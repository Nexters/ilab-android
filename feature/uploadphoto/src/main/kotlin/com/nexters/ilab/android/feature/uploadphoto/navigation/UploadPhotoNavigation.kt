package com.nexters.ilab.android.feature.uploadphoto.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nexters.ilab.android.core.common.extension.sharedViewModel
import com.nexters.ilab.android.feature.uploadphoto.InputStyleRoute
import com.nexters.ilab.android.feature.uploadphoto.UploadCheckRoute
import com.nexters.ilab.android.feature.uploadphoto.UploadPhotoRoute
import com.nexters.ilab.android.feature.uploadphoto.viewmodel.UploadPhotoViewModel

const val UPLOAD_PHOTO_ROUTE = "upload_photo_route"
const val UPLOAD_ROUTE = "upload_route"
const val UPLOAD_CHECK_ROUTE = "upload_check_route"
const val INPUT_STYLE_ROUTE = "input_style_route"

fun NavController.navigateToUploadPhoto(navOptions: NavOptions) {
    navigate(UPLOAD_PHOTO_ROUTE, navOptions)
}

fun NavController.navigateToUploadCheck() {
    navigate(UPLOAD_CHECK_ROUTE)
}

fun NavController.navigateToInputStyle() {
    navigate(INPUT_STYLE_ROUTE)
}

fun NavGraphBuilder.uploadPhotoNavGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
    onNavigateToInputStyle: () -> Unit,
    onNavigateToCreateImage: () -> Unit,
) {
    navigation(
        startDestination = UPLOAD_ROUTE,
        route = UPLOAD_PHOTO_ROUTE,
    ) {
        composable(route = UPLOAD_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)

            UploadPhotoRoute(
                onBackClick = onBackClick,
                onNavigateToPrivacyPolicy = onNavigateToPrivacyPolicy,
                onNavigateToUploadCheck = onNavigateToUploadCheck,
                viewModel = viewModel,
            )
        }

        composable(route = UPLOAD_CHECK_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)
            UploadCheckRoute(
                onBackClick = onBackClick,
                onNavigateToInputStyle = onNavigateToInputStyle,
                viewModel = viewModel,
            )
        }

        composable(route = INPUT_STYLE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)
            InputStyleRoute(
                onBackClick = onBackClick,
                onNavigateToCreateImage = onNavigateToCreateImage,
                viewModel = viewModel,
            )
        }
    }
}
