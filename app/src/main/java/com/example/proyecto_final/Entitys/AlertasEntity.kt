package com.example.proyecto_final.Entitys

data class AlertasEntity (

    var id:Int,
    var nombre_recordatorio:String,
    var hora_recordatorio:String,
    var monto:Int,
    var estado_alarma: Int,
    var usuario:Int
){
    constructor():this(0,"","",0,0,0)
}