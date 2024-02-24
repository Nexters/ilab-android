package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.StyleResponse

interface StyleDataSource {
    suspend fun getStyleList(): List<StyleResponse>
}
