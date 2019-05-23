package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log

@Dao
interface LogDao:BaseDao<Log> {

    @Query("SELECT * FROM table_logs ")
    fun getAll(): LiveData<List<Log>>

    @Query("SELECT * FROM table_logs WHERE id = (SELECT MAX(ID) FROM table_logs); ")
    fun getLastId():Int

    @Query("DELETE FROM table_logs")
    fun nukeTable()

}