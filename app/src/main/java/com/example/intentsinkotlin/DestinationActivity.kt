package com.example.intentsinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentsinkotlin.databinding.ActivityDestinationBinding
import com.example.intentsinkotlin.databinding.ActivityMainBinding

class DestinationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDestinationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callingIntent = intent
        callingIntent.let {
            val str = it.getStringExtra("StringData")
            val int1 = it.getIntExtra("IntData", -1)

            val data = """ 
                $str
                $int1
            """.trimIndent() //elimina spazi prima e dopo
            binding.txtValues.text=data

        }
    }
}