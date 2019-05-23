package com.prueba.julianlopez.appintergrupo.Login.bussines.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.ApiListener
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.ProspectsServices
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.ProspectDataBaseHelper
import retrofit2.Response
import rx.Single

class ProspectRepository(prospectsServices: ProspectsServices,prospectDataBaseHelper: ProspectDataBaseHelper): BaseRepository() {

    private var prospectsServices: ProspectsServices? = null
    private var prospectDataBaseHelper: ProspectDataBaseHelper? = null
    init {

        this.prospectsServices = prospectsServices
        this.prospectDataBaseHelper = prospectDataBaseHelper
    }


    fun getProspects(token:String):LiveData<List<Prospect>>{
        val result: Single<Response<List<Prospect>>> = prospectsServices!!.pronspects(token)
       RestApiAdapter.request(result, object : ApiListener<List<Prospect>> {
            override fun onSuccess(t: List<Prospect>?) {

                if (t != null) {
                    prospectDataBaseHelper!!.cleanData()
                    prospectDataBaseHelper!!.save(t)
                }
            }

            override fun onError(throwable: Throwable) {

                Log.e("Repository", throwable.message)
            }

            override fun onServerError(errorMessage: String) {

                Log.e("Repository", errorMessage)

            }

        })


        return prospectDataBaseHelper!!.getAll()

    }

    fun updateProspect(prospect: Prospect){
        prospectDataBaseHelper!!.update(prospect)
    }
}