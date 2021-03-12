package com.example.intentsinkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.intentsinkotlin.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMediaBinding

    companion object {
        private const val GET_IMAGE_CAPTURE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnStartCamera -> {
                // TODO: Start the camera in photo mode
                val i = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                }
            }
            R.id.btnCapturePic -> {
                // Take a picture and consume the returned result bitmap
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (i.resolveActivity(packageManager) != null) {
                    // start the activity and indicate that we expect a result back
                    startActivityForResult(i, GET_IMAGE_CAPTURE)
                    // quando l'activity che abbiamo lanciato termina ci restituisce qualcosa
                }
            }
            R.id.btnSendText -> {
                val message = "This is a text message"
                val i = Intent(Intent.ACTION_SENDTO)

                // Use the setData function to indicate the type of data that will be sent
                // this will help the system figure out what apps to include in the chooser
                i.data = Uri.parse("sms:18885551212")
                i.putExtra("sms_body", message)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                }
            }
            R.id.btnOpenURL -> {
                val url = "http://www.google.com"

                // Parse the URL string using the Uri class
                val webpage = Uri.parse(url)
                val i = Intent(Intent.ACTION_VIEW, webpage)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                }
            }
        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == GET_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                // Retrieve the data from the result intent and look for the bitmap
                val extras = data!!.extras
                val imageBitmap = extras!!["data"] as Bitmap?
                binding.imgCapturePic.setImageBitmap(imageBitmap)
            }
        }
}