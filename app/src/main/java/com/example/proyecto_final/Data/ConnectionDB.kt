package com.example.proyecto_final.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConnectionDB(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USUARIOS)
        db?.execSQL(CREATE_TABLE_ALERTAS)
        db?.execSQL(CREATE_TABLE_MOVIMIENTOS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_USUARIOS)
        db?.execSQL(DROP_TABLE_ALERTAS)
        db?.execSQL(DROP_TABLE_MOVIMIENTOS)
    }


    fun openConnection(typeConnectionDB: Int): SQLiteDatabase {
        return when(typeConnectionDB){
            MODE_WRITE->
                writableDatabase
            MODE_READ->
                readableDatabase
            else->
                readableDatabase
        }
    }


    companion object{

        const val DATABASE_NAME = "CONTROL_AHORROS"
        const val DATABASE_VERSION= 1
        const val MODE_WRITE = 1
        const val MODE_READ = 2

        const val TABLE_NAME_USUARIOS = "USUARIOS"
        const val TABLE_NAME_ALERTAS = "ALERTAS"
        const val TABLE_NAME_MOVIMIENTOS = "MOVIMIENTOS"

        const val CREATE_TABLE_USUARIOS ="CREATE TABLE $TABLE_NAME_USUARIOS(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(50), APELLIDO_PATERNO VARCHAR(50),APELLIDO_MATERNO VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50))"
        const val DROP_TABLE_USUARIOS ="DROP TABLE IF EXISTS $TABLE_NAME_USUARIOS"

        const val CREATE_TABLE_MOVIMIENTOS ="CREATE TABLE $TABLE_NAME_MOVIMIENTOS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TIPO_MOV INTEGER, CANTIDAD INTEGER, DESCRIPCION VARCHAR(50), FECHA_MOV DATE, LATITUD REAL, LONGITUD REAL, ID_USUARIO INTEGER, SYSNC_STATE INTEGER, FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID))"
        const val DROP_TABLE_MOVIMIENTOS ="DROP TABLE IF EXISTS $TABLE_NAME_MOVIMIENTOS"

        const val CREATE_TABLE_ALERTAS= "CREATE TABLE $TABLE_NAME_ALERTAS(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE_RECORDATORIO VARCHAR(50), HORA_RECORDATORIO TIME, MONTO INTEGER, ESTADO_ALARMA INTEGER, ID_USUARIO INTEGER, FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID) )"
        const val DROP_TABLE_ALERTAS = "DROP TABLE IF EXISTS $TABLE_NAME_ALERTAS"
    }
}