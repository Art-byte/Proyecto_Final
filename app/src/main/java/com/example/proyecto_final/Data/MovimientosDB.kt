package com.example.proyecto_final.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.proyecto_final.Data.Usuarios_DB.Companion.UsuarioAdd
import com.example.proyecto_final.Entitys.Movimientos_Entity

class MovimientosDB {


    private var connectionDB: ConnectionDB
    private lateinit var sqLiteDatabase: SQLiteDatabase

    constructor(context: Context){
        connectionDB = ConnectionDB(context)
    }

    companion object{
        const val ID = "Id"
        const val CANTIDAD = "cantidad"
        const val TIPO_MOVIMIENTO = "tipo_mov"
        const val DESCRIPCION = "descripcion"
        const val FECHA_MOVIMIENTO = "fecha_mov"
        const val LATITUD = "latitud"
        const val LONGITUD = "longitud"
        const val ID_USUARIO = "id_usuario"
        const val SYSNC_STATE = "sysnc_state"


    }


    fun addMovimientos (gasto: Movimientos_Entity): Long{
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)

        val values = ContentValues()
        values.put(CANTIDAD, gasto.cantidad)
        values.put(TIPO_MOVIMIENTO, gasto.tipo_movimiento)
        values.put(DESCRIPCION, gasto.descripcion)
        values.put(FECHA_MOVIMIENTO, gasto.fecha_movimiento)
        values.put(LONGITUD, gasto.longitud)
        values.put(LATITUD, gasto.latitud)
        values.put(ID_USUARIO, gasto.id_usuario)
        values.put(SYSNC_STATE, gasto.sysnc_state )
        return sqLiteDatabase.insert(ConnectionDB.TABLE_NAME_MOVIMIENTOS, null,values)
    }



    fun EliminarMovimiento(idAlerta: Int):Int{
        val answer: Int
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)
        var selection = "$ID=?"
        var args = arrayOf(idAlerta.toString())
        answer = sqLiteDatabase.delete(ConnectionDB.TABLE_NAME_MOVIMIENTOS,selection,args)
        sqLiteDatabase.close()
        return answer
    }


    fun movimientosGetAll():ArrayList<Movimientos_Entity>{
        val answer = ArrayList<Movimientos_Entity>()

        var selected = "id_usuario = ${UsuarioAdd.id}"

        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID, CANTIDAD, TIPO_MOVIMIENTO, DESCRIPCION, FECHA_MOVIMIENTO, LONGITUD,
            LATITUD, ID_USUARIO, SYSNC_STATE)

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_MOVIMIENTOS,fields,selected,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                answer.add(
                    Movimientos_Entity(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getInt(7),
                        cursor.getInt(8)
                    )

                )

                Log.d("MOV","${cursor.getInt(0)} ${cursor.getInt(1)} ${cursor.getInt(2)} ${cursor.getString(3)}" +
                        "${cursor.getString(4)} ${cursor.getDouble(5)} ${cursor.getDouble(6)} ${cursor.getInt(7)} ${cursor.getInt(8)}")
            }while(cursor.moveToNext())
        }

        return answer
    }



    fun GastosGetAll():ArrayList<Movimientos_Entity>{

        val answer = ArrayList<Movimientos_Entity>()
        val selection = "tipo_mov = 1 and id_usuario = ${UsuarioAdd.id}"

        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID, CANTIDAD, TIPO_MOVIMIENTO, DESCRIPCION, FECHA_MOVIMIENTO, LONGITUD,
            LATITUD, ID_USUARIO, SYSNC_STATE)

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_MOVIMIENTOS,fields,selection,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                answer.add(
                    Movimientos_Entity(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getInt(7),
                        cursor.getInt(8)
                    )

                )

                Log.d("MOV","${cursor.getInt(0)} ${cursor.getInt(1)} ${cursor.getInt(2)} ${cursor.getString(3)}" +
                        "${cursor.getString(4)} ${cursor.getDouble(5)} ${cursor.getDouble(6)} ${cursor.getInt(7)} ${cursor.getInt(8)}")
            }while(cursor.moveToNext())
        }

        return answer
    }



    fun IngresosGetAll():ArrayList<Movimientos_Entity>{

        val answer = ArrayList<Movimientos_Entity>()
        val selection = "tipo_mov = 2 and id_usuario = ${UsuarioAdd.id}"

        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID, CANTIDAD, TIPO_MOVIMIENTO, DESCRIPCION, FECHA_MOVIMIENTO, LONGITUD,
            LATITUD, ID_USUARIO, SYSNC_STATE)

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_MOVIMIENTOS,fields,selection,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                answer.add(
                    Movimientos_Entity(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getInt(7),
                        cursor.getInt(8)
                    )

                )


                Log.d("MOV","${cursor.getInt(0)} ${cursor.getInt(1)} ${cursor.getInt(2)} ${cursor.getString(3)}" +
                        "${cursor.getString(4)} ${cursor.getDouble(5)} ${cursor.getDouble(6)} ${cursor.getInt(7)} ${cursor.getInt(8)}")
            }while(cursor.moveToNext())
        }

        return answer
    }


    /***CALCULOS SOBRE LA BASE DE DATOS*/
    fun avgIngresos():Float{
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val selection = " tipo_mov = 2 and id_usuario = ${UsuarioAdd.id}"
        val fields = arrayOf(ID,"AVG(${CANTIDAD})")

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_MOVIMIENTOS,fields,selection,null,null,null,null,null)

        if(cursor.moveToFirst()){
            cursor.getInt(1)
            Log.d("INGRESOS","Este es el total de ingresos: ${cursor.getInt(1)} ")
        }
        return cursor.getInt(1).toFloat()
    }


    fun avgGastos():Float{
        sqLiteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val selection = " tipo_mov = 1 and id_usuario = ${UsuarioAdd.id}"
        val fields = arrayOf(ID,"AVG(${CANTIDAD})")

        val cursor = sqLiteDatabase.query(ConnectionDB.TABLE_NAME_MOVIMIENTOS,fields,selection,null,null,null,null,null)

        if(cursor.moveToFirst()){

            cursor.getInt(1).toFloat()
            Log.d("GASTOS","Este es el total de gastos: ${cursor.getInt(1)} ")
        }

        return cursor.getInt(1).toFloat()
    }
}