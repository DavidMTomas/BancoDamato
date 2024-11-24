package com.davidmaiques.bancodamato.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.adapters.AdapterMovimientos
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityMovementsBinding
import com.davidmaiques.bancodamato.pojo.Cliente
import com.davidmaiques.bancodamato.pojo.Cuenta
import com.davidmaiques.bancodamato.pojo.Movimiento

class MovementsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovementsBinding
    lateinit var adapterMovimientos: AdapterMovimientos
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var itemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // CAPTURAMOS EL INTENT
        val cliente = intent.getSerializableExtra("cliente") as Cliente

        // CREAMOS INSTANCIA PARA ACCEDER AL LISTADO DE CUENTAS DEL CLIENTE
        val mbo = MiBancoOperacional.getInstance(this)
        val listado: ArrayList<Cuenta> = mbo?.getCuentas(cliente) as ArrayList<Cuenta>

        // Guardamos el numero de cada cuenta del cliente para mostrarlo en el spinner
        val numerosCuentas = listado.map { it.getNumeroCuenta() }

        // metemos el listado en el spinner
        val adapterSpinner =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, numerosCuentas)

        var spinner = binding.spCuentas
        spinner.adapter = adapterSpinner


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // a por el recivcler view
                //TODO TEMA 5 activitat 3
                //Seleccion
                val cuentaSeleccionada = listado[position]
                //Metodos de POJO
                val mbo = MiBancoOperacional.getInstance(this@MovementsActivity)
                //Vector de movimientso
                val movimientosCuenta =
                    mbo?.getMovimientos(cuentaSeleccionada) as ArrayList<Movimiento>

                //PAsar el vector al adapter
                adapterMovimientos = AdapterMovimientos(movimientosCuenta)
                linearLayoutManager = LinearLayoutManager(this@MovementsActivity)
                itemDecoration = DividerItemDecoration(this@MovementsActivity, 1)

                binding.rcMovimientos.apply {
                    adapter = adapterMovimientos
                    layoutManager = linearLayoutManager
                    addItemDecoration(itemDecoration)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
}