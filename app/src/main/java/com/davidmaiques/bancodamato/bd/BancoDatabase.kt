package com.davidmaiques.bancodamato.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidmaiques.bancodamato.dao.CajeroDAO
import com.davidmaiques.bancodamato.entities.CajeroEntity

@Database(entities = arrayOf(CajeroEntity::class), version = 1)
abstract class BancoDatabase : RoomDatabase() {
    abstract fun cajeroDao(): CajeroDAO
}