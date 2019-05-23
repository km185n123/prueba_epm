package com.prueba.julianlopez.appintergrupo.Login.bussines.networking

interface ApiListener<T> {

    fun onSuccess(t: T?)

    fun onError(throwable: Throwable)

    fun onServerError(errorMessage: String)


}