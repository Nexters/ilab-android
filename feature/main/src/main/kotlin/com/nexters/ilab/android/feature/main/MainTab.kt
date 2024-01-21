package com.nexters.ilab.android.feature.main

import com.nexters.ilab.android.core.designsystem.R

internal enum class MainTab(
    val iconResId: Int,
    val contentDescription: String,
    val route: String,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        contentDescription = "홈",
        route = "home_route",
    ),
    CAMERA(
        iconResId = R.drawable.ic_camera,
        contentDescription = "카메라",
        route = "camera_route",
    ),
    MY_PAGE(
        iconResId = R.drawable.ic_my_page,
        contentDescription = "마이페이지",
        route = "my_page_route",
    ),
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}
