package com.nexters.ilab.android.feature.mypage


data class MyAlbum (
    val myAlbumImage: List<Pair<String, String>> = emptyList(),
    val myAlbumKeyword: String = ""
)
data class MyPageState(
    val selectedMyAlbum: Int = 0,
    val myAlbumImageList: List<MyAlbum> = emptyList(),
)
