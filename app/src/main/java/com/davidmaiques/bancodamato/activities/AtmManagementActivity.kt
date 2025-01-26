package com.davidmaiques.bancodamato.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.bd.BancoApplication
import com.davidmaiques.bancodamato.databinding.ActivityAtmManagementBinding
import com.davidmaiques.bancodamato.entities.CajeroEntity
import com.davidmaiques.bancodamato.pojo.Cliente
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.LinkedBlockingQueue

class AtmManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtmManagementBinding
    private var cajeroEntity: CajeroEntity? = null
    private var cliente: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cajeroEntity = intent.getSerializableExtra("cajero") as CajeroEntity?

        cliente = intent.getSerializableExtra("cliente") as? Cliente


        // si el usuario es admin
        if (cliente != null && cliente!!.getNif().equals("11111111A", ignoreCase = true)) {
            if (cajeroEntity == null) {
                binding.apply {
                    tvTitle.text = getString(R.string.createAtm)
                    btnSave.isVisible = true
                    btnCancel.isVisible = true
                    btnUpdate.isVisible = false
                    btnDelete.isVisible = false
                }
            } else {
                binding.apply {
                    tvTitle.text = getString(R.string.modifyAtm)
                    btnSave.isVisible = false
                    btnCancel.isVisible = false
                    btnUpdate.isVisible = true
                    btnDelete.isVisible = true
                    tilAddress.editText?.setText(cajeroEntity!!.direccion)
                    tilLatitude.editText?.setText(cajeroEntity!!.latitud.toString())
                    tilLongitude.editText?.setText(cajeroEntity!!.longitud.toString())
                }
            }

            // si el usuario no es admin
        } else {
            binding.apply {
                tvTitle.text = getString(R.string.placeOf)
                btnSave.isVisible = false
                btnCancel.isVisible = false
                btnUpdate.isVisible = false
                btnDelete.isVisible = false
            }
            Toast.makeText(this, "Los botones no son visibles para este usuario", Toast.LENGTH_LONG)
                .show()
        }



        binding.btnDelete.setOnClickListener {

            val queue = LinkedBlockingQueue<Int>()
            Thread {
                val id = BancoApplication.database.cajeroDao().deleteCajero(cajeroEntity!!)
                queue.add(id)
            }.start()

            if (queue.take() > 0) {
                Toast.makeText(
                    this,
                    getText(R.string.deletedSuccessfully),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }


        binding.btnUpdate.setOnClickListener {
            cajeroEntity!!.apply {
                try {
                    direccion = binding.tilAddress.editText?.text.toString()
                    latitud = binding.tilLatitude.editText?.text.toString().toDouble()
                    longitud = binding.tilLongitude.editText?.text.toString().toDouble()

                } catch (e: NumberFormatException) {
                    Snackbar.make(
                        binding.root,
                        getText(R.string.numberException),
                        Snackbar.LENGTH_SHORT
                    ).setAnchorView(R.id.btnUpdate).show()
                    return@setOnClickListener
                }
            }

            val queue = LinkedBlockingQueue<Int>()
            Thread {
                val id = BancoApplication.database.cajeroDao().updateCajero(cajeroEntity!!)
                queue.add(id)
            }.start()

            if (queue.take() > 0) {
                Toast.makeText(
                    this,
                    getText(R.string.updatedSuccessfully),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }


        binding.btnSave.setOnClickListener {
            val cajero: CajeroEntity
            try {
                cajero = CajeroEntity(
                    direccion = binding.tilAddress.editText?.text.toString(),
                    latitud = binding.tilLatitude.editText?.text.toString().toDouble(),
                    longitud = binding.tilLongitude.editText?.text.toString().toDouble(),
                    zoom = ""
                )
            } catch (e: NumberFormatException) {
                Snackbar.make(
                    binding.root,
                    getText(R.string.numberException),
                    Snackbar.LENGTH_SHORT
                ).setAnchorView(R.id.btnSave).show()
                return@setOnClickListener
            }

            val queue = LinkedBlockingQueue<Long>()
            Thread {
                val id = BancoApplication.database.cajeroDao().addCajero(cajero)
                queue.add(id)
            }.start()

            if (queue.take() > 0)
                Toast.makeText(
                    this,
                    getText(R.string.createdSuccessfully),
                    Toast.LENGTH_SHORT
                ).show()

            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
