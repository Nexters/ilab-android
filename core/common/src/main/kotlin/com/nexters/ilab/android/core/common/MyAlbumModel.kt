package com.nexters.ilab.android.core.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyAlbumModel(
    val styleName: String = "",
    val myAlbumImageUrlList: List<String> = emptyList(),
): Parcelable
