package com.example.proyecto_final.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.proyecto_final.Login
import com.example.proyecto_final.R

class Bati_Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bati_splash)

        Handler().postDelayed({
            val intent = Intent(this@Bati_Splash, Login::class.java )
            startActivity(intent)
            finish()
        },3000)
    }

    }

