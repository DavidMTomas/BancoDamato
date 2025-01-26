package com.davidmaiques.bancodamato.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.davidmaiques.bancodamato.entities.CajeroEntity


@Dao
interface CajeroDAO {

    @Query("SELECT * FROM cajeros")
    fun getAllCajeros(): MutableList<CajeroEntity>

    @Insert
    fun insertAll(cajeroEntityList: List<CajeroEntity>)

    @Insert
    fun addCajero(cajeroEntity: CajeroEntity): Long

    @Update
    fun updateCajero(cajeroEntity: CajeroEntity): Int

    @Delete
    fun deleteCajero(cajeroEntity: CajeroEntity): Int
}