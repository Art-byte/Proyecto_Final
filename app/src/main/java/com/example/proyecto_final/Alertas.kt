package com.example.proyecto_final

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Adapters.AlertaAdapter
import com.example.proyecto_final.Data.Alertas_DB
import com.example.proyecto_final.Data.Usuarios_DB.Companion.UsuarioAdd
import com.example.proyecto_final.Entitys.AlertasEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_alertas.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Alertas : AppCompatActivity() {


    val alertDb = Alertas_DB(this)
    private lateinit var alertList: ArrayList<AlertasEntity>
    private lateinit var alertAdapter: AlertaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertas)

        btnGuardarAlerta.setOnClickListener {

             val queue = Volley.newRequestQueue(this)
             val url= "http://batiahorros.somee.com/api/Alertas"

            val alerts = AlertasEntity()

            if (edtNombreAlerta.text.toString().trim().isNotEmpty()) {
                val nomAl = edtNombreAlerta.text.toString()

                if (edtMontoAPagarRecordatorio.text.toString().trim().isNotEmpty()) {
                    val montoAl = edtMontoAPagarRecordatorio.text.toString().toInt()

                    val Estado: Int = 0


                    var hora = txvHoraMontoAPagarRecordatorio.text.toString()
                    alerts.usuario = UsuarioAdd.id

                    var valuest =
                        AlertasEntity(alerts.id, nomAl, hora, montoAl, Estado, alerts.usuario)

                    alertDb.AddAlerta(valuest)

                    edtNombreAlerta.text.clear()
                    edtMontoAPagarRecordatorio.text.clear()


                    val values = JSONObject()

                    values.put("nombre_recordatorio","${nomAl}")
                    values.put("hora_de_recordatorio","${hora}")
                    values.put("monto","${montoAl}")
                    values.put("estado_alarma","${Estado}")
                    values.put("id_usuario","${UsuarioAdd.id}".toInt())

                    val stringRequest = JsonObjectRequest(Request.Method.POST,url,values, Response.Listener{ response ->
                        Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                    }, Response.ErrorListener { error ->
                        error.printStackTrace()
                        Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                    })

                    queue.add(stringRequest)



                    val linearLayoutManager =
                        LinearLayoutManager(this@Alertas, LinearLayoutManager.VERTICAL, false)
                    alertList = alertDb.AlertasGetAll()
                    alertAdapter = AlertaAdapter(alertList, this@Alertas)
                    rvwRecordatorios.layoutManager = linearLayoutManager
                    rvwRecordatorios.setHasFixedSize(true)
                    rvwRecordatorios.adapter = alertAdapter
                    alertAdapter.notifyDataSetChanged()



                } else {
                    Snackbar.make(it, "Por favor ingresa el monto a pagar", Snackbar.LENGTH_SHORT)
                        .show()
                }


            } else {
                Snackbar.make(it, "Por favor ingresa el nombre de alerta", Snackbar.LENGTH_SHORT)
                    .show()
            }


        }


        btnHoraRecordatorio.setOnClickListener {

            TimePickerDialog(
                this,timePicker(),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true)
                .show()

        }


        /**ESTO MANTIENE VISIBLE EL CARD CUANDO NO SE HA APRETADO NADA*/
        val linearLayoutManager =
            LinearLayoutManager(this@Alertas, LinearLayoutManager.VERTICAL, false)
        alertList = alertDb.AlertasGetAll()
        alertAdapter = AlertaAdapter(alertList, this@Alertas)
        rvwRecordatorios.layoutManager = linearLayoutManager
        rvwRecordatorios.setHasFixedSize(true)
        rvwRecordatorios.adapter = alertAdapter
        alertAdapter.notifyDataSetChanged()


    }


    // Formato de la hora
    private var cal = Calendar.getInstance()
    private fun setMyTimeFormat():String{
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat)
        return sdf.format(cal.time)
    }

    //Contruccion del time picker dialog
    private fun timePicker(): TimePickerDialog.OnTimeSetListener{
        val timeSetListener = object: TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
                txvHoraMontoAPagarRecordatorio.text = setMyTimeFormat()
            }
        }
        return timeSetListener
    }
}






