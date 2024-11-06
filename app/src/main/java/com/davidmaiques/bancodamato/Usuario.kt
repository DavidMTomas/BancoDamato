package com.davidmaiques.bancodamato

data class Usuario(
    private val nombre: String,
    private val dni: String,
    private val password: String,
    private val cuentas: MutableList<Cuenta> = mutableListOf()
) {

    fun obtenerCuentasPorDni(dni: String): List<Cuenta>? {
        // Buscar el usuario por su DNI
        val usuario = UsuarioDatos.DATOS_USUARIOS.find { it.getDni() == dni } //

        // Si el usuario se encuentra, devolver sus cuentas
        return usuario?.getCuentas()
    }


    fun agregarCuenta(cuenta: Cuenta) {
        cuentas.add(cuenta)
    }

    fun getCuentas(): List<Cuenta> {
        return cuentas
    }

    fun getNombre(): String {
        return nombre
    }

    fun getDni(): String {
        return dni
    }

    fun getPassword(): String {
        return password
    }
}

class Cuenta(private val numero: String, private val saldo: Float) {

    fun getNumeroCuenta(): String {
        return numero
    }

    fun getSaldo(): Float {
        return saldo
    }
}


class UsuarioDatos {
    companion object {
        val DATOS_USUARIOS = arrayListOf<Usuario>(
            Usuario(
                "Juan", "123458A", "pass123", mutableListOf(
                    Cuenta("122", 1000f),
                    Cuenta("123", 1500f),
                    Cuenta("124", 1500f)
                )
            ),
            Usuario(
                "Ana", "234567B", "pass456", mutableListOf(
                    Cuenta("223", 1500f),
                    Cuenta("224", 2500f)
                )
            ),
            Usuario(
                "Luis", "345678C", "pass789", mutableListOf(
                    Cuenta("324", 2000f),
                    Cuenta("325", 3000f)
                )
            )
        )
    }
}
