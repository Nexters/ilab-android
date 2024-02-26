package com.nexters.ilab.android.core.common

import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ErrorHandlerActions {
    fun openServerErrorDialog()
    fun openNetworkErrorDialog()
}

fun handleException(exception: Throwable, actions: ErrorHandlerActions) {
    when (exception) {
        is HttpException -> {
            if (exception.code() == 500) {
                actions.openServerErrorDialog()
            } else {
                Timber.e(exception)
            }
        }
        is UnknownHostException -> {
            actions.openNetworkErrorDialog()
        }
        is SocketTimeoutException -> {
            actions.openServerErrorDialog()
        }
        else -> {
            Timber.e(exception)
        }
    }
}
