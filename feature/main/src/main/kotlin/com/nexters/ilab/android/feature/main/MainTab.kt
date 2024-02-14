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
    UPLOAD_PHOTO(
        iconResId = R.drawable.ic_upload,
        contentDescription = "사진 업로드",
        route = "upload_photo_route",
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
