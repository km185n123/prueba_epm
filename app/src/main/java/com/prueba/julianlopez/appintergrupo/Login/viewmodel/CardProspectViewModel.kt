package com.prueba.julianlopez.appintergrupo.Login.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.RestApiAdapter
import com.prueba.julianlopez.appintergrupo.Login.bussines.networking.api.ProspectsServices
import com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.ProspectDataBaseHelper
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.LogRepository
import com.prueba.julianlopez.appintergrupo.Login.bussines.repository.ProspectRepository

class CardProspectViewModel(application: Application ) : AndroidViewModel(application) {

    private lateinit var logRepository: LogRepository
    private lateinit var prospectRepository:ProspectRepository

    init {
        logRepository = LogRepository(application.applicationContext)
       var prospectsServices = RestApiAdapter().getService(ProspectsServices::class.java)
       var  prospectDataBaseHelper = ProspectDataBaseHelper(application.applicationContext)
         prospectRepository = ProspectRepository(prospectsServices,prospectDataBaseHelper)
    }

    fun saveNewLog(log: Log){
        logRepository?.saveLog(log)
    }

    fun updateProspect(prospect: Prospect){
        prospectRepository!!.updateProspect(prospect)
    }
}