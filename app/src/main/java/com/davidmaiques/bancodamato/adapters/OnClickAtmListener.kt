package com.davidmaiques.bancodamato.adapters

import com.davidmaiques.bancodamato.entities.CajeroEntity

interface OnClickAtmListener {
    fun onClick(cajeroEntity: CajeroEntity)
}