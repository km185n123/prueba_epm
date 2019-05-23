package com.prueba.julianlopez.appintergrupo.Login.bussines.persistence.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * BaseDao
 */

public interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    void insert(T t);

    @Insert(onConflict = REPLACE)
    void insert(List<T> t);

    @Delete
    void delete(T t);

    @Update
    void update(T t);
}
