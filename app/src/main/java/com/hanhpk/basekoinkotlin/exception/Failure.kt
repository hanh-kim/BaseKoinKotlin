package com.hanhpk.basekoinkotlin.exception

/**
 * Manage errors, failures and exceptions
 */
sealed class Failure: Throwable() {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    //For additional error handling, extend this and use it
    abstract class FeatureFailure : Failure()
}