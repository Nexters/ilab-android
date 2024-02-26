package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.ProfileResponse

interface ProfileDataSource {
    suspend fun getProfileList(): List<ProfileResponse>
}
