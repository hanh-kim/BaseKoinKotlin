package com.hanhpk.basekoinkotlin.exception

/**
 * エラー、failure, exceptionを管理する
 */
sealed class Failure: Throwable() {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    //追加エラー処理はこれをExtendして使う
    abstract class FeatureFailure : Failure()
}