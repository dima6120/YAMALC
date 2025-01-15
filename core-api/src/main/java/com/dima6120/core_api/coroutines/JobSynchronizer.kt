package com.dima6120.core_api.coroutines

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class JobSynchronizer<T> {

    private var activeJob: Deferred<T>? = null
    private val mutex = Mutex()

    suspend fun runOrJoin(block: suspend () -> T): T =
        coroutineScope {

            val deferredJob = mutex.withLock(owner = this@coroutineScope) {

                var activeJob: Deferred<T>? = activeJob

                if (activeJob == null || activeJob.isCancelled) {

                    Log.i(TAG, "Running new job")

                    activeJob = async {

                        block().also {
                            mutex.withLock(owner = this@coroutineScope) {
                                this@JobSynchronizer.activeJob = null
                            }
                        }
                    }

                    this@JobSynchronizer.activeJob = activeJob
                }

                activeJob
            }

            Log.i(TAG, "Joining to existing job")

            deferredJob.await()
        }

    companion object {

        private val TAG = JobSynchronizer::class.java.simpleName
    }
}