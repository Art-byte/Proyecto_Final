package com.example.proyecto_final.Entitys

data class Movimientos_Entity(

    var id: Int,
    var cantidad: Int,
    var tipo_movimiento: Int,
    var descripcion: String,
    var fecha_movimiento:String,
    var latitud: Double?,
    var longitud: Double?,
    var id_usuario: Int,
    var sysnc_state:Int

){
    constructor():this(0,0,0,"",
        "",0.0,0.0,0,0)
}