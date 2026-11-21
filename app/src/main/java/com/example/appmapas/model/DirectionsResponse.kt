package com.example.appmapas.model

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    val routes: List<Route>,
    val status: String, // <-- AÑADIDO: El estado de la respuesta
    @SerializedName("error_message")
    val errorMessage: String? // <-- AÑADIDO: El mensaje de error si existe
)

data class Route(
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline
)

data class OverviewPolyline(
    val points: String
)