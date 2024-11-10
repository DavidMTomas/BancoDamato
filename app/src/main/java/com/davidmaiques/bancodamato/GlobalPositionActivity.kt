package com.davidmaiques.bancodamato

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityPosicionGlogalBinding
import com.davidmaiques.bancodamato.pojo.Cliente
import com.davidmaiques.bancodamato.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity() {
    lateinit var binding: ActivityPosicionGlogalBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapterCuentas: AdapterCuentas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPosicionGlogalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Recuperar el valor pasado en el Intent y convertirlo a Cliente
        val cliente = intent.getSerializableExtra("cliente") as? Cliente

        //Accedemos a los metodos de los POJOS
        val mbo = MiBancoOperacional.getInstance(this)
        val cuentas = mbo?.getCuentas(cliente)


        adapterCuentas = AdapterCuentas(cuentas as ArrayList<Cuenta>)
        linearLayoutManager=LinearLayoutManager(this)

        binding.rcCuentas.apply {
            adapter=adapterCuentas
            layoutManager=linearLayoutManager
        }




    }


}
