package com.davidmaiques.bancodamato.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.bd.BancoApplication

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.davidmaiques.bancodamato.databinding.ActivityMapsBinding
import com.davidmaiques.bancodamato.entities.CajeroEntity
import com.google.android.gms.maps.model.LatLngBounds
import java.util.concurrent.LinkedBlockingQueue

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Lista de cajeros
        val queue = LinkedBlockingQueue<MutableList<CajeroEntity>>()
        Thread {
            val cajeros = BancoApplication.database.cajeroDao().getAllCajeros()
            queue.add(cajeros)
        }.start()

        // Marcadores  y zoom
        val cajeros = queue.take()
        if (cajeros.isNotEmpty()) {
            val builder = LatLngBounds.Builder() // Calcula are de tosdo los marcadores

            for (cajero in cajeros) {
                val latLng = LatLng(cajero.latitud, cajero.longitud)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("cajero: ${cajero.id}")
                        .snippet(cajero.direccion)
                )?.tag = cajero // asocia cajero a marcador
                builder.include(latLng) // añadir amrcador al are
            }

            // AJUSTAR ZOOM A ESPACIO QUE OCUPAN LOS MARCADORES
            val area = builder.build()
            val margen = 100
            val vista = CameraUpdateFactory.newLatLngBounds(area, margen)
            mMap.moveCamera(vista)

            // listener a marcadores
            mMap.setOnMarkerClickListener { it ->
                val cajero = it.tag as? CajeroEntity
                if (cajero != null) {
                    // zoom
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 18f))

                    // mostrar informacion
                    val message = """
                    ID: ${cajero.id}
                    Adreça: ${cajero.direccion}
                    Latitud: ${cajero.latitud}
                    Longitud: ${cajero.longitud}
                """.trimIndent()
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this,
                        "Error: No hay información sobre el cajero",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                true
            }
        } else {
            Toast.makeText(this, "No hay cajeros disponibles", Toast.LENGTH_SHORT).show()
        }
    }
}