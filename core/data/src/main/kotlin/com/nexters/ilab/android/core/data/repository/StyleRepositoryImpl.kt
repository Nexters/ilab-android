package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.StyleDataSource
import com.nexters.ilab.android.core.data.mapper.toEntity
import com.nexters.ilab.android.core.domain.repository.StyleRepository
import com.nexters.ilab.android.core.data.util.runSuspendCatching
import javax.inject.Inject

class StyleRepositoryImpl @Inject constructor(
    private val dataSource: StyleDataSource,
) : StyleRepository {
    override suspend fun getStyleList() = runSuspendCatching {
        dataSource.getStyleList().map { it.toEntity() }
    }
}
