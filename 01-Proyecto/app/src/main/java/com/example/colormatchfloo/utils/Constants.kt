/**
 * Descripción: Almacena valores constantes y datos estáticos de la aplicación.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.utils

import com.example.colormatchfloo.R
import com.example.colormatchfloo.model.ColorOption

object Constants {

    const val GAME_DURATION_MS = 30000L // 30 segundos en milisegundos
    const val COUNTDOWN_INTERVAL_MS = 1000L // Intervalo de 1 segundo

    // Centralizamos la lista de colores para que sea fácil añadir más en el futuro.
    val GAME_COLORS = listOf(
        ColorOption("Rojo", R.color.game_red),
        ColorOption("Verde", R.color.game_green),
        ColorOption("Azul", R.color.game_blue),
        ColorOption("Amarillo", R.color.game_yellow),
        ColorOption("Naranja", R.color.game_orange),
        ColorOption("Morado", R.color.game_purple)
    )
}