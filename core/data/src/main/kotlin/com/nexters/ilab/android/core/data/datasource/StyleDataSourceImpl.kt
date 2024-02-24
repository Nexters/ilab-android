package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.StyleResponse
import com.nexters.ilab.android.core.data.service.ILabService
import javax.inject.Inject

class StyleDataSourceImpl @Inject constructor(
    private val service: ILabService,
) : StyleDataSource {
    override suspend fun getStyleList(): List<StyleResponse> {
        return service.getStyleList()
    }
}
