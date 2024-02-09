package com.nexters.ilab.android.feature.uploadphoto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nexters.ilab.android.feature.uploadphoto.CreateImageCompleteRoute
import com.nexters.ilab.android.feature.uploadphoto.CreateImageRoute
import com.nexters.ilab.android.feature.uploadphoto.InputStyleRoute
import com.nexters.ilab.android.feature.uploadphoto.UploadCheckRoute
import com.nexters.ilab.android.feature.uploadphoto.UploadPhotoRoute
import com.nexters.ilab.android.feature.uploadphoto.UploadPhotoViewModel

const val UPLOAD_PHOTO_ROUTE = "upload_photo_route"
const val UPLOAD_ROUTE = "upload_route"
const val UPLOAD_CHECK_ROUTE = "upload_check_route"
const val INPUT_STYLE_ROUTE = "input_style_route"
const val CREATE_IMAGE_ROUTE = "create_image_route"
const val CREATE_IMAGE_COMPLETE_ROUTE = "create_image_complete_route"

fun NavController.navigateToUploadPhoto(navOptions: NavOptions) {
    navigate(UPLOAD_PHOTO_ROUTE, navOptions)
}

fun NavController.navigateToUploadCheck() {
    navigate(UPLOAD_CHECK_ROUTE)
}

fun NavController.navigateToInputStyle() {
    navigate(INPUT_STYLE_ROUTE)
}

fun NavController.navigateToCreateImage() {
    navigate(CREATE_IMAGE_ROUTE)
}

fun NavController.navigateToCreateImageComplete() {
    navigate(CREATE_IMAGE_COMPLETE_ROUTE)
}

fun NavGraphBuilder.uploadPhotoNavGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
    onNavigateToInputStyle: () -> Unit,
    onNavigateToCreateImage: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
) {
    navigation(
        startDestination = UPLOAD_ROUTE,
        route = UPLOAD_PHOTO_ROUTE,
    ) {
        composable(route = UPLOAD_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)
            UploadPhotoRoute(
                onBackClick = onBackClick,
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

        composable(route = CREATE_IMAGE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)
            CreateImageRoute(
                onCloseClick = onCloseClick,
                onNavigateToCreateImageComplete = onNavigateToCreateImageComplete,
                viewModel = viewModel,
            )
        }

        composable(route = CREATE_IMAGE_COMPLETE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<UploadPhotoViewModel>(navController)
            CreateImageCompleteRoute(
                onCloseClick = onCloseClick,
                viewModel = viewModel,
            )
        }
    }
}

// https://youtu.be/h61Wqy3qcKg?si=OqctoATR5MGbypOW
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
