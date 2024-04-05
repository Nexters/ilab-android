package com.nexters.ilab.feature.createimage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.nexters.ilab.android.core.common.extension.sharedViewModel
import com.nexters.ilab.feature.createimage.CreateImageCompleteRoute
import com.nexters.ilab.feature.createimage.CreateImageRoute
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageViewModel

const val IMAGE_URL = "image_url"
const val STYLE_ID = "style_id"
const val CREATE_ROUTE = "create_route/{$IMAGE_URL}/{$STYLE_ID}"
const val CREATE_IMAGE_ROUTE = "create_image_route"
const val CREATE_IMAGE_COMPLETE_ROUTE = "create_image_complete_route"

fun NavController.navigateToCreateImage(
    imageUrl: String,
    styleId: Int,
) {
    navigate("create_route/$imageUrl/$styleId")
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
        startDestination = CREATE_IMAGE_ROUTE,
        route = CREATE_ROUTE,
        arguments = listOf(
            navArgument(IMAGE_URL) {
                type = NavType.StringType
            },
            navArgument(STYLE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        composable(route = CREATE_IMAGE_ROUTE) { entry ->
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
