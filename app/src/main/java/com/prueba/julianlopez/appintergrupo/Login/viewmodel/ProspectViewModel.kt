package com.prueba.julianlopez.appintergrupo.Login.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.ProspectsServices
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.ProspectDataBaseHelper
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.ProspectRepository

class ProspectViewModel(aplication: Application): AndroidViewModel(aplication) {

    private var prospectRepository: ProspectRepository? = null


    init {
        var prospectsServices = RestApiAdapter().getService(ProspectsServices::class.java)
        var  prospectDataBaseHelper = ProspectDataBaseHelper(aplication.applicationContext)
        prospectRepository = ProspectRepository(prospectsServices,prospectDataBaseHelper)
    }

    fun getListProspect(token: String):LiveData<List<Prospect>>{
        return prospectRepository!!.getProspects(token)
    }



}