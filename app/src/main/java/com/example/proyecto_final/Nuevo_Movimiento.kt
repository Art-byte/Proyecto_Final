package com.example.proyecto_final

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Data.MovimientosDB
import com.example.proyecto_final.Data.Usuarios_DB.Companion.UsuarioAdd
import com.example.proyecto_final.Entitys.Movimientos_Entity
import com.example.proyecto_final.Tools.NetworkConnectionState
import com.example.proyecto_final.Tools.Preferencias
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_nuevo__movimiento.*
import org.json.JSONObject
import java.util.*

class Nuevo_Movimiento : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var gmap: GoogleMap
    private lateinit var preferencias: SharedPreferences

    val movDB = MovimientosDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo__movimiento)


        preferencias = getSharedPreferences("DATOS", Context.MODE_PRIVATE)



        btnGuardarMov.setOnClickListener {
            val movimiento = Movimientos_Entity()
            val queue = Volley.newRequestQueue(this)
            val urlUsr = "http://batiahorros.somee.com/api/Movimientos"


            if (edtDesMov.text.toString().trim().isNotEmpty()) {
                val movDes = edtDesMov.text.toString()

                if (edtCantidadMov.text.toString().trim().isNotEmpty()) {
                    val movCant = edtCantidadMov.text.toString().toInt()

                    val tipoMovimiento = spnTipoMovimiento.selectedItemPosition
                    if (tipoMovimiento > 0) {
                        movimiento.tipo_movimiento = tipoMovimiento
                    }



                    /*******************Shared Preferences*******************/
                    preferencias.edit().putString(Preferencias.ULTIMO_MOVIMIENTO,edtDesMov.text.toString()).apply()


                    val fecha = txvFechaMov.text.toString()
                    movimiento.latitud = lastLocation.latitude
                    movimiento.longitud = lastLocation.longitude

                    movimiento.id_usuario = UsuarioAdd.id
                    movimiento.sysnc_state = 0
                    val valuest = Movimientos_Entity(movimiento.id,movCant,movimiento.tipo_movimiento,movDes,fecha,movimiento.latitud,movimiento.longitud,movimiento.id_usuario,movimiento.sysnc_state )
                    movDB.addMovimientos(valuest)


                    Toast.makeText(this@Nuevo_Movimiento, "Movimiento guardado ", Toast.LENGTH_SHORT).show()
                    movDB.movimientosGetAll()

                    edtDesMov.text.clear()
                    edtCantidadMov.text.clear()

                    /*****DETECCION DE LA RED****/
                    val networkState = NetworkConnectionState(this@Nuevo_Movimiento)
                    networkState.NetworkIsConnected()

                    if(networkState.NetworkIsConnected()){

                        val values = JSONObject()
                        values.put("tipo_movimiento","${tipoMovimiento}".toInt())
                        values.put("cantidad","${movCant}".toInt())
                        values.put("descripcion","${movDes}")
                        values.put("fecha_movimiento","${fecha}")
                        values.put("latitud","${movimiento.latitud}".toDouble())
                        values.put("longitud","${movimiento.longitud}".toDouble())
                        values.put("id_usuario","${UsuarioAdd.id}".toInt())

                        val stringRequest = JsonObjectRequest(Request.Method.POST,urlUsr,values, Response.Listener{ response ->
                            Log.d("UDELP","MI SERVICIO RESPONSE!!!   $response")
                        }, Response.ErrorListener { error ->
                            error.printStackTrace()
                            Log.d("UDELP","ERROR MI SERVICIO!!!   ${error.toString()}")
                        })

                        queue.add(stringRequest)
                    }else{
                        Snackbar.make(it, "No hay conexion para sincronizar ", Snackbar.LENGTH_SHORT)
                            .show()
                    }



                } else {
                    Snackbar.make(
                        it,
                        "Por favor ingresa el monto del movimiento",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

            } else {
                Snackbar.make(it, "Por favor describe el movimiento", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }


        btnTituloUltimoMovimiento.setOnClickListener{
            txvUltimoMovimiento.text = preferencias.getString(Preferencias.ULTIMO_MOVIMIENTO,"No hay registro aun")
        }



        btnFechaEgreso.setOnClickListener{
            var cal = Calendar.getInstance()
            var year: Int = cal.get(Calendar.YEAR)
            var month: Int = cal.get(Calendar.MONTH)
            var day: Int = cal.get(Calendar.DAY_OF_MONTH)


            var dpd = DatePickerDialog(this@Nuevo_Movimiento, DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, y: Int, m: Int, d: Int ->
                val dayString = if(d !in 1..9){
                    d.toString()
                }else{
                    "0$d"
                }

                val monthString = if(m !in 0..9){
                    (m+1).toString()

                }else{
                    "0${m+1}"
                }

                txvFechaMov.text = "$y - $monthString - $dayString "

            },year,month,day)
            dpd.show()
        }

        /*******************Google Map*******************/
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fgmMapMovimiento) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)




        //Deteccion de red


    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onMapReady(p0: GoogleMap) {
        gmap = p0
        gmap.setOnMarkerClickListener(this)
        gmap.uiSettings.isZoomControlsEnabled = true
        setUpMap()

    }

    private fun setUpMap(){
        gmap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) {location ->
            if(location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarket(currentLatLong)
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 15f))
            }
        }
    }

    private fun placeMarket(location: LatLng){
        val markerOptions = MarkerOptions().position(location)
        gmap.addMarker(markerOptions)
    }


}
