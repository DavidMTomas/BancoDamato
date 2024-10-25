package com.davidmaiques.bancodamato

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davidmaiques.bancodamato.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // metodo para acceder a los atributos del xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        // setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val usuario = intent.getStringExtra("usuario") ?: getString(R.string.error_usuario)
        binding.txtvUsuario.text = usuario




        binding.btnCambiarContrasenya.setOnClickListener {

            val modificarPass = layoutInflater.inflate(R.layout.modificar_contrasenya, null)

            val dialog = AlertDialog.Builder(this)
                .setView(modificarPass)
                .setTitle(getString(R.string.titulo_modificar_contraseña))
                .create()

            val btnAceptar = modificarPass.findViewById<MaterialButton>(R.id.btnEntrar)
            val btnCancelar = modificarPass.findViewById<MaterialButton>(R.id.btnSalir)
            var contrasenya1 = modificarPass.findViewById<TextInputEditText>(R.id.txtinModificarContrasenya1)
            var contrasenya2 = modificarPass.findViewById<TextInputEditText>(R.id.txtinModificarContrasenya2)


            btnAceptar.isEnabled=false
            btnAceptar.background.setTint(Color.DKGRAY)


            contrasenya2.setOnFocusChangeListener { _, _ ->
                btnAceptar.isEnabled = contrasenya1.text.toString().isNotEmpty() && contrasenya2.text.toString().isNotEmpty()
                btnAceptar.background.setTint(getColor(R.color.botonesAccesos))
            }


            btnAceptar.setOnClickListener {
              if(contrasenya1.text.toString()!=contrasenya2.text.toString()){
                  Toast.makeText(this, getString(R.string.toas_contraseñas_distintas), Toast.LENGTH_SHORT).show()              }
                else{
                  Toast.makeText(this, getString(R.string.contraseñas_iguales), Toast.LENGTH_SHORT).show()
                  dialog.dismiss()
              }
            }

            btnCancelar.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }



        binding.btnSalir.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }



    }
}