package com.example.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_final.Adapters.MovimientosAdapter
import com.example.proyecto_final.Data.MovimientosDB
import com.example.proyecto_final.Entitys.Movimientos_Entity
import kotlinx.android.synthetic.main.activity_ver_movimientos.*

class Ver_Movimientos : AppCompatActivity() {

    val movDB = MovimientosDB(this)
    private  lateinit var movList: ArrayList<Movimientos_Entity>
    private lateinit var movAdapter: MovimientosAdapter
    val movos = Movimientos_Entity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_movimientos)

        btnSoloGasto.setOnClickListener {

            movList = movDB.GastosGetAll()
            movAdapter = MovimientosAdapter(movList,this@Ver_Movimientos)

            val linearLayoutManager = LinearLayoutManager(this@Ver_Movimientos, LinearLayoutManager.VERTICAL,false )

            rvwMovimientos.layoutManager = linearLayoutManager
            rvwMovimientos.setHasFixedSize(true)
            rvwMovimientos.adapter = movAdapter

        }


        btnSoloIngresos.setOnClickListener{
            movList = movDB.IngresosGetAll()
            movAdapter = MovimientosAdapter(movList,this@Ver_Movimientos)

            val linearLayoutManager = LinearLayoutManager(this@Ver_Movimientos, LinearLayoutManager.VERTICAL,false )

            rvwMovimientos.layoutManager = linearLayoutManager
            rvwMovimientos.setHasFixedSize(true)
            rvwMovimientos.adapter = movAdapter
        }


        btnVerTodo.setOnClickListener{

            movList = movDB.movimientosGetAll()
            movAdapter = MovimientosAdapter(movList,this@Ver_Movimientos)

            val linearLayoutManager = LinearLayoutManager(this@Ver_Movimientos, LinearLayoutManager.VERTICAL,false )

            rvwMovimientos.layoutManager = linearLayoutManager
            rvwMovimientos.setHasFixedSize(true)
            rvwMovimientos.adapter = movAdapter

        }

        movList = movDB.movimientosGetAll()

        val linearLayoutManager = LinearLayoutManager(this@Ver_Movimientos, LinearLayoutManager.VERTICAL,false )
        movAdapter = MovimientosAdapter(movList,this@Ver_Movimientos)
        rvwMovimientos.layoutManager = linearLayoutManager
        rvwMovimientos.setHasFixedSize(true)
        rvwMovimientos.adapter = movAdapter
        movAdapter.notifyDataSetChanged()

    }
}
