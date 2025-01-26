package com.davidmaiques.bancodamato.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "cajeros")
data class CajeroEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var direccion: String,
    var latitud: Double,
    var longitud: Double,
    var zoom: String
) : Serializable