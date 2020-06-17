package com.example.proyecto_final

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.proyecto_final.Data.MovimientosDB
import com.example.proyecto_final.Entitys.UsuariosEntity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val usur = UsuariosEntity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hay que comprobar la funcionalidad de esta linea
        txvUsuario.text = intent.getStringExtra("Usuario")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_Layaout)
        var toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigation.setNavigationItemSelectedListener(this)



        btnActualizar.setOnClickListener{
            setUpPieChartData()

        }

        setUpPieChartData()

    }


    private fun setUpPieChartData() {

        val gasto = MovimientosDB(this)

        val egreso = gasto.avgGastos()
        val ingreso = gasto.avgIngresos()

        val yVals = ArrayList<PieEntry>()
        yVals.add(PieEntry(egreso, "Gastos ${egreso}%"))
        yVals.add(PieEntry(ingreso, "Ingresos ${ingreso}%"))

        val dataSet = PieDataSet(yVals, "Reporte general")
        dataSet.valueTextSize=0f
        val colors = java.util.ArrayList<Int>()

        colors.add(Color.DKGRAY)
        colors.add(Color.GRAY)

        dataSet.setColors(colors)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.centerTextRadiusPercent = 0f
        pieChart.data.notifyDataChanged()

        pieChart.isDrawHoleEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.description.isEnabled = true
        pieChart.setBackgroundColor(Color.BLACK)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.setUsePercentValues(true)
        pieChart.highlightValue(null)
        pieChart.setUsePercentValues(true)
        data.dataSet
        data.notifyDataChanged()
        dataSet.notifyDataSetChanged()
        pieChart.invalidate()
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.btnRecordatorios -> {
                val intent = Intent(this@MainActivity, Alertas::class.java)
                startActivity(intent)
            }

            R.id.btnMiPerfil -> {
                val intent = Intent(this@MainActivity, Mi_Perfil::class.java)
                val nombre = usur.nombre
                intent.putExtra("NOMBRE",nombre)
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
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnMovimientosMenu->{
                val intent = Intent(this@MainActivity,Nuevo_Movimiento::class.java)
                startActivity(intent)
            }

            R.id.btnMovimientos -> {
                val intent = Intent(this@MainActivity, Ver_Movimientos::class.java)
                startActivity(intent)
            }


        }

        return true
    }



}
