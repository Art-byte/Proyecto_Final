package com.example.proyecto_final.Adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Data.MovimientosDB
import com.example.proyecto_final.Detalle_Movimiento
import com.example.proyecto_final.Entitys.Movimientos_Entity
import com.example.proyecto_final.R
import kotlinx.android.synthetic.main.item_movimientos.view.*



class MovimientosAdapter (val movimientosList: ArrayList<Movimientos_Entity>, val context: Context): RecyclerView.Adapter<MovimientosHolder>(){

    val movDb = MovimientosDB(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientosHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovimientosHolder(inflater.inflate(R.layout.item_movimientos,parent,false))
    }

    override fun getItemCount(): Int {
        return movimientosList.size
    }

    override fun onBindViewHolder(holder: MovimientosHolder, position: Int) {

        holder.txvTipoMovimiento.text = if(movimientosList[position].tipo_movimiento == 1)"Gasto" else "Ingreso"
        holder.txvNameMovimiento.text = "${movimientosList[position].descripcion}"
        holder.txvMontoMovimiento.text = "Monto $ ${movimientosList[position].cantidad}"
        holder.txvFechaMovimiento.text = "Fecha: ${movimientosList[position].fecha_movimiento}"
        holder.txvLatitud.text = "${movimientosList[position].latitud}"
        holder.txvLongitud.text = "${movimientosList[position].longitud}"




        holder.ibtView.setOnClickListener{

            val context: Context = holder.ibtView.getContext()

            val intent = Intent(context, Detalle_Movimiento::class.java)
            intent.putExtra("Cantidad",holder.txvMontoMovimiento.text.toString())
            intent.putExtra("Fecha",holder.txvFechaMovimiento.text.toString())
            intent.putExtra("NAME", holder.txvNameMovimiento.text.toString() )
            intent.putExtra("LATI", holder.txvLatitud.text.toString())
            intent.putExtra("LONGI",  holder.txvLongitud.text.toString())
            context.startActivity(intent)

        }

        holder.ibtDelete.setOnClickListener{

            val queue = Volley.newRequestQueue(context)
            val urlUsr = "http://batiahorros.somee.com/api/Movimientos"

            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Desea eliminar este registro?")
            builder.setPositiveButton("SI"){ dialogInterface: DialogInterface, i: Int ->

                val moviDb = MovimientosDB(context)
                val result =  moviDb.EliminarMovimiento(movimientosList[position].id)


                val stringRequest = JsonObjectRequest(Request.Method.DELETE,"$urlUsr/${movimientosList[position].id}",null, Response.Listener{ response ->
                    Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                },Response.ErrorListener {  error ->
                    error.printStackTrace()
                    Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                })
                queue.add(stringRequest)




                if(result>0){
                    val indexArray = movimientosList.indexOf(movimientosList[position])
                    movimientosList.removeAt(indexArray)
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



    }
}



class MovimientosHolder(view: View): RecyclerView.ViewHolder(view){

    val imvLogoItem = view.imvLogoItem
    val txvNameMovimiento = view.txvNameMovimiento
    val txvMontoMovimiento = view.txvMontoMovimiento
    val txvFechaMovimiento = view.txvFechaMovimiento
    val ibtView = view.ibtView
    val txvTipoMovimiento = view.txvTipoMovimiento
    val txvLatitud = view.txvLatitud
    val txvLongitud = view.txvLongitud
    val ibtDelete = view.ibtDeletes

}