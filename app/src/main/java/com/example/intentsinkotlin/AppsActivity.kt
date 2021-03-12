package com.example.intentsinkotlin

import android.app.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import androidx.core.app.NotificationCompat
import com.example.intentsinkotlin.databinding.ActivityAppsBinding
import com.example.intentsinkotlin.databinding.ActivityMainBinding

class AppsActivity : AppCompatActivity(),View.OnClickListener {

    companion object {
        private const val NOTIFY_ID = 1001 // il compilatore sostituisce la costante col valore in compile time
        private const val CHANNEL_ID = "My Channel"
        private const val CHANNEL_NAME = "Android Intents"
        private const val CHANNEL_DESCRIPTION ="Test Notification Channel"
    }

    private lateinit var binding : ActivityAppsBinding // perOgni Layout crea una sua rappresentazione in codice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSetAlarm.setOnClickListener(this)
        binding.btnShowMapLoc.setOnClickListener(this)
        binding.btnStartPhoneCall.setOnClickListener(this)
        binding.btnSendAnEmail.setOnClickListener(this)
        binding.btnPendIntent.setOnClickListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description= CHANNEL_DESCRIPTION

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager //SystemService ritorna Object quindi lo casto
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.btnSetAlarm -> {
                val message = "Time to wake up!"
                val hour = 6
                val minutes = 30

                val i = Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                    .putExtra(AlarmClock.EXTRA_VIBRATE, true)

                if(i.resolveActivity(packageManager) != null){
                    startActivity(i)
                }
            }
            R.id.btnShowMapLoc -> {
                val location = "geo:37.4220,-122.0841"
                val geoLocUri = Uri.parse(location)
                val i = Intent(Intent.ACTION_VIEW, geoLocUri)
                if(i.resolveActivity(packageManager)!=null){
                    startActivity(i)
                }
            }
            R.id.btnSendAnEmail -> {
                val addresses =  arrayOf("test@example.com")
                val ccs = arrayOf("someone@example.com")
                val subject = "This is a test"
                val message = "This is a test email message!"

                val i = Intent(Intent.ACTION_SENDTO)

                i.data= Uri.parse("mailto:")
                i.putExtra(Intent.EXTRA_EMAIL, addresses)
                i.putExtra(Intent.EXTRA_SUBJECT, subject)
                i.putExtra(Intent.EXTRA_CC, ccs)
                i.putExtra(Intent.EXTRA_TEXT, message)

                if(i.resolveActivity(packageManager)!=null) {
                    startActivity(i)
                }
            }
            R.id.btnStartPhoneCall -> {
                val phoneNumber = "1-800-555-1212"
                val numUri = Uri.parse("tel:"+phoneNumber)
                val i = Intent(Intent.ACTION_DIAL)
                i.data=numUri

                if(i.resolveActivity(packageManager)!=null) {
                    startActivity(i)
                }
            }
            R.id.btnWebSearch -> {
                val queryStr = "Eiffel Tower"
                val i= Intent(Intent.ACTION_WEB_SEARCH)
                i.putExtra(SearchManager.QUERY, queryStr)

                if(i.resolveActivity(packageManager)!=null) {
                    startActivity(i)
                }
            }
            R.id.btnPendIntent -> {
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                val intent = Intent(this, DestinationActivity::class.java)
                intent.putExtra("IntData", 1234)
                intent.putExtra("StringData", "Questa è una stringa")

                val pendingIntent= PendingIntent.getActivity(this, NOTIFY_ID,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT)
                // contenitore di un altro intento eseguito successivamente
                // se mi arrivano N notifiche dello stesso tipo da questa app l'ultima cancella le precedenti

                builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                builder.setContentTitle("Sample Notification")
                builder.setContentText("This is a sample notification")
                builder.setAutoCancel(true)
                builder.setSubText("Tap to view")
                builder.setContentIntent(pendingIntent)

                val notification = builder.build()
                val mgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                mgr.notify(NOTIFY_ID, notification)
            }

        }
    }
}