package com.davidmaiques.bancodamato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.adapters.AdapterMovimientos
import com.davidmaiques.bancodamato.adapters.OnClickListener
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.DialogoMovimientoBinding
import com.davidmaiques.bancodamato.databinding.FragmentAccountsMovementsBinding
import com.davidmaiques.bancodamato.pojo.Cuenta
import com.davidmaiques.bancodamato.pojo.Movimiento
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val ARG_ACCOUNT = "cuenta"
class AccountsMovementsFragment : Fragment(),OnClickListener<Movimiento> {
    private lateinit var binding: FragmentAccountsMovementsBinding
    private var cuenta:Cuenta?=null

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movementAdapter: AdapterMovimientos
    private lateinit var dividerItemDecoration: DividerItemDecoration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cuenta = it.getSerializable(ARG_ACCOUNT) as Cuenta
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsMovementsBinding.inflate(inflater, container, false)
        val bancoOperacional = MiBancoOperacional.getInstance(context)
        val movements = bancoOperacional?.getMovimientos(cuenta) as ArrayList<Movimiento>

        linearLayoutManager = LinearLayoutManager(context)
        movementAdapter = AdapterMovimientos(movements, this)
        dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        binding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = movementAdapter
            addItemDecoration(dividerItemDecoration)
        }

        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(cuenta: Cuenta) =
            AccountsMovementsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ACCOUNT, cuenta)
                }
            }
    }

    override fun onClick(movimiento: Movimiento) {
        val dialogoBinding = DialogoMovimientoBinding.inflate(layoutInflater)
        val context = dialogoBinding.tvDialog.context

        val originAccount = "${movimiento.getCuentaOrigen()?.getBanco()}" +
                "-${movimiento.getCuentaOrigen()?.getSucursal()}" +
                "-${movimiento.getCuentaOrigen()?.getDc()}" +
                "-$movimiento.getCuentaOrigen()?.getNumeroCuenta()}"

        val destinationAccount = "${movimiento.getCuentaDestino()?.getBanco()}" +
                "-${movimiento.getCuentaDestino()?.getSucursal()}" +
                "-${movimiento.getCuentaDestino()?.getDc()}" +
                "-${movimiento.getCuentaDestino()?.getNumeroCuenta()}"

        dialogoBinding.tvDialog.text = getString(
            R.string.textoDialogo,
            movimiento.getId().toString(),
            movimiento.getTipo().toString(),
            movimiento.getFechaOperacion(),
            movimiento.getDescripcion(),
            movimiento.getImporte().toString(),
            originAccount,
            destinationAccount
        )

        MaterialAlertDialogBuilder(context)
            .setView(dialogoBinding.root)
            .show()
    }
}