package com.davidmaiques.bancodamato.fragments

import com.davidmaiques.bancodamato.pojo.Cuenta

interface AccountsListener {
    fun onCuentaSeleccionada(cuenta: Cuenta)
}