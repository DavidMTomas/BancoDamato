package com.davidmaiques.bancodamato.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.adapters.AdapterCuentas
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityPosicionGlogalBinding
import com.davidmaiques.bancodamato.fragments.AccountsFragment
import com.davidmaiques.bancodamato.fragments.AccountsListener
import com.davidmaiques.bancodamato.pojo.Cliente
import com.davidmaiques.bancodamato.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(),AccountsListener {
    lateinit var binding: ActivityPosicionGlogalBinding
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



        // instancia de fragment
        val fragmentAccounts:AccountsFragment=AccountsFragment.newInstance(cliente as Cliente)

        // cargar fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.frPosicionGlobal, AccountsFragment()).commit()

        fragmentAccounts.setCuentasListener(this)

    }

    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        TODO("Not yet implemented")
    }


}
