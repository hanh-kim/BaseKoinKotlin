package com.hanhpk.basekoinkotlin.interactors

import com.hanhpk.basekoinkotlin.exception.Failure
import kotlinx.coroutines.*

//機能説明
//NET と NATIVE のJsonDataを切り替えする時に使用
//ただし戻り値は一つのDataタイプしかないのでNET と NATIVEのJsonDataを一つのClassに継承するつもりです
abstract class UseCaseExpand<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params,jsonType: JsonType): Result<Failure, Type>

    operator fun invoke(
        params: Params,
        jsonType: JsonType,
        scope: CoroutineScope = GlobalScope,
        onResult: (Result<Failure, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params, jsonType)
            }
            onResult(deferred.await())
        }
    }

    class None
    enum class JsonType(type:String){
        NET("NET"),
        NATIVE("NATIVE")
    }
}
