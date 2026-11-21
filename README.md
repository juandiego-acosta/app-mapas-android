# App Mapas · Android

App Android en Kotlin que trabaja con mapas, rutas y sensores del teléfono (CETI Colomos, 2025).

## Qué hace

- **Mapa con Google Maps:** muestra la ubicación y coloca marcadores.
- **Rutas con Directions API:** pide la ruta entre dos puntos a la API de Google y la dibuja en el mapa con una polilínea.
- **Intents de mapas:** abre rutas directamente en Google Maps.
- **Sensores:** lee el giroscopio y el sensor de luz del teléfono.

## Tecnologías

Kotlin · Google Maps SDK · Directions API · Retrofit + Gson · servicios de ubicación · SensorManager

## Cómo ejecutarlo

1. Consigue una llave de API en Google Cloud con **Maps SDK for Android** y **Directions API** activados.
2. Reemplaza `TU_API_KEY_DE_GOOGLE_MAPS` por tu llave en:
   - `app/src/main/AndroidManifest.xml`
   - `app/src/main/java/com/example/appmapas/network/ApiService.kt`
3. Abre el proyecto en Android Studio y ejecútalo en un emulador o teléfono.

> La llave real no está en el repositorio, por seguridad.
