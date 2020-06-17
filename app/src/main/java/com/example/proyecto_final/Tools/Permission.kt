package com.example.proyecto_final.Tools

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(val context: Context){

    fun hasPermission(listPermission:Array<String>):Boolean{
        var answer: Boolean = true
        for(permission in listPermission){
            if(ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                answer = false
                break
            }
        }
        return answer
    }


    fun requestPermission(listPermission:Array<String>,requestCode: Int){
        ActivityCompat.requestPermissions(context as Activity, listPermission, requestCode)
    }
}