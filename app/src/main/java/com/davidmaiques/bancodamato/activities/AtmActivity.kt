package com.davidmaiques.bancodamato.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.adapters.AtmAdapter
import com.davidmaiques.bancodamato.adapters.OnClickAtmListener
import com.davidmaiques.bancodamato.bd.BancoApplication
import com.davidmaiques.bancodamato.databinding.ActivityAtmBinding
import com.davidmaiques.bancodamato.entities.CajeroEntity
import com.davidmaiques.bancodamato.pojo.Cliente
import java.util.concurrent.LinkedBlockingQueue

class AtmActivity : AppCompatActivity(), OnClickAtmListener {
    private lateinit var binding: ActivityAtmBinding
    private lateinit var atmAdapter: AtmAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var cliente: Cliente? = null // Cambiar a tipo nullable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener cliente del intent dentro de onCreate
        cliente = intent.getSerializableExtra("cliente") as? Cliente


    }


    override fun onStart() {
        super.onStart()
        initUI()
    }

    private fun initUI() {
        initRecycler()
        // OCULTAR BOTON SI ADMIN
        if (cliente != null && cliente!!.getNif().equals("11111111A", ignoreCase = true)){
            Toast.makeText(this, "TIENES ACCESO COMO ADMIN 11111111A",Toast.LENGTH_LONG).show()
            binding.fab.setImageResource(R.drawable.baseline_lock_open_24)

            binding.fab.setOnClickListener {
                val intent = Intent(this, AtmManagementActivity::class.java)
                intent.putExtra("cliente", cliente)
                startActivity(intent)
            }

        }else{
            binding.fab.setImageResource(R.drawable.baseline_lock_outline_24)
            Toast.makeText(this, "SOLO EL ADMIN 11111111A, TIENE ACCESO AL BOTON",Toast.LENGTH_LONG).show()
            binding.fab.setOnClickListener {
                Toast.makeText(this, "No tienes acceso..",Toast.LENGTH_LONG).show()
            }
        }



    }

    fun initRecycler() {
        val queue = LinkedBlockingQueue<MutableList<CajeroEntity>>()
        Thread {
            // Si recupera algun cajero de la base de datos los mostramos en el recycler
            val cajeroEntityList = BancoApplication.database.cajeroDao().getAllCajeros()
            queue.add(cajeroEntityList)
        }.start()

        // Si no recupera nada de la base de datos, insertamos los cajeros por defecto
        // y los mostramos en el recycler
        var cajeroList = queue.take()
        if (cajeroList.isNullOrEmpty()) {
            cajeroList = getCajerosDefault() as MutableList<CajeroEntity>
            Thread {
                BancoApplication.database.cajeroDao().insertAll(cajeroList)
            }.start()
        }

        atmAdapter = AtmAdapter(cajeroList, this)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recycler.apply {
            adapter = atmAdapter
            layoutManager = linearLayoutManager
        }

    }

    override fun onClick(cajeroEntity: CajeroEntity) {
        val intent = Intent(this, AtmManagementActivity::class.java)
        intent.putExtra("cajero", cajeroEntity)
        intent.putExtra("cliente", cliente)
        startActivity(intent)
    }

    fun getCajerosDefault(): List<CajeroEntity> {
        return listOf(
            CajeroEntity(
                1,
                "Carrer del Clariano, 1, 46021 Valencia, Valencia, España",
                39.47600769999999,
                -0.3524475000000393,
                ""
            ),
            CajeroEntity(
                2,
                "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España",
                39.4710366,
                -0.3547525000000178,
                ""
            ),
            CajeroEntity(
                3, "Av. del Port, 237, 46011 València, Valencia, España",
                39.46161999999999, -0.3376299999999901, ""
            ),
            CajeroEntity(
                4,
                "Carrer del Batxiller, 6, 46010 València, Valencia, España",
                39.4826729,
                -0.3639118999999482,
                ""
            ),
            CajeroEntity(
                5,
                "Av. del Regne de València, 2, 46005 València, Valencia, España",
                39.4647669,
                -0.3732760000000326,
                ""
            )
        )
    }
}