package com.hanhpk.basekoinkotlin.interactors

/**
 * hanhpk.dev
 */
sealed class Result<out Failure : Throwable, out Success> {
    data class Error<out Failure : Throwable>(val error: Failure) : Result<Failure, Nothing>()
    data class Success<out Success>(val data: Success) : Result<Nothing, Success>()

    /**
     * @see onFailure
     * @see onSuccess
     */
    inline fun <R> fold(onFailure: (Failure) -> R, onSuccess: (Success) -> R): R =
        when (this) {
            is Error -> onFailure(error)
            is Result.Success -> onSuccess(data)
        }
}