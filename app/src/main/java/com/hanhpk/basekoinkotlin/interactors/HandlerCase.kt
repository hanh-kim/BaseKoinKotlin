package com.hanhpk.basekoinkotlin.interactors

import com.hanhpk.basekoinkotlin.exception.Failure
import kotlinx.coroutines.*

abstract class HandlerCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Result<Failure, Type>

    @OptIn(DelicateCoroutinesApi::class)
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Result<Failure, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}