package com.nexters.ilab.android.feature.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Suppress("unused")
    fun toggleDarkTheme(isDarkTheme: Boolean) {
        //
    }
}
