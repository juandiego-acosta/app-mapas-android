package com.example.appmapas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón de la Práctica 7
        val botonMapas: Button = findViewById(R.id.activity_main_maps)
        botonMapas.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        // --- Lógica Práctica 8 ---

        // Botón para Método 1 (API)
        val botonRutaApi: Button = findViewById(R.id.btnRouteApi)
        botonRutaApi.setOnClickListener {
            val intent = Intent(this, DirectionsApiActivity::class.java)
            startActivity(intent)
        }

        // Botón para Método 2 (Intent)
        val botonRutaIntent: Button = findViewById(R.id.btnRouteIntent)
        botonRutaIntent.setOnClickListener {
            val intent = Intent(this, IntentMapsActivity::class.java)
            startActivity(intent)
        }

        // --- AÑADIDO: Lógica Práctica 9 ---

        // Botón para Sensores
        val botonSensores: Button = findViewById(R.id.btnSensors)
        botonSensores.setOnClickListener {
            val intent = Intent(this, SensorsActivity::class.java)
            startActivity(intent)
        }
    }
}