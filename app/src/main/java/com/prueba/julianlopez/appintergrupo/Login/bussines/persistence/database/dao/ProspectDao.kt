package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect

@Dao
interface ProspectDao:BaseDao<Prospect>{

    @Query("SELECT * FROM table_prospect WHERE updated <> 0 ")
    fun getLog(): List<Prospect>


    @Query("SELECT * FROM table_prospect ")
    fun getAll(): LiveData<List<Prospect>>


    @Query("SELECT * FROM table_prospect WHERE id = (SELECT MAX(ID) FROM table_prospect); ")
    fun getLastId():Int


    @Query("DELETE FROM table_prospect")
    fun nukeTable()

}