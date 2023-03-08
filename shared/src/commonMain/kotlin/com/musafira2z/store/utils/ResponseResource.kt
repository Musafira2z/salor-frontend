package com.musafira2z.store.utils

sealed interface ResponseResource<out R> {
    class Success<out T>(val data: T) : ResponseResource<T>
    class Error<out T>(val exception: Throwable, val data: T? = null) : ResponseResource<T>
    object Loading : ResponseResource<Nothing>
    object Idle : ResponseResource<Nothing>
}