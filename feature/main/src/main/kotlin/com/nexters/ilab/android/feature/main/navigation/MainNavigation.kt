package com.nexters.ilab.android.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val MAIN_ROUTE = "main_route"

fun NavController.navigateToMain(navOptions: NavOptions) {
    navigate(MAIN_ROUTE, navOptions)
}
