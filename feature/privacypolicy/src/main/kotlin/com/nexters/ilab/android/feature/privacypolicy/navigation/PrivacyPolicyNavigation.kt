package com.nexters.ilab.android.feature.privacypolicy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.ilab.android.feature.privacypolicy.PrivacyPolicyRoute

const val PRIVACY_POLICY_ROUTE = "privacy_policy_route"

fun NavController.navigateToPrivacyPolicy() {
    navigate(PRIVACY_POLICY_ROUTE)
}

fun NavGraphBuilder.privacyPolicyNavGraph(
    onCloseClick: () -> Unit,
) {
    composable(
        route = PRIVACY_POLICY_ROUTE,
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Up,
//                animationSpec = tween(500),
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentTransitionScope.SlideDirection.Down,
//                animationSpec = tween(500),
//            )
//        },
    ) {
        PrivacyPolicyRoute(
            onCloseClick = onCloseClick,
        )
    }
}
