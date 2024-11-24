package com.davidmaiques.bancodamato.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.databinding.ActivityGlobalPositionDetailsBinding
import com.davidmaiques.bancodamato.fragments.AccountsMovementsFragment
import com.davidmaiques.bancodamato.pojo.Cuenta

class GlobalPositionDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlobalPositionDetailsBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var accountsMovementsFragment: AccountsMovementsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_global_position_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cuenta: Cuenta = intent.getSerializableExtra("cuenta") as Cuenta

        accountsMovementsFragment = AccountsMovementsFragment.newInstance(cuenta)
        fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(
            R.id.frPosicionGlobal,
            accountsMovementsFragment,
            AccountsMovementsFragment::class.java.name
        ).commit()


    }
}