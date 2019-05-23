package com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api

import com.prueba.julianlopez.appintergrupo.Login.Rest.ConstantsRestApi
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import rx.Single

interface ProspectsServices {

    @GET(ConstantsRestApi.END_PROSPECTS)
    fun pronspects(
            @Header("token") token:String

    ): Single<Response<List<Prospect>>>

}