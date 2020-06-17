package com.example.proyecto_final.Tools

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.proyecto_final.R


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val mBuilder = NotificationCompat.Builder(context!!)
            .setSmallIcon(R.drawable.ic_bat_icon)
            .setContentTitle("Batman tenemos que pagar: ${intent.getStringExtra("nombre")}")
            .setContentText(intent.getStringExtra(Preferencias.EXTRA_PESAN))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(2000)
        val id = 30103
        NotificationManager.notify(id,mBuilder.build())
        var mp= MediaPlayer.create(context,R.raw.batman_song)
        mp.start()
    }
}