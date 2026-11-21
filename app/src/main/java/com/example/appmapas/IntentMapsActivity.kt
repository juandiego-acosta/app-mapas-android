package com.example.appmapas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class IntentMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_maps)

        val etOrigin: TextInputEditText = findViewById(R.id.etOrigin)
        val etDestination: TextInputEditText = findViewById(R.id.etDestination)
        val btnLaunch: Button = findViewById(R.id.btnLaunchGmaps)

        btnLaunch.setOnClickListener {
            val origin = etOrigin.text.toString().trim()
            val destination = etDestination.text.toString().trim()

            if (origin.isNotEmpty() && destination.isNotEmpty()) {
                launchGoogleMaps(origin, destination)
            } else {
                Toast.makeText(this, "Origen y Destino no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchGoogleMaps(origin: String, destination: String) {
        try {
            // Creamos la URI para el Intent de Google Maps
            val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$origin&destination=$destination")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            // Especificamos que solo queremos que Google Maps maneje esto
            intent.setPackage("com.google.android.apps.maps")

            startActivity(intent)

        } catch (e: Exception) {
            // Si Google Maps no está instalado, abrimos la versión web
            val webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$origin&destination=$destination")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }
}