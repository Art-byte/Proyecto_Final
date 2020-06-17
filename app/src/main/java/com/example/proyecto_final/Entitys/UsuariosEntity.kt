package com.example.proyecto_final.Entitys

data class UsuariosEntity(
    var id: Int,
    var nombre: String,
    var apellido_paterno: String,
    var apellido_materno: String,
    var email:String,
    var pass:String
){
    constructor():this(0,"","","","",
        "")
}


/**
 *
var genero: Int,
var fecha_Nacimiento:String,
var calle: String,
var num_exterior: String,
var numero_interio: String,
var codigo_postal: Int,
var idMunicipio: Int,
 *
 * **/