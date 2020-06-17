package com.example.proyecto_final.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.proyecto_final.Entitys.UsuariosEntity

class Usuarios_DB{

    private var connectionDB: ConnectionDB
    private lateinit var sqliteDatabase: SQLiteDatabase

    constructor(context: Context){
        connectionDB = ConnectionDB(context)
    }

    companion object{
        const val ID = "id"
        const val NOMBRE = "Nombre"
        const val APELLIDO_PATERNO = "Apellido_paterno"
        const val APELLIDO_MATERNO = "Apellido_materno"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"

        var idUsuariosList = ArrayList<UsuariosEntity>()
        var UsuarioAdd = UsuariosEntity() // lo uso para definir el id del usuario en relacion con sus gastos

    }

    fun AddUsers(users: UsuariosEntity): Long{
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)

        val values = ContentValues()
        values.put(NOMBRE, users.nombre)
        values.put(APELLIDO_PATERNO, users.apellido_paterno)
        values.put(APELLIDO_MATERNO, users.apellido_materno)
        values.put(EMAIL, users.email)
        values.put(PASSWORD, users.pass)

        return sqliteDatabase.insert(ConnectionDB.TABLE_NAME_USUARIOS, null, values)
    }


    fun editPerfilUsr(users:UsuariosEntity):Int{
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_WRITE)
        val values = ContentValues()
        values.put(NOMBRE, users.nombre)
        values.put(APELLIDO_PATERNO, users.apellido_paterno)
        values.put(APELLIDO_MATERNO, users.apellido_materno)
        values.put(EMAIL, users.email)
        values.put(PASSWORD, users.pass)
        var selection = "id=?"
        var args = arrayOf(users.id.toString())

        return sqliteDatabase.update(ConnectionDB.TABLE_NAME_USUARIOS,values,selection,args)
    }

    fun UsrGetAllView(): ArrayList<UsuariosEntity> {

        var answer = ArrayList<UsuariosEntity>()
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMAIL, PASSWORD)
        var selection = "$ID = ${UsuarioAdd.id}"
        val cursor = sqliteDatabase.query(ConnectionDB.TABLE_NAME_USUARIOS,fields,selection,null, null,null,null)
        if(cursor.moveToFirst()){

            answer.add(UsuariosEntity(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
            )
        }
        sqliteDatabase.close()
        return  answer
    }


    fun GetOneUsuario(users: Int): UsuariosEntity{
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fileds = arrayOf(ID,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMAIL, PASSWORD)
        var selection = "$ID=?"
        var args = arrayOf(users.toString())

        val cursor = sqliteDatabase.query(ConnectionDB.TABLE_NAME_USUARIOS,fileds,selection,args,null,null,null,null)
        var user = UsuariosEntity()

        if(cursor.moveToFirst()){

            var id = cursor.getInt(0)
            var nombre = cursor.getString(1)
            var ap = cursor.getString(2)
            var am =  cursor.getString(3)
            var email = cursor.getString(4)
            var password = cursor.getString(5)

            user = UsuariosEntity(
                cursor.getInt(0),
                "${nombre}",
                "${ap}",
                "${am}",
                "${email}",
                "${password}"

            )
        }
        return user
    }


    fun UsuarioGetAll(){
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)
        val fields = arrayOf(ID,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMAIL, PASSWORD)

        val cursor = sqliteDatabase.query(ConnectionDB.TABLE_NAME_USUARIOS, fields,null,null,null,null,null)

        if(cursor.moveToFirst()){
            do{
                Log.d("USUARIO","${cursor.getInt(0)} ${cursor.getString(1)} ${cursor.getString(2)}" +
                        "${cursor.getString(3)} ${cursor.getString(4)} ${cursor.getString(5)}")


            }while(cursor.moveToNext())
        }
    }



    fun LoginAutentication(login: UsuariosEntity): Int{
        var encontrado =- 1
        sqliteDatabase = connectionDB.openConnection(ConnectionDB.MODE_READ)

        val fields = arrayOf(ID,NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, EMAIL, PASSWORD)
        val cursor = sqliteDatabase.query(ConnectionDB.TABLE_NAME_USUARIOS, fields, null,null,null,null,null)

        if(cursor.moveToFirst()){
            do{

                if(cursor.getString(4) == login.email && cursor.getString(5) == login.pass ){
                    encontrado = cursor.getInt(0)
                    Log.d("Login","Usuario Exitoso")
                }

            }while(cursor.moveToNext())
        }
        return encontrado
    }
}