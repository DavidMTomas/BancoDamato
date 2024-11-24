package com.davidmaiques.bancodamato.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.databinding.ItemMovimientosBinding
import com.davidmaiques.bancodamato.pojo.Movimiento

// TODO tema 5 act 3
class AdapterMovimientos(private var lista: ArrayList<Movimiento>, private val listener: OnClickListener<Movimiento>) :
    RecyclerView.Adapter<AdapterMovimientos.ViewHolder>() {
    lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMovimientosBinding.bind(view)
        fun render(movimiento: Movimiento) {
            binding.tvDescripcion.text = movimiento.getDescripcion()
            binding.tvFecha.text = movimiento.getFechaOperacion().toString()
            binding.tvImporte.text = movimiento.getImporte().toString()
            setListener(movimiento)
        }

        private fun setListener(movimiento: Movimiento) {
                binding.root.setOnClickListener {
                    listener.onClick(movimiento)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movimientos, parent, false)
        )
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.render(lista[position])
    }
}