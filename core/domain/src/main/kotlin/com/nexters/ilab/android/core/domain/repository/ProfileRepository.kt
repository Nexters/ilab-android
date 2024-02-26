package com.nexters.ilab.android.core.domain.repository

import com.nexters.ilab.android.core.domain.entity.ProfileEntity

interface ProfileRepository {
    suspend fun getProfileList(): Result<List<ProfileEntity>>
}
