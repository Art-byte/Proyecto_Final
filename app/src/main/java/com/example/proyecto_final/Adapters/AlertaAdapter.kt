package com.example.proyecto_final.Adapters

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Data.Alertas_DB
import com.example.proyecto_final.Detalle_Alertas
import com.example.proyecto_final.Entitys.AlertasEntity
import com.example.proyecto_final.R
import com.example.proyecto_final.Tools.AlarmReceiver
import kotlinx.android.synthetic.main.item_recordatorios.view.*
import org.json.JSONObject
import java.util.*


class AlertaAdapter(val alertasList: ArrayList<AlertasEntity>, val context: Context): RecyclerView.Adapter<AlertasHolder>() {

    val alertdb = Alertas_DB(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertasHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AlertasHolder(inflater.inflate(R.layout.item_recordatorios, parent, false))

    }

    override fun getItemCount(): Int {
        return alertasList.size    }

    override fun onBindViewHolder(holder: AlertasHolder, position: Int) {
        holder.txvInfoRecordatorio.text = "${alertasList[position].nombre_recordatorio}"
        holder.txvMRepeticiones.text = if(alertasList[position].estado_alarma == 1) "Activada" else "Inactiva"
        holder.txvTiempoRepeticion.text = "${alertasList[position].hora_recordatorio}"
        holder.txvMontoPagar.text = "$ ${alertasList[position].monto}"
        holder.txvId.text = "${alertasList[position].id}"


        holder.ibtEdit.setOnClickListener{
            var context: Context = holder.ibtEdit.getContext()
            val intent = Intent(context.applicationContext, Detalle_Alertas::class.java)
            intent.putExtra("nombre", holder.txvInfoRecordatorio.text.toString())
            intent.putExtra("repeticion", holder.txvMRepeticiones.text.toString())
            intent.putExtra("tiempo", holder.txvTiempoRepeticion.text.toString())
            intent.putExtra("monto", holder.txvMontoPagar.text.toString())
            context.startActivity(intent)

        }

        /***********************Configurar el metodo de Delete************************/
        holder.ibtDelete.setOnClickListener{

            val queue = Volley.newRequestQueue(context)
            val urlUsr = "http://batiahorros.somee.com/api/Alertas"


            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Desea eliminar esta alarma?")
            builder.setPositiveButton("SI"){ dialogInterface: DialogInterface, i: Int ->
                val alarmaDb = Alertas_DB(context)
                val result = alarmaDb.EliminarAlerta(alertasList[position].id)

                val stringRequest = JsonObjectRequest(Request.Method.DELETE,"$urlUsr/${alertasList[position].id-1}",null, Response.Listener{ response ->
                    Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                },Response.ErrorListener {  error ->
                    error.printStackTrace()
                    Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                })
                queue.add(stringRequest)

                if(result>0){

                    val indexArray = alertasList.indexOf(alertasList[position])
                    alertasList.removeAt(indexArray)
                    notifyDataSetChanged()
                    Toast.makeText(context, "eliminado ", Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(context, "cancelado ", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->

            }

            builder.create().show()
        }



        /**NUEVO TIME PICKER DIALOG CON AJUSTES**/
        var mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = 100
        var pendingIntent: PendingIntent? = null



        holder.swtActAlarm.setOnCheckedChangeListener{ buttonView: CompoundButton, isChecked: Boolean ->

            val queue = Volley.newRequestQueue(context)
            val url= "http://batiahorros.somee.com/api/Alertas"

            if(isChecked){
                var setTime = Calendar.getInstance()
                var dateTime = holder.txvTiempoRepeticion.text.split(":")
                setTime.set(Calendar.HOUR_OF_DAY,dateTime[0].toInt())
                setTime.set(Calendar.MINUTE,dateTime[1].toInt())
                setTime.set(Calendar.SECOND, 0)

                val sendIntent = Intent(context, AlarmReceiver::class.java)
                sendIntent.putExtra("nombre", holder.txvInfoRecordatorio.text.toString())
                pendingIntent = PendingIntent.getBroadcast(context,requestCode,sendIntent,0)

                mAlarmManager.set(AlarmManager.RTC_WAKEUP,setTime.timeInMillis,pendingIntent)
                Toast.makeText(context,"La alarma sonara a las ${holder.txvTiempoRepeticion.text}",
                    Toast.LENGTH_SHORT).show()



                var estado:Int = 1

                val values = JSONObject()

                values.put("id","${alertasList[position].id-1}")
                values.put("estado_alarma","${estado}".toInt())

                val stringRequest = JsonObjectRequest(Request.Method.PUT,url,values, Response.Listener{ response ->
                    Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                }, Response.ErrorListener { error ->
                    error.printStackTrace()
                    Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                })

                queue.add(stringRequest)



            }else{
                if(pendingIntent != null) {
                    mAlarmManager.cancel(pendingIntent)
                    Toast.makeText(context, "Alarma deshabilitada", Toast.LENGTH_SHORT).show()

                    var estado:Int = 0

                    val values = JSONObject()

                    values.put("id","${alertasList[position].id-1}")
                    values.put("estado_alarma","${estado}".toInt())

                    val stringRequest = JsonObjectRequest(Request.Method.PUT,url,values, Response.Listener{ response ->
                        Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                    }, Response.ErrorListener { error ->
                        error.printStackTrace()
                        Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                    })

                    queue.add(stringRequest)
                }

            }

        }

    }
}



class AlertasHolder(view: View): RecyclerView.ViewHolder(view){

    val imvLogoItem = view.imvLogoItem
    val txvInfoRecordatorio = view.txvInfoRecordatorio
    val txvMRepeticiones = view.txvMRepeticiones
    val txvTiempoRepeticion = view.txvTiempoRepeticion
    val txvMontoPagar = view.txvMontoPagar
    val ibtEdit = view.ibtEdit
    val ibtDelete = view.ibtDelete
    val txvId = view.txvId
    val swtActAlarm = view.swtActAlarm
}