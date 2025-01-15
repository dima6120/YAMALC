package com.dima6120.core_impl.error

import android.util.Log
import com.dima6120.core_api.error.YamalcError
import java.io.IOException
import javax.inject.Inject

class InternalErrorHandlerImpl @Inject constructor(): InternalErrorHandler {

    override fun handle(throwable: Throwable): YamalcError {
        Log.e(TAG, "Internal Error", throwable)

        return when (throwable) {
            is IOException -> YamalcError.Network(throwable)
            else -> YamalcError.Unknown(throwable)
        }
    }

    override suspend fun <T> run(block: suspend () -> T): T =
        runCatching { block() }
            .fold(
                onSuccess = { it },
                onFailure = { throw handle(it) }
            )

    companion object {

        private val TAG = InternalErrorHandlerImpl::class.java.simpleName
    }
}