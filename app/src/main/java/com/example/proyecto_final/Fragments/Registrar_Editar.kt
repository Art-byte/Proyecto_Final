package com.example.proyecto_final.Fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_final.Data.Usuarios_DB
import com.example.proyecto_final.Entitys.UsuariosEntity
import com.example.proyecto_final.Login

import com.example.proyecto_final.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registrar__editar.*
import org.json.JSONObject
import java.util.*

class Registrar_Editar : Fragment() {

    private lateinit var mContext:Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext= context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar__editar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val queue = Volley.newRequestQueue(context)
         val urlUsr = "http://batiahorros.somee.com/api/Usuarios"


        btnFechaRegistro.setOnClickListener{
            var cal = Calendar.getInstance()
            var year: Int = cal.get(Calendar.YEAR)
            var month: Int = cal.get(Calendar.MONTH)
            var day: Int = cal.get(Calendar.DAY_OF_MONTH)


            var dpd = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, y: Int, m: Int, d: Int ->
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

                txvFechaRegistro.text = " $y - $monthString - $dayString"

            },year,month,day)
            dpd.show()
        }



        btnEnviarRegistro.setOnClickListener{
            val usuariosDb = Usuarios_DB(mContext)


            progressBarGuardar.visibility = View.VISIBLE
            Handler().postDelayed({


                if(edtNombreRegistro.text.toString().trim().isNotEmpty()){
                    var nomRe = edtNombreRegistro.text.toString()

                    if(edtApRegistro.text.toString().trim().isNotEmpty()){
                        var apR = edtApRegistro.text.toString()

                        if(edtAmRegistro.text.toString().trim().isNotEmpty()){
                            var amR = edtAmRegistro.text.toString()

                            if(edtCalle.text.toString().trim().isNotEmpty()){
                                var calleR = edtCalle.text.toString()

                                if(edtNum_ext.text.toString().trim().isNotEmpty()){
                                    var numExtR = edtNum_ext.text.toString()

                                    if(edtNum_Int.text.toString().trim().isNotEmpty()){
                                        var numIntT = edtNum_Int.text.toString()

                                        if(edtCP.text.toString().trim().isNotEmpty()){
                                            var cpR = edtCP.text.toString()


                                            if(edtEmails.text.toString().trim().isNotEmpty()){
                                                var mailR = edtEmails.text.toString()

                                                if(edtPass.text.toString().trim().isNotEmpty()){
                                                    var passR = edtPass.text.toString()

                                                    var select = rdgGenerosRegistro.checkedRadioButtonId

                                                    if(select != -1){
                                                        when(select){
                                                            rdbMasculino.id ->{
                                                                select = 1
                                                            }
                                                            rdbFemenino.id ->{
                                                                select = 0
                                                            }

                                                        }
                                                    }


                                                    val delposition = spnDelegacion.selectedItemPosition
                                                    if(delposition>0){
                                                         delposition
                                                    }

                                                    var fecha = txvFechaRegistro.text.toString()

                                                    var valors = UsuariosEntity(-1, nomRe,apR,amR,mailR,passR)

                                                    var id = usuariosDb.AddUsers(valors)


                                                    edtNombreRegistro.text.clear()
                                                    edtApRegistro.text.clear()
                                                    edtAmRegistro.text.clear()
                                                    edtCalle.text.clear()
                                                    edtNum_ext.text.clear()
                                                    edtNum_Int.text.clear()
                                                    edtCP.text.clear()
                                                    edtEmails.text.clear()
                                                    edtPass.text.clear()



                                                    val intent = Intent(mContext, Login::class.java)
                                                    intent.putExtra("usuario", edtNombreRegistro.text.toString())
                                                    startActivity(intent)




                                                        val values = JSONObject()

                                                        values.put("nombre","${nomRe}")
                                                        values.put("Ap","${apR}")
                                                        values.put("Am","${amR}")
                                                        values.put("genero","${select.toInt()}")
                                                        values.put("fecha_nacimiento","${fecha}")
                                                        values.put("calle","${calleR}")
                                                        values.put("num_exterior","${numExtR}")
                                                        values.put("numero_interior","${numIntT}")
                                                        values.put("codigo_postal","${cpR.toInt()}")
                                                        values.put("id_municipio","${delposition.toInt()}")
                                                        values.put("email","${mailR}")
                                                        values.put("password","${passR}")


                                                        val stringRequest = JsonObjectRequest(Request.Method.POST,urlUsr,values, Response.Listener{ response ->
                                                            Log.d("UDELP","MI SERVICIO INSERTA!!!   $response")
                                                        }, Response.ErrorListener { error ->
                                                            error.printStackTrace()
                                                            Log.d("UDELP","ERROR MI SERVICIO NO INSERTA!!!   ${error.toString()}")
                                                        })

                                                        queue.add(stringRequest)






                                                }else{
                                                    Snackbar.make(it,"Ingresa un password", Snackbar.LENGTH_SHORT).show()
                                                }

                                            }else{
                                                Snackbar.make(it,"Ingresa el Email", Snackbar.LENGTH_SHORT).show()
                                            }

                                        } else{
                                            Snackbar.make(it, "Ingresa el codigo postal", Snackbar.LENGTH_SHORT).show()
                                        }

                                    }else{
                                        Snackbar.make(it, "Ingresa el Numero interio", Snackbar.LENGTH_SHORT).show()
                                    }

                                }else{
                                    Snackbar.make(it, "Ingresa el Numero Exterior", Snackbar.LENGTH_SHORT).show()
                                }

                            }else{
                                Snackbar.make(it, "Ingresa una Calle", Snackbar.LENGTH_SHORT).show()
                            }

                        }else{
                            Snackbar.make(it, "Por favor ingresa el apellido materno", Snackbar.LENGTH_SHORT).show()
                        }

                    }else{
                        Snackbar.make(it, "Por favor ingresa el apellido paterno ", Snackbar.LENGTH_SHORT).show()
                    }

                }else{
                    Snackbar.make(it, "Por favor ingresa el nombre", Snackbar.LENGTH_SHORT).show()
                }

                usuariosDb.UsuarioGetAll()

                progressBarGuardar.visibility = View.GONE
            },3000)




        }


    }


    companion object{
        @JvmStatic
        fun newInstance() = Registrar_Editar()
    }
}
