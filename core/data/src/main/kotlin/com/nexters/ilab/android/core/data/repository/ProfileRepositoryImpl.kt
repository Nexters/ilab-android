package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.ProfileDataSource
import com.nexters.ilab.android.core.data.mapper.toEntity
import com.nexters.ilab.android.core.data.util.runSuspendCatching
import com.nexters.ilab.android.core.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val dataSource: ProfileDataSource,
) : ProfileRepository {
    override suspend fun getProfileList() = runSuspendCatching {
        dataSource.getProfileList().map { it.toEntity() }
    }
}
