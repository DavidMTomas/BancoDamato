package com.davidmaiques.bancodamato.activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.davidmaiques.bancodamato.R
import com.davidmaiques.bancodamato.bd.MiBancoOperacional
import com.davidmaiques.bancodamato.databinding.ActivityMainBinding
import com.davidmaiques.bancodamato.pojo.Cliente
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
   private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // metodo para acceder a los atributos del xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO mensaje de bienvenida
         cliente = intent.getSerializableExtra("cliente") as Cliente
         binding.txtvUsuario.text = "$cliente.getNombre()"


        //TODO tema 7 activitat1
        drawerLayout = binding.drawerLayout
        val appBar= binding.btnAppBar
        val navigationView= binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, appBar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        // TODO posicion global Tema 5 activitat 3
        binding.btCuentasGlobal?.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("cliente", cliente)
            startActivity(intent)
        }

        // TODO Tema 5 activitat 3
        binding.btnMovimientos.setOnClickListener {
            val intent= Intent(this, MovementsActivity::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
        }

        binding.btnCambiarContrasenya.setOnClickListener {

            val modificarPass = layoutInflater.inflate(R.layout.modificar_contrasenya, null)

            val dialog = AlertDialog.Builder(this)
                .setView(modificarPass)
                .setTitle(getString(R.string.titulo_modificar_contraseña))
                .create()

            val btnAceptar = modificarPass.findViewById<MaterialButton>(R.id.btnEntrar)
            val btnCancelar = modificarPass.findViewById<MaterialButton>(R.id.btnSalir)
            var contrasenya1 =
                modificarPass.findViewById<TextInputEditText>(R.id.txtinModificarContrasenya1)
            var contrasenya2 =
                modificarPass.findViewById<TextInputEditText>(R.id.txtinModificarContrasenya2)


            btnAceptar.isEnabled = false
            btnAceptar.background.setTint(Color.DKGRAY)


            contrasenya2.setOnFocusChangeListener { _, _ ->
                btnAceptar.isEnabled =
                    contrasenya1.text.toString().isNotEmpty() && contrasenya2.text.toString()
                        .isNotEmpty()
                btnAceptar.background.setTint(getColor(R.color.botonesAccesos))
            }

            btnAceptar.setOnClickListener {
                if (contrasenya1.text.toString() != contrasenya2.text.toString()) {
                    Toast.makeText(
                        this,
                        getString(R.string.toas_contraseñas_distintas),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // TODO: Tema 5 Actividad 3 Modificar LOGIN
                    // Instancia de conexion a al Base de Datos
                    val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
                    val modificado = mbo?.changePassword(cliente);
                    if (modificado != 0) Toast.makeText(
                        this,
                        getString(R.string.contraseñas_iguales),
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(
                        this,
                        "Error al modificar la contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
            btnCancelar.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        binding.btnSalir.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Tema 5 act. 2
        binding.btnTransferencias.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("usuario", binding.txtvUsuario.text)
            startActivity(intent)
        }


    }

    // TODO tema 7 implementar metodo
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       when(item.itemId) {
           R.id.nav_home -> {
               val intent = Intent(this, MainActivity::class.java)
               intent.putExtra("cliente", cliente)
               startActivity(intent)
           }

           R.id.nav_posicion -> {
               val intent = Intent(this, GlobalPositionActivity::class.java)
               intent.putExtra("cliente", cliente)
               startActivity(intent)
           }

           R.id.nav_movimientos -> {
               val intent = Intent(this, MovementsActivity::class.java)
               intent.putExtra("cliente", cliente)
               startActivity(intent)
           }

           R.id.nav_transferencias -> {
               val intent = Intent(this, TransferActivity::class.java)
               startActivity(intent)
           }
           R.id.nav_contrasena->{
               binding.btnCambiarContrasenya.performClick()
           }
           R.id.nav_logout -> {
               val intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)
           }
       }
           drawerLayout.closeDrawer(GravityCompat.START)
                   return true
       }

    override fun onBackPressed() {
        super.onBackPressed()
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
    }else
        onBackPressedDispatcher.onBackPressed()
    }
}