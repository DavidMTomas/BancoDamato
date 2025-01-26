package com.davidmaiques.bancodamato.bd

import android.app.Application
import androidx.room.Room


class BancoApplication : Application() {

    companion object {
        lateinit var database: BancoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            BancoDatabase::class.java,
            "banco_database")
            .build()
    }
}