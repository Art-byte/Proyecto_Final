package com.example.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_final.Adapters.UsuarioAdapter
import com.example.proyecto_final.Data.Usuarios_DB
import com.example.proyecto_final.Entitys.UsuariosEntity
import com.example.proyecto_final.Fragments.Registrar_Editar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawer_Layaout
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.activity_mi__perfil.*
import kotlinx.android.synthetic.main.content_layout.*

class Mi_Perfil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val usuariosDB = Usuarios_DB(this)
    private lateinit var listaUsuarios: ArrayList<UsuariosEntity>
    private lateinit var usrAdapter: UsuarioAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi__perfil)

        listaUsuarios = usuariosDB.UsrGetAllView()
        usrAdapter = UsuarioAdapter(listaUsuarios, this@Mi_Perfil)
        val linearLayoutManager = LinearLayoutManager(this@Mi_Perfil,
            LinearLayoutManager.VERTICAL,false)
        rvwUsuario.layoutManager = linearLayoutManager
        rvwUsuario.setHasFixedSize(true)
        rvwUsuario.adapter = usrAdapter




        /************************FUNCIONAMIENTO DE VISIBILIDAD DEL MENU LATERAL Y EL TOOLBAR***********************************/
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_Layaout)
        var toggle = ActionBarDrawerToggle(this@Mi_Perfil,drawerLayout,toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigation.setNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.btnRecordatorios -> {
                val intent = Intent(this@Mi_Perfil, Alertas::class.java)
                startActivity(intent)
            }

            R.id.btnMiPerfil -> {
                val intent = Intent(this@Mi_Perfil, Mi_Perfil::class.java)
                startActivity(intent)
            }

        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_Layaout.isDrawerOpen(GravityCompat.START)) {
            drawer_Layaout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnEditarPerfil->{
                val intent= Intent(this@Mi_Perfil,Registrar_Editar::class.java)
                startActivity(intent)
            }

        }
        return true
    }
}
