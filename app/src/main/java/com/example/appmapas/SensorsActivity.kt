package com.example.appmapas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appmapas.databinding.ActivitySensorsBinding // <-- Importante: Importa el View Binding
import kotlin.math.abs

class SensorsActivity : AppCompatActivity(), SensorEventListener {

    // Manager de Sensores
    private lateinit var sensorManager: SensorManager

    // AÑADIDO: Variable de View Binding
    private lateinit var binding: ActivitySensorsBinding

    // --- Sensores ---
    private var lightSensor: Sensor? = null
    private var gyroSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Configuración de View Binding ---
        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // --- Fin de Configuración ---

        // Inicializar el Manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // --- Configurar Sensor de Luz ---
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            Toast.makeText(this, "El dispositivo no tiene sensor de luz", Toast.LENGTH_SHORT).show()
        }

        // --- Configurar Giroscopio ---
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroSensor == null) {
            Toast.makeText(this, "El dispositivo no tiene giroscopio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Registrar los listeners cuando la app está activa
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        gyroSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar los listeners para ahorrar batería cuando la app no está visible
        sensorManager.unregisterListener(this)
    }

    // Esta función se llama cada vez que un sensor detecta un cambio
    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        // Usamos 'when' para saber qué sensor se actualizó
        when (event.sensor.type) {

            // --- Caso: Sensor de Luz ---
            Sensor.TYPE_LIGHT -> {
                val lux = event.values[0]
                // Usamos 'binding' para acceder a las vistas
                binding.tvLightData.text = "Luz: $lux lux"

                // Si la luz es fuerte (ej. > 20 lux), ponemos el sol
                if (lux > 20) {
                    binding.ivLightSensor.setImageResource(R.drawable.ic_sun)
                } else {
                    binding.ivLightSensor.setImageResource(R.drawable.ic_moon)
                }
            }

            // --- Caso: Giroscopio ---
            Sensor.TYPE_GYROSCOPE -> {
                val rotX = event.values[0] // Rotación en X
                val rotY = event.values[1] // Rotación en Y
                val rotZ = event.values[2] // Rotación en Z

                // Usamos 'binding' para acceder a las vistas
                binding.tvGyroData.text = "Giroscopio:\nX: %.2f rad/s\nY: %.2f rad/s\nZ: %.2f rad/s".format(rotX, rotY, rotZ)

                // Si el teléfono está girando en cualquier eje (umbral > 0.5)
                if (abs(rotX) > 0.5 || abs(rotY) > 0.5 || abs(rotZ) > 0.5) {
                    binding.ivGyroSensor.setImageResource(R.drawable.ic_spinning)
                } else {
                    binding.ivGyroSensor.setImageResource(R.drawable.ic_still)
                }
            }
        }
    }

    // No necesitamos esto, pero es obligatorio implementarlo
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No hacer nada
    }
}