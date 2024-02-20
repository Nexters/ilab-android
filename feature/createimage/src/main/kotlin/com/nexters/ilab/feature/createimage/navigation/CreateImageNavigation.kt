package com.nexters.ilab.feature.createimage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nexters.ilab.android.core.common.extension.sharedViewModel
import com.nexters.ilab.feature.createimage.CreateImageCompleteRoute
import com.nexters.ilab.feature.createimage.CreateImageRoute
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageViewModel

const val CREATE_IMAGE_ROUTE = "create_image_route"
const val CREATE_ROUTE = "create_route"
const val CREATE_IMAGE_COMPLETE_ROUTE = "create_image_complete_route"

fun NavController.navigateToCreateImage() {
    navigate(CREATE_IMAGE_ROUTE)
}

fun NavController.navigateToCreateImageComplete() {
    navigate(CREATE_IMAGE_COMPLETE_ROUTE)
}

fun NavGraphBuilder.createImageNavGraph(
    navController: NavHostController,
    onCloseClick: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
) {
    navigation(
        startDestination = CREATE_ROUTE,
        route = CREATE_IMAGE_ROUTE,
    ) {
        composable(route = CREATE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<CreateImageViewModel>(navController)
            CreateImageRoute(
                onCloseClick = onCloseClick,
                onNavigateToCreateImageComplete = onNavigateToCreateImageComplete,
                viewModel = viewModel,
            )
        }

        composable(route = CREATE_IMAGE_COMPLETE_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<CreateImageViewModel>(navController)
            CreateImageCompleteRoute(
                onCloseClick = onCloseClick,
                viewModel = viewModel,
            )
        }
    }
}
