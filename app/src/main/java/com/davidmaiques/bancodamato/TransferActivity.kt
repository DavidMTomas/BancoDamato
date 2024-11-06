package com.davidmaiques.bancodamato

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davidmaiques.bancodamato.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // val nombre = intent.getStringExtra("usuario")
        //val usuario = UsuarioDatos.DATOS_USUARIOS.find { it.getNombre() == nombre } // encuentra le primer usuario
        //val cuentas = usuario?.getCuentas() // devuelve las cuentas del usuario

        //// Verificamos que 'cuentas' no sea nulo antes de proceder
        // //val lista = cuentas?.map { it.getNumeroCuenta() } ?: emptyList()
        //val lista = usuario?.getCuentas()!!.map { it.getNumeroCuenta() }

        val lista =
            arrayListOf<String>("1234 5678 9876 5432", "4321 8765 3456 7890", "5678 1234 8765 4321")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinner = binding.spCuenta
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Usando this@Activity para obtener el contexto
                Toast.makeText(this@TransferActivity, "Mensaje para cuenta", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}