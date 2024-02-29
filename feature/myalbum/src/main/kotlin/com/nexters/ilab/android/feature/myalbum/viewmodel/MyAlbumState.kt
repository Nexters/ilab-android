package com.nexters.ilab.android.feature.myalbum.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MyAlbumState(
    val isLoading: Boolean = false,
    val styleName: String = "",
    val myAlbumImageUrlList: ImmutableList<String> = persistentListOf(),
)
