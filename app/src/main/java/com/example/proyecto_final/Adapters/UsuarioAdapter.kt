package com.example.proyecto_final.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Entitys.UsuariosEntity
import com.example.proyecto_final.R
import kotlinx.android.synthetic.main.item_usuario.view.*

class UsuarioAdapter(val usrList: ArrayList<UsuariosEntity>, val context: Context): RecyclerView.Adapter<UsuariosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UsuariosHolder(inflater.inflate(R.layout.item_usuario, parent, false))
    }

    override fun getItemCount(): Int {
        return usrList.size
    }


    override fun onBindViewHolder(holder: UsuariosHolder, position: Int) {
        holder.txvNombrePerfilView.text = "${usrList[position].nombre}"
        holder.txvApPerfilView.text = "${usrList[position].apellido_paterno}"
        holder.txvAmPerfilView.text = "${usrList[position].apellido_materno}"
        holder.txvEmailView.text = "${usrList[position].email}"
        holder.txvPassword.text = "${usrList[position].pass}"

    }
}


class UsuariosHolder(view: View): RecyclerView.ViewHolder(view){

    val txvNombrePerfilView = view.txvNombrePerfilView
    val txvApPerfilView = view.txvApPerfilView
    val txvAmPerfilView = view.txvAmPerfilView
    val txvEmailView = view.txvEmailView
    val txvPassword = view.txvPassword
}