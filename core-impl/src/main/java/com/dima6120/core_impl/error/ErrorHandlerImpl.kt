package com.dima6120.core_impl.error

import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.error.YamalcError
import com.dima6120.core_api.model.UseCaseResult
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(): ErrorHandler {

    override suspend fun <T> run(block: suspend () -> T): UseCaseResult<T> =
        try {
            UseCaseResult.success(block())
        } catch (e: YamalcError) {
            UseCaseResult.error(e)
        }
}