package com.example.intentsinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.intentsinkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      // setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater) // istanzio l'oggetto e gli faccio gonfiare il layout
        setContentView(binding.root) //radice del layout

        binding.createExplicit.setOnClickListener(this)
        binding.createImplicit.setOnClickListener(this)
        binding.btnMediaIntents.setOnClickListener(this)
        binding.btnAppIntents.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.create_explicit -> {
                val i = Intent(this, DestinationActivity::class.java)
                i.putExtra("IntData", 1234)
                i.putExtra("StringData", "This is a string")
                startActivity(i)
            }
            R.id.create_implicit -> {
                val textMessage = "This is a sample message"
                val i = Intent()
                i.action=Intent.ACTION_SEND
                i.type="text/plain"
                i.putExtra(Intent.EXTRA_TEXT, textMessage) //sta registrando il mex da mandare con la chiave che la specifica stabilisce
                val chooser = Intent.createChooser(i,"Select an app:")
                if(i.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                }
            }
            R.id.btnAppIntents -> {
                val i = Intent(this, AppsActivity::class.java)
                startActivity(i)
            }
            R.id.btnMediaIntents -> {
                val i = Intent(this, MediaActivity::class.java)
                startActivity(i)
            }
        }
    }
}