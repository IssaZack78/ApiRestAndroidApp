package com.example.apirestapp

import android.graphics.Color as AndroidColor

/**
 * Theme de la app: define colores globales y utilidades para UI.
 */
object Theme {

    // Colores predefinidos de la app (puedes agregar más)
    val primaryColor = AndroidColor.parseColor("#6200EE")
    val primaryDarkColor = AndroidColor.parseColor("#3700B3")
    val secondaryColor = AndroidColor.parseColor("#03DAC5")
    val backgroundColor = AndroidColor.parseColor("#F6F6F6")
    val textColor = AndroidColor.parseColor("#000000")
    val textOnPrimary = AndroidColor.parseColor("#FFFFFF")

    /**
     * Convierte un color hexadecimal (de tu API) a int para usar en Views
     * @param hexColor Ejemplo: "#FF0000"
     */
    fun fromHex(hexColor: String): Int {
        return try {
            AndroidColor.parseColor(hexColor)
        } catch (e: IllegalArgumentException) {
            // Retorna negro si el hex es inválido
            AndroidColor.BLACK
        }
    }
}