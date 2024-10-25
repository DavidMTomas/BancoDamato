package com.davidmaiques.bancodamato

import android.app.Activity
import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davidmaiques.bancodamato.databinding.ActivityLoginBinding
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
        binding.btnEntrar.setOnClickListener {
            if (!usuario.isNullOrEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }

        }


        binding.btnSalir.setOnClickListener{
            startActivity(Intent(this,WelcomeActivity::class.java))
        }


    }
}