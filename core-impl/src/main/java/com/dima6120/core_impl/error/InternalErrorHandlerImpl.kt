package com.dima6120.core_impl.error

import android.util.Log
import com.dima6120.core_api.error.YamalcError
import javax.inject.Inject

class InternalErrorHandlerImpl @Inject constructor(): InternalErrorHandler {

    override fun handle(throwable: Throwable): YamalcError {
        Log.e(TAG, throwable.toString())

        return YamalcError.Unknown(throwable) // TODO
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