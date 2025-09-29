package com.aeternam.notesappmvi.core

sealed class Result<out S, out F> {
    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Failure<out F>(val error: F) : Result<Nothing, F>()

    fun getOrNull(): S? {
        return when (this) {
            is Success -> data
            is Failure -> null
        }
    }

    fun getOrElse(defaultValue: @UnsafeVariance S): S {
        return when (this) {
            is Success -> data
            is Failure -> defaultValue
        }
    }
    inline fun <S, F, NewS> Result<S, F>.map(transform: (S) -> NewS): Result<NewS, F> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(error)
        }
    }
}