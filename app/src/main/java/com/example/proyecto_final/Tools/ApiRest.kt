package com.example.proyecto_final.Tools

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ApiRest(context:Context) {

    private val queue = Volley.newRequestQueue(context)
    private val urlUsr = "http://batiahorros.somee.com/api/Usuarios"


    fun getAllJsonObject() {

        val stringRequest = JsonObjectRequest(Request.Method.GET,urlUsr,null, Response.Listener{ response ->
            Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
        },Response.ErrorListener {  error ->
            error.printStackTrace()
            Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
        })

        queue.add(stringRequest)

    }



    fun postJsonObject() {

        val values = JSONObject()
        //a la derecha iria el tipo entity
        values.put("dataEvent","2020/05/26")
        values.put("latitude","19.291574")
        values.put("longitude","-99.130202")
        values.put("battery","99")
        values.put("source","0")
        values.put("type","0")

        val stringRequest = JsonObjectRequest(Request.Method.GET,urlUsr,values, Response.Listener{ response ->
            Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
        },Response.ErrorListener {  error ->
            error.printStackTrace()
            Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
        })

        queue.add(stringRequest)

    }


}