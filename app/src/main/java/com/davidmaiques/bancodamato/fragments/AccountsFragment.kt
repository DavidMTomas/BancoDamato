package com.davidmaiques.bancodamato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.adapters.AdapterCuentas
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityPosicionGlogalBinding
import com.davidmaiques.bancodamato.databinding.FragmentAccountsBinding
import com.davidmaiques.bancodamato.pojo.Cliente
import com.davidmaiques.bancodamato.pojo.Cuenta

private const val ARG_CLIENTE="cliente"

class AccountsFragment : Fragment(), com.davidmaiques.bancodamato.adapters.OnClickListener {
   lateinit var binding:FragmentAccountsBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapterCuentas: AdapterCuentas

    private lateinit var cliente:Cliente

    private lateinit var listener:AccountsListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cliente=it.getSerializable(ARG_CLIENTE) as Cliente
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //Accedemos a los metodos de los POJOS
        val mbo = MiBancoOperacional.getInstance(context)
        val cuentas = mbo?.getCuentas(cliente)

        adapterCuentas = AdapterCuentas(cuentas as ArrayList<Cuenta>)
        linearLayoutManager=LinearLayoutManager(context)

        binding.rcCuentas.apply {
            adapter=adapterCuentas
            layoutManager=linearLayoutManager
        }


        return binding.root
    }

    fun setCuentasListener(listener: AccountsListener) {
        this.listener=listener
    }

    companion object {
        @JvmStatic
        fun newInstance(c:Cliente) =
            AccountsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLIENTE, c)
                }
            }
    }

    override fun onClick(cuenta :Cuenta) {
        if(listener!=null){
            listener.onCuentaSeleccionada(cuenta)
        }
    }
}