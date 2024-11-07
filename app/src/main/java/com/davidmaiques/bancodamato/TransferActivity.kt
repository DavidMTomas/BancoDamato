package com.davidmaiques.bancodamato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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
            arrayListOf<String>(
                "ES1234 5678 98765432",
                "ES4321 8765 34567890",
                "ES5678 1234 8764321"
            )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spCuentaOrigen = binding.spCuentaOrigen
        spCuentaOrigen.adapter = adapter


        spCuentaOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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


        val divisas = listOf("€", "$", "l", "Y", "B")
        val adapterDivisas = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)
        adapterDivisas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spDivisa = binding.spDivisa
        spDivisa.adapter = adapterDivisas


        var cuentaPropia = binding.rbCuentaPropia
        var cuentaAjena = binding.rbCuentaAjena
        var cuentaDestino = binding.spCuentaDestino
        cuentaDestino.adapter = adapter
        var etCuentaDestino = binding.etCuentaDestino


        binding.rgCuenta.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                cuentaPropia.id -> {
                    // pasar spinner
                    cuentaDestino.visibility = View.VISIBLE
                    etCuentaDestino.visibility = View.GONE
                }

                cuentaAjena.id -> {
                    // pasar textview
                    etCuentaDestino.visibility = View.VISIBLE
                    binding.spCuentaDestino.visibility = View.GONE
                }

            }
        }// view group


        binding.btnEnviar.setOnClickListener {
            val justificante = if (binding.cbEnviar.isChecked)
                getString(R.string.enviar_justificante) else ""

            // Obtener el texto real de los recursos
            val textoDestino = if(cuentaPropia.isChecked)
                getString(R.string.propia)
            else
                getString(R.string.ajena)

            var texto = "${getString(R.string.Cuenta_origen)} \n" +
                    "${spCuentaOrigen.selectedItem.toString()} \n" +
                    "${getString(R.string.A_cuenta)} $textoDestino \n" +
                    "${cuentaDestino.selectedItem.toString()} \n" +
                    "${getString(R.string.Importe)} ${binding.etCantidad.text.toString()} \n" +
                    "$justificante"

            // Inflar el layout personalizado para el Toast
            val customView = layoutInflater.inflate(R.layout.toast_custom, null)
            val textView = customView.findViewById<TextView>(R.id.tv_toast)
            textView.text = texto

            // Crear el Toast con el layout personalizado
            val customToast = Toast(applicationContext)
            customToast.duration = Toast.LENGTH_LONG
            customToast.view = customView

            // Mostrar el Toast
            customToast.show()
        }

        binding.btnCancelar.setOnClickListener {
            binding.etCantidad.setText("")
            binding.cbEnviar.isChecked = false
            binding.etCuentaDestino.setText("")
        }


        // on create
    }

}