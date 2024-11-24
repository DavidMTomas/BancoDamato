package com.davidmaiques.bancodamato.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.databinding.ItemCuentasBinding
import com.davidmaiques.bancodamato.pojo.Cuenta

// TODO TEMA 5 act. 3
class AdapterCuentas(private var lista: ArrayList<Cuenta>, private val listener: OnClickListener<Cuenta>) :
    RecyclerView.Adapter<AdapterCuentas.ViewHolder>() {
    lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCuentasBinding.bind(view)
        fun render(cuenta: Cuenta) {
            binding.tvCuentas.text = cuenta.getNumeroCuenta()
            binding.tvSaldo.text = cuenta.getSaldoActual().toString()
            setListener(cuenta)
        }

        private fun setListener(cuenta: Cuenta) {
                binding.root.setOnClickListener {
                    listener.onClick(cuenta)
                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_cuentas, parent, false)
        )
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.render(lista[position])
    }
}