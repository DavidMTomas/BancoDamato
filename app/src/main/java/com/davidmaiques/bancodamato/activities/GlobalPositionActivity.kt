package com.davidmaiques.bancodamato.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.databinding.ActivityPosicionGlogalBinding
import com.davidmaiques.bancodamato.fragments.AccountsFragment
import com.davidmaiques.bancodamato.fragments.AccountsListener
import com.davidmaiques.bancodamato.fragments.AccountsMovementsFragment
import com.davidmaiques.bancodamato.pojo.Cliente
import com.davidmaiques.bancodamato.pojo.Cuenta

class GlobalPositionActivity : AppCompatActivity(),AccountsListener {
    lateinit var binding: ActivityPosicionGlogalBinding
    lateinit var accountsFragment: AccountsFragment
   lateinit var fragmentManager: FragmentManager

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

        val cliente = intent.getSerializableExtra("cliente") as Cliente
        val activeFragment =
            supportFragmentManager.findFragmentById(binding.frPosicionGlobal.id)

        fragmentManager = supportFragmentManager
        accountsFragment = AccountsFragment.newInstance(cliente)


            fragmentManager.beginTransaction().add(
                binding.frPosicionGlobal.id,
                accountsFragment,
                AccountsFragment::class.java.name
            ).commit()
        accountsFragment.setCuentasListener(this)

    }
    //TODO Tema 6 act2
    override fun onCuentaSeleccionada(cuenta: Cuenta) {
        val tablet: Boolean = binding.frPosicionGlobal?.let {
            supportFragmentManager.findFragmentById( it.id ) } != null

        if (tablet) {
            val accountsMovementsFragment = AccountsMovementsFragment.newInstance(cuenta)
            supportFragmentManager.beginTransaction().replace(
                binding.frPosicionGlobal.id,
                accountsMovementsFragment,
                AccountsMovementsFragment::class.java.name
            ).commit()

        } else {
            val intent = Intent(this, GlobalPositionDetailsActivity::class.java)
            intent.putExtra("cuenta", cuenta)
            startActivity(intent)
        }
    }
}