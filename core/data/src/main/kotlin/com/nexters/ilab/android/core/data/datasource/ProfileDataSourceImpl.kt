package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.ProfileResponse
import com.nexters.ilab.android.core.data.service.ILabService
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val service: ILabService,
) : ProfileDataSource {
    override suspend fun getProfileList(): List<ProfileResponse> {
        return service.getProfileList()
    }
}
