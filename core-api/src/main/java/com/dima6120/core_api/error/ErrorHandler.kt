package com.dima6120.core_api.error

import com.dima6120.core_api.model.UseCaseResult

interface ErrorHandler {

    suspend fun <T> run(block: suspend () -> T): UseCaseResult<T>
}