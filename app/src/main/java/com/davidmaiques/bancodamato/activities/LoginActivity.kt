package com.davidmaiques.bancodamato.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityLoginBinding
import com.davidmaiques.bancodamato.pojo.Cliente
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    //Añadir en el build.gradle.kts (Module :app)
    // buildFeatures {viewBinding = true}

    // declaramos la variable binding de tipo activityloginBinding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // metodo para acceder a los atributos del xml
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        enableEdgeToEdge()
        //setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Cerrar aplicacion
        binding.btnSalir.setOnClickListener {
            (this as Activity).finish()
        }

        var usuario: String? = null

        binding.entradaUsuario.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                usuario = binding.entradaUsuario.text.toString()
                if (usuario.isNullOrEmpty()) {
                    Snackbar.make(v, getString(R.string.usuario_vacio), Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.txtfUsuario).show()
                }
            }
        }



        binding.passwordUsuario.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val password = binding.passwordUsuario.text.toString()
                if (password.isEmpty()) {
                    Snackbar.make(v, getString(R.string.error_contrasenya), Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.txtfPassword).show()
                }
            }

        }

        //boton entrar, lanza la otra pantalla si el usuario es correcto
        // Botón de login, lanza la otra pantalla si el usuario es correcto
        binding.btnEntrar.setOnClickListener {
            // Instancia de conexion a al Base de Datos
            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

            // Capturamos info de login y la guardamos en las propiedades del cliente: nif / password
            var cliente = Cliente()
            cliente.setNif(binding.entradaUsuario.text.toString().uppercase())
            cliente.setClaveSeguridad(binding.passwordUsuario.text.toString())

            // Accedemos TODOS los datos del cliente si existe y lo pasamos a la instancia clientelogeado
            val clienteLogeado = mbo?.login(cliente)?: -1

            if (clienteLogeado == -1) {
                Toast.makeText(this, "El cliente no existe en la base de datos", Toast.LENGTH_LONG).show()
            } else {
                // Pasa el objeto Cliente completo (que es Serializable)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cliente", clienteLogeado)  // Pasa el objeto Cliente directamente
                startActivity(intent)
            }
        }


        binding.btnSalir.setOnClickListener{
            startActivity(Intent(this, WelcomeActivity::class.java))
        }


    }
}