@file:Suppress("UNCHECKED_CAST")
package com.android.test.testandroid

open class Result<out R> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any? = null
){

    val isSuccess: Boolean = value !is Throwable
    val isFailure: Boolean = value is Throwable

    fun exceptionOrNull() : Throwable? = when {
        isFailure -> value as Throwable?
        else -> null
    }

    companion object {
        fun <T> success(value: T) : Result<T> = Result(value)
        fun <T> failure(throwable: Throwable) : Result<T> = Result(throwable)
    }


}

fun <T,R> Result<T>.map(transform: (value: T) -> R) : Result<R> = when {
    isSuccess -> result { transform(this.value as T) }
    else -> Result(this.value)
}

fun <T, R> Result<T?>.mapNotNull(transform: (value: T) -> R) : Result<R> =
    this.map{ transform(it ?: throw Throwable())}

fun <T> Result<T?>.notNull() : Result<T> =
    this.map { it ?: throw Throwable() }

inline fun <T> result(block: () -> T) : Result<T> = try {
    Result.success(block())
}catch (e: Throwable){
    Result.failure(e)
}

fun <T> Result<T>.onSuccess(block: (T) -> Unit) : Result<T> {
    if(isSuccess) block(value as T)
    return this
}

fun <T> Result<T>.onFailure(block: (Throwable) -> Unit) : Result<T> {
    exceptionOrNull()?.let{ block(it) }
    return this
}