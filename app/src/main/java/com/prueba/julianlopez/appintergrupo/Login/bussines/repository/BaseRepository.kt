package com.prueba.julianlopez.appintergrupo.Login.bussines.repository

import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter

open class BaseRepository {

    fun getRestApiAdapter():RestApiAdapter{
        return RestApiAdapter()
    }
}