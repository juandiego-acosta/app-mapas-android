package com.example.appmapas

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity // CAMBIO: Se usa FragmentActivity para mejor compatibilidad con mapas

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appmapas.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.PolylineOptions

// CAMBIO: La clase ahora hereda de FragmentActivity
class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val PERMISSION_REQUEST_LOCATION = 1 // Código para la solicitud de permisos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val guadalajara = LatLng(20.6668, -103.3918)
        mMap.addMarker(MarkerOptions().position(guadalajara).title("Marcador en Guadalajara"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guadalajara, 15f))

        enableMyLocation()
    }

    fun changeMapType(view: View) {
        when (view.id) {
            R.id.btnEstandar -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.btnSatelite -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.btnHibrida -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.btnPolilinea -> showPolylines()
        }
    }

    private fun showPolylines() {
        val polylineOptions = PolylineOptions()
            .add(LatLng(20.73882, -103.40063)) // Punto A
            .add(LatLng(20.69676, -103.37541)) // Punto B
            .add(LatLng(20.67806, -103.34673)) // Punto C
            .add(LatLng(20.64047, -103.31154)) // Punto D
            .geodesic(true)
            .color(Color.BLUE) // Color de la línea
            .width(10f)

        mMap.addPolyline(polylineOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(20.68697, -103.35339), 12f))
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return
        if (isLocationPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos de ubicación", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Si el permiso fue concedido, habilitamos la capa de ubicación.
                    mMap.isMyLocationEnabled = true
                } else {
                    // Si el permiso fue denegado, mostramos un mensaje.
                    Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}