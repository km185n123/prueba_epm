package com.prueba.julianlopez.appintergrupo.Login.bussines.networking

import android.annotation.SuppressLint
import com.prueba.julianlopez.appintergrupo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RestApiAdapter {

    companion object{

        @SuppressLint("CheckResult")
        fun <T> request(single: Single<Response<T>>, listener: ApiListener<T>) {
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ tResponse: Response<T> ->
                if (tResponse.isSuccessful) {
                    listener.onSuccess(tResponse.body())
                } else {
                    listener.onServerError(tResponse.errorBody()!!.toString())
                }
            }, {  listener.onError(it) })
        }
    }

    private var retrofit: Retrofit.Builder  = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(OkHttpClient().newBuilder().addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())


    fun <T> getService(tService: Class<T>): T {
        return retrofit!!.build().create(tService)
    }





}