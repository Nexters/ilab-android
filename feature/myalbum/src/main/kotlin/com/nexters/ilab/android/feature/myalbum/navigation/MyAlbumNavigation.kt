package com.nexters.ilab.android.feature.myalbum.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.ilab.android.core.common.MyAlbumModel
import com.nexters.ilab.android.feature.myalbum.MyAlbumRoute

const val STYLE_NAME = "style_name"
const val MY_ALBUM_IMAGE_URL_LIST = "my_album_image_url_list"
const val MY_ALBUM_ROUTE = "my_album_route/{$STYLE_NAME}/{$MY_ALBUM_IMAGE_URL_LIST}"

fun NavController.navigateToMyAlbum(
//    styleName: String,
//    myAlbumImageUrlList: Array<String>,
) {
//    navigate("my_album_route/$styleName/$myAlbumImageUrlList")
    navigate(MY_ALBUM_ROUTE)
}

fun NavGraphBuilder.myAlbumNavGraph(
    navController: NavController,
    onCloseClick: () -> Unit,
) {
    composable(
        route = MY_ALBUM_ROUTE,
//        arguments = listOf(
//            navArgument(STYLE_NAME) {
//                type = NavType.StringType
//            },
//            navArgument(MY_ALBUM_IMAGE_URL_LIST) {
//                type = NavType.StringArrayType
//            },
//        ),
    ) {
        val myAlbum = navController.previousBackStackEntry?.savedStateHandle?.get<MyAlbumModel>("my_album")
        MyAlbumRoute(
            onCloseClick = onCloseClick,
            myAlbum = myAlbum ?: MyAlbumModel(),
        )
    }
}
