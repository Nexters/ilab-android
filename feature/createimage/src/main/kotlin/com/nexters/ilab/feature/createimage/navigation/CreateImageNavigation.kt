package com.nexters.ilab.feature.createimage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nexters.ilab.feature.createimage.CreateImageCompleteRoute
import com.nexters.ilab.feature.createimage.CreateImageRoute
import com.nexters.ilab.feature.createimage.CreateImageViewModel

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
