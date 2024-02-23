package com.nexters.ilab.android.feature.setting.viewmodel

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
}
