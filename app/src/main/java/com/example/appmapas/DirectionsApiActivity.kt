package com.example.appmapas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.appmapas.network.ApiService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil // Necesita: implementation 'com.google.maps.android:android-maps-utils:2.2.0' (Añadir a build.gradle si falta)
import kotlinx.coroutines.launch

class DirectionsApiActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // Coordenadas de ejemplo (CETI Colomos a CETI Tonalá)
    private val origin = LatLng(20.70329, -103.38808)
    private val destination = LatLng(20.60682, -103.20324)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions_api)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_directions) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        drawRoute()
    }

    private fun drawRoute() {
        mMap.addMarker(MarkerOptions().position(origin).title("Origen: CETI Colomos"))
        mMap.addMarker(MarkerOptions().position(destination).title("Destino: CETI Tonalá"))

        val originStr = "${origin.latitude},${origin.longitude}"
        val destStr = "${destination.latitude},${destination.longitude}"

        lifecycleScope.launch {
            try {
                val response = ApiService.instance.getDirections(originStr, destStr)

                // --- INICIO DE LA MODIFICACIÓN ---
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!

                    if (responseBody.status == "OK") {
                        // ¡ÉXITO! Dibujamos la ruta
                        val points = responseBody.routes[0].overviewPolyline.points
                        val decodedPath = PolyUtil.decode(points)

                        mMap.addPolyline(
                            PolylineOptions().addAll(decodedPath).color(Color.BLUE).width(12f)
                        )

                        val bounds = LatLngBounds.Builder()
                            .include(origin)
                            .include(destination)
                            .build()
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

                    } else {
                        // La API de Google nos dio un error (ej. REQUEST_DENIED, ZERO_RESULTS)
                        Log.e("DirectionsApiActivity", "Error de API: ${responseBody.status} - ${responseBody.errorMessage}")
                        Toast.makeText(
                            this@DirectionsApiActivity,
                            "Error de API: ${responseBody.status}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    // La llamada de red falló (ej. 404, 500)
                    Log.e("DirectionsApiActivity", "Error de Red: ${response.code()}")
                    Toast.makeText(
                        this@DirectionsApiActivity,
                        "Error de Red: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                // --- FIN DE LA MODIFICACIÓN ---
            } catch (e: Exception) {
                // Hubo una excepción (ej. sin internet)
                Log.e("DirectionsApiActivity", "Excepción al llamar a la API", e)
                Toast.makeText(
                    this@DirectionsApiActivity,
                    "Excepción: ${e.javaClass.simpleName}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}