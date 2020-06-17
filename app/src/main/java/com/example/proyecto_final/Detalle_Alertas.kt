package com.example.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle__alertas.*

class Detalle_Alertas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle__alertas)

        var bundle:Bundle? = intent.extras
        var value = bundle!!.getString("ID")

        txvDetalleNombre.text = intent.getStringExtra("nombre")
        txvDetalleHora.text = intent.getStringExtra("tiempo")
        txvDetalleMonto.text = intent.getStringExtra("monto")
        txvDetalleRepeticion.text = intent.getStringExtra("repeticion")

    }
}
