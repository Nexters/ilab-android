package com.nexters.ilab.android.feature.setting.viewmodel

import android.content.Context
import android.content.pm.PackageInfo
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel(), ContainerHost<SettingState, SettingSideEffect> {

    override val container = container<SettingState, SettingSideEffect>(SettingState())

    fun openDeleteAccountDialog() = intent {
        reduce {
            state.copy(isDeleteAccountDialogVisible = true)
        }
    }

    fun dismissDeleteAccountDialog() = intent {
        reduce {
            state.copy(isDeleteAccountDialogVisible = false)
        }
    }

    fun getVersionInfo(context: Context): String {
        val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (packageInfo != null) {
            return packageInfo.versionName
        } else {
            return "버전 정보가 없습니다."
        }
    }
}
