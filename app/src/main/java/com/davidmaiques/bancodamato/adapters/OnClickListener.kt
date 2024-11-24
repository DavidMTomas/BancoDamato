package com.davidmaiques.bancodamato.adapters

import com.davidmaiques.bancodamato.pojo.Cuenta

interface OnClickListener<T> {
    fun onClick(cuenta: T)
}