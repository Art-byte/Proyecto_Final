package com.example.proyecto_final

import android.content.SharedPreferences
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto_final.Data.MovimientosDB
import com.example.proyecto_final.Entitys.Movimientos_Entity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detalle__movimiento.*

class Detalle_Movimiento : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


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
        setContentView(R.layout.activity_detalle__movimiento)

        val movimiento = Movimientos_Entity()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fgmMapVistaMovimiento) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        var bundle:Bundle? = intent.extras
        var value = bundle!!.getString("ID")

        txvDetalleNombre.text = intent.getStringExtra("NAME")
        txvDetalleNombreMovimiento.text = intent.getStringExtra("Cantidad")
        txvDetalleFechaMovimiento.text = intent.getStringExtra("Fecha")

        movimiento.latitud = intent.getStringExtra("LATI").toDouble()
        movimiento.longitud = intent.getStringExtra("LONGI").toDouble()


    }


    override fun onMapReady(p0: GoogleMap) {
        gmap = p0
        gmap.setOnMarkerClickListener(this)
        gmap.uiSettings.isZoomControlsEnabled = true
        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?)= false

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
