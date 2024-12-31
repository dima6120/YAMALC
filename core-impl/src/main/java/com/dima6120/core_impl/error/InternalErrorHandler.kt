package com.dima6120.core_impl.error

import com.dima6120.core_api.error.YamalcError

interface InternalErrorHandler {

    fun handle(throwable: Throwable): YamalcError

    suspend fun <T> run(block: suspend () -> T): T
}