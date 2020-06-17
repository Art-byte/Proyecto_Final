package com.example.proyecto_final.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.proyecto_final.Entitys.AlertasEntity

class Alertas_DB {

    private var connectionDB: ConnectionDB
    private lateinit var sqLiteDatabase: SQLiteDatabase

    constructor(context: Context) {
        connectionDB = ConnectionDB(context)
    }

    companion object {
        const val ID = "id"
        const val NOMBRE_RECORDATORIO = "nombre_recordatorio"
        const val HORA_RECORDATORIO = "hora_recordatorio"
        const val MONTO = "monto"
        const val ESTADO_ALARMA = "estado_alarma"
        const val ID_USUARIO = "id_usuario"


        private val listString = arrayListOf<String>()
        val listAlertasStringIDS =
            arrayListOf<String>()// lista ids // esto puede cambiar a lo mejor con recycler
    }

    fun AddAlerta(alerta: AlertasEntity):Long{
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)

        val values = ContentValues()
        values.put(NOMBRE_RECORDATORIO, alerta.nombre_recordatorio)
        values.put(HORA_RECORDATORIO, alerta.hora_recordatorio)
        values.put(MONTO, alerta.monto)
        values.put(ESTADO_ALARMA, alerta.estado_alarma)
        values.put(ID_USUARIO, alerta.usuario)
        return sqLiteDatabase.insert(ConnectionDB.TABLE_NAME_ALERTAS, null, values)
    }

    fun EliminarAlerta(idAlerta: Int): Int{

        val answer: Int
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)
        var selection = "$ID=?"
        var args = arrayOf(idAlerta.toString())
        answer = sqLiteDatabase.delete(ConnectionDB.TABLE_NAME_ALERTAS,selection,args)
        sqLiteDatabase.close()
        return answer
    }


    fun AlertasGetAll():ArrayList<AlertasEntity>{

        val answer = ArrayList<AlertasEntity>()
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID,NOMBRE_RECORDATORIO, HORA_RECORDATORIO, MONTO, ESTADO_ALARMA, ID_USUARIO)
        val selection = "id_usuario = ${Usuarios_DB.UsuarioAdd.id}"

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_ALERTAS,fields,selection,null,null,null,null)

        if(cursor.moveToFirst()){
            do{

                answer.add(
                    AlertasEntity(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                    )
                )
                Log.d("ALERTA", "${ cursor.getInt(0)} ${ cursor.getString(1)} ${ cursor.getString(2)}" +
                        "${ cursor.getInt(3)} ${ cursor.getInt(4)} ${ cursor.getInt(5)}")

            }while (cursor.moveToNext())
        }
        return answer
    }


    fun alertasGetOne(idEAlerta: Int): AlertasEntity {
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID, NOMBRE_RECORDATORIO, HORA_RECORDATORIO, MONTO, ESTADO_ALARMA)

        var selection = "Id?"
        var args = arrayOf(idEAlerta.toString())
        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_ALERTAS,fields,selection,args,null,null,null)
        var alerta = AlertasEntity()

        if(cursor.moveToFirst()){

            alerta = AlertasEntity(
                cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                cursor.getInt(3),cursor.getInt(4),cursor.getInt(5))


            Log.d("ALERTA", "${ cursor.getInt(0)} ${ cursor.getString(1)} ${ cursor.getString(2)}" +
                    "${ cursor.getInt(3)} ${ cursor.getInt(4)} ${ cursor.getInt(5)}")
        }

        return alerta
    }


}