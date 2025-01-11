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


        val cuenta: Cuenta = intent.getSerializableExtra("cuenta") as Cuenta


        supportFragmentManager.beginTransaction().replace(
            R.id.frgGlobalPositionDetails,
            AccountsMovementsFragment.newInstance(cuenta, -1)
        ).commit()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_all -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frgGlobalPositionDetails,
                        AccountsMovementsFragment.newInstance(cuenta, 0)
                    ).commit()
                    true
                }

                R.id.nav_type_0 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frgGlobalPositionDetails,
                        AccountsMovementsFragment.newInstance(cuenta, 0)
                    ).commit()
                    true
                }

                R.id.nav_type_1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frgGlobalPositionDetails,
                        AccountsMovementsFragment.newInstance(cuenta, 1)
                    ).commit()
                    true
                }

                R.id.nav_type_2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frgGlobalPositionDetails,
                        AccountsMovementsFragment.newInstance(cuenta, 2)
                    ).commit()
                    true
                }

                else -> {
                    false
                }


            }
        }

//        accountsMovementsFragment = AccountsMovementsFragment.newInstance(cuenta)
//        fragmentManager = supportFragmentManager
//
//        fragmentManager.beginTransaction().replace(
//            R.id.frPosicionGlobal,
//            accountsMovementsFragment,
//            AccountsMovementsFragment::class.java.name
//        ).commit()


    }
}