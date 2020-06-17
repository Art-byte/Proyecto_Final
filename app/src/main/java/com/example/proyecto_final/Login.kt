package com.example.proyecto_final

import android.Manifest
import android.app.FragmentManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Data.Usuarios_DB
import com.example.proyecto_final.Data.Usuarios_DB.Companion.UsuarioAdd
import com.example.proyecto_final.Entitys.UsuariosEntity
import com.example.proyecto_final.Fragments.Registrar_Editar
import com.example.proyecto_final.Tools.ApiRest
import com.example.proyecto_final.Tools.NetworkConnectionState
import com.example.proyecto_final.Tools.Permission
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    var bn = 0
    val login = Usuarios_DB(this)
    val manager = supportFragmentManager
    var isFragmentOneLoaded =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val myApi = ApiRest(this@Login)
        myApi.getAllJsonObject()




        btnAceptar.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            val usrVal = UsuariosEntity()
            usrVal.email = email
            usrVal.pass = password
            bn = login.LoginAutentication(usrVal)

            if(bn >= 0){
                progressBar.visibility = View.VISIBLE
                Handler().postDelayed({

                    val intent = Intent(this@Login, MainActivity::class.java)
                    intent.putExtra("Usuario", edtEmail.text.toString() )


                    UsuarioAdd = login.GetOneUsuario(bn)
                    login.UsuarioGetAll()
                    startActivity(intent)


                    progressBar.visibility = View.GONE
                },2000)


            }else{
                Snackbar.make(it, "ERROR: El usuario o password es incorrecto", Snackbar.LENGTH_SHORT).show()
            }


        }


        /**********************Funcion del boton Registro*********************/
        txvRegistrarse.setOnClickListener{

            showFragment()
            //val intent = Intent(this@Login,Registrar_Editar::class.java)
            //startActivity(intent)
        }

        //Deteccion de red
        val networkState = NetworkConnectionState(this@Login)
        networkState.NetworkIsConnected()

        //Control de Permisos
        if(!permission.hasPermission(ListPermission)){
            permission.requestPermission(ListPermission,requestCode)
        }
    }


    /***************Variables para los permisos*******************/
    val requestCode = 1
    val permission = Permission(this@Login)
    val ListPermission = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            requestCode->{
                if(grantResults.size > 0){
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[2] != PackageManager.PERMISSION_GRANTED){


                        Toast.makeText(this@Login,"Es obligatorio aceptar los permisos para utilizar la aplicacion", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else{
                    Toast.makeText(this@Login, "Es obligatorio aceptar los permisos para usar la aplicacion",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun showFragment(){
        val transaction = manager.beginTransaction()
        val fragment = Registrar_Editar()
        transaction.replace(R.id.FrameAct, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = true
    }


}
